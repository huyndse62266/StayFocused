package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.Store;
import huynd.repository.StoreRepository;
import huynd.service.StoreService;
import huynd.service.dto.StoreDTO;
import huynd.service.mapper.StoreMapper;
import huynd.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static huynd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ListStoreResource REST controller.
 *
 * @see StoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class StoreGroupResourceIntTest {

    private static final String DEFAULT_STORE_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;
    
    @Autowired
    private StoreService storeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restListStoreMockMvc;

    private Store store;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreResource storeResource = new StoreResource(storeService);
        this.restListStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity(EntityManager em) {
        Store store = new Store()
            .storeID(DEFAULT_STORE_ID)
            .storeName(DEFAULT_STORE_NAME)
            .storeAddress(DEFAULT_STORE_ADDRESS);
        return store;
    }

    @Before
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createListStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the ListStore
        StoreDTO storeDTO = storeMapper.toDto(store);
        restListStoreMockMvc.perform(post("/api/list-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the ListStore in the database
        List<Store> listStore = storeRepository.findAll();
        assertThat(listStore).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = listStore.get(listStore.size() - 1);
        assertThat(testStore.getListStoreID()).isEqualTo(DEFAULT_STORE_ID);
        assertThat(testStore.getListStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStore.getListStoreAddress()).isEqualTo(DEFAULT_STORE_ADDRESS);
    }

    @Test
    @Transactional
    public void createListStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the ListStore with an existing ID
        store.setListStoreID("1L");
        StoreDTO storeDTO = storeMapper.toDto(store);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListStoreMockMvc.perform(post("/api/list-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListStore in the database
        List<Store> listStore = storeRepository.findAll();
        assertThat(listStore).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllListStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the listStoreList
        restListStoreMockMvc.perform(get("/api/list-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getListStoreID())))
            .andExpect(jsonPath("$.[*].storeID").value(hasItem(DEFAULT_STORE_ID.toString())))
            .andExpect(jsonPath("$.[*].storeName").value(hasItem(DEFAULT_STORE_NAME.toString())))
            .andExpect(jsonPath("$.[*].storeAddress").value(hasItem(DEFAULT_STORE_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getListStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the listStore
        restListStoreMockMvc.perform(get("/api/list-stores/{id}", store.getListStoreID()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(store.getListStoreID()))
            .andExpect(jsonPath("$.storeID").value(DEFAULT_STORE_ID.toString()))
            .andExpect(jsonPath("$.storeName").value(DEFAULT_STORE_NAME.toString()))
            .andExpect(jsonPath("$.storeAddress").value(DEFAULT_STORE_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingListStore() throws Exception {
        // Get the listStore
        restListStoreMockMvc.perform(get("/api/list-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the listStore
        Store updatedStore = storeRepository.findById(store.getListStoreID()).get();
        // Disconnect from session so that the updates on updatedListStore are not directly saved in db
        em.detach(updatedStore);
        updatedStore
            .listStoreID(UPDATED_STORE_ID)
            .liststoreName(UPDATED_STORE_NAME)
            .listStoreAddress(UPDATED_STORE_ADDRESS);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restListStoreMockMvc.perform(put("/api/list-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isOk());

        // Validate the ListStore in the database
        List<Store> listStore = storeRepository.findAll();
        assertThat(listStore).hasSize(databaseSizeBeforeUpdate);
        Store testStore = listStore.get(listStore.size() - 1);
        assertThat(testStore.getListStoreID()).isEqualTo(UPDATED_STORE_ID);
        assertThat(testStore.getListStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testStore.getListStoreName()).isEqualTo(UPDATED_STORE_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingListStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Create the ListStore
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListStoreMockMvc.perform(put("/api/list-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListStore in the database
        List<Store> listStore = storeRepository.findAll();
        assertThat(listStore).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Get the listStore
        restListStoreMockMvc.perform(delete("/api/list-stores/{id}", store.getListStoreID())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Store> listStore = storeRepository.findAll();
        assertThat(listStore).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Store.class);
        Store store1 = new Store();
        store1.setListStoreID("1L");
        Store store2 = new Store();
        store2.setListStoreID(store1.getListStoreID());
        assertThat(store1).isEqualTo(store2);
        store2.setListStoreID("2L");
        assertThat(store1).isNotEqualTo(store2);
        store1.setListStoreID(null);
        assertThat(store1).isNotEqualTo(store2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreDTO.class);
        StoreDTO storeDTO1 = new StoreDTO();
        storeDTO1.setListStoreID("1L");
        StoreDTO storeDTO2 = new StoreDTO();
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO2.setListStoreID(storeDTO1.getListStoreID());
        assertThat(storeDTO1).isEqualTo(storeDTO2);
        storeDTO2.setListStoreID("2L");
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO1.setListStoreID(null);
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeMapper.fromId("42L").getListStoreName()).isEqualTo(42);
        assertThat(storeMapper.fromId(null)).isNull();
    }
}
