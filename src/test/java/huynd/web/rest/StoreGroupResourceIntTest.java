package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.StoreGroup;
import huynd.repository.StoreGroupRepository;
import huynd.service.StoreGroupService;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.mapper.StoreGroupMapper;
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
 * Test class for the StoreResource REST controller.
 *
 * @see StoreGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class StoreGroupResourceIntTest {

    private static final String DEFAULT_STORE_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_STORE_PHONE = 1L;
    private static final Long UPDATED_STORE_PHONE = 2L;

    private static final String DEFAULT_STORE_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_STORE_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_WEB = "AAAAAAAAAA";
    private static final String UPDATED_STORE_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STORE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StoreGroupRepository storeGroupRepository;

    @Autowired
    private StoreGroupMapper storeGroupMapper;
    
    @Autowired
    private StoreGroupService storeGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStoreMockMvc;

    private StoreGroup storeGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreGroupResource storeGroupResource = new StoreGroupResource(storeGroupService);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeGroupResource)
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
    public static StoreGroup createEntity(EntityManager em) {
        StoreGroup storeGroup = new StoreGroup()
            .storeID(DEFAULT_STORE_ID)
            .storeName(DEFAULT_STORE_NAME)
            .storePhone(DEFAULT_STORE_PHONE)
            .storeMail(DEFAULT_STORE_MAIL)
            .storeWeb(DEFAULT_STORE_WEB)
            .storeDescription(DEFAULT_STORE_DESCRIPTION);
        return storeGroup;
    }

    @Before
    public void initTest() {
        storeGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeGroupRepository.findAll().size();

        // Create the StoreGroup
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        StoreGroup testStoreGroup = storeGroupList.get(storeGroupList.size() - 1);
        assertThat(testStoreGroup.getStoreID()).isEqualTo(DEFAULT_STORE_ID);
        assertThat(testStoreGroup.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStoreGroup.getStorePhone()).isEqualTo(DEFAULT_STORE_PHONE);
        assertThat(testStoreGroup.getStoreMail()).isEqualTo(DEFAULT_STORE_MAIL);
        assertThat(testStoreGroup.getStoreWeb()).isEqualTo(DEFAULT_STORE_WEB);
        assertThat(testStoreGroup.getStoreDescription()).isEqualTo(DEFAULT_STORE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeGroupRepository.findAll().size();

        // Create the StoreGroup with an existing ID
        storeGroup.setId(1L);
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        // Get all the storeList
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].storeID").value(hasItem(DEFAULT_STORE_ID.toString())))
            .andExpect(jsonPath("$.[*].storeName").value(hasItem(DEFAULT_STORE_NAME.toString())))
            .andExpect(jsonPath("$.[*].storePhone").value(hasItem(DEFAULT_STORE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].storeMail").value(hasItem(DEFAULT_STORE_MAIL.toString())))
            .andExpect(jsonPath("$.[*].storeWeb").value(hasItem(DEFAULT_STORE_WEB.toString())))
            .andExpect(jsonPath("$.[*].storeDescription").value(hasItem(DEFAULT_STORE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        // Get the storeGroup
        restStoreMockMvc.perform(get("/api/stores/{id}", storeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeGroup.getId().intValue()))
            .andExpect(jsonPath("$.storeID").value(DEFAULT_STORE_ID.toString()))
            .andExpect(jsonPath("$.storeName").value(DEFAULT_STORE_NAME.toString()))
            .andExpect(jsonPath("$.storePhone").value(DEFAULT_STORE_PHONE.intValue()))
            .andExpect(jsonPath("$.storeMail").value(DEFAULT_STORE_MAIL.toString()))
            .andExpect(jsonPath("$.storeWeb").value(DEFAULT_STORE_WEB.toString()))
            .andExpect(jsonPath("$.storeDescription").value(DEFAULT_STORE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the storeGroup
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        int databaseSizeBeforeUpdate = storeGroupRepository.findAll().size();

        // Update the storeGroup
        StoreGroup updatedStoreGroup = storeGroupRepository.findById(storeGroup.getId()).get();
        // Disconnect from session so that the updates on updatedStoreGroup are not directly saved in db
        em.detach(updatedStoreGroup);
        updatedStoreGroup
            .storeID(UPDATED_STORE_ID)
            .storeName(UPDATED_STORE_NAME)
            .storePhone(UPDATED_STORE_PHONE)
            .storeMail(UPDATED_STORE_MAIL)
            .storeWeb(UPDATED_STORE_WEB)
            .storeDescription(UPDATED_STORE_DESCRIPTION);
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(updatedStoreGroup);

        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isOk());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeUpdate);
        StoreGroup testStoreGroup = storeGroupList.get(storeGroupList.size() - 1);
        assertThat(testStoreGroup.getStoreID()).isEqualTo(UPDATED_STORE_ID);
        assertThat(testStoreGroup.getStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testStoreGroup.getStorePhone()).isEqualTo(UPDATED_STORE_PHONE);
        assertThat(testStoreGroup.getStoreMail()).isEqualTo(UPDATED_STORE_MAIL);
        assertThat(testStoreGroup.getStoreWeb()).isEqualTo(UPDATED_STORE_WEB);
        assertThat(testStoreGroup.getStoreDescription()).isEqualTo(UPDATED_STORE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeGroupRepository.findAll().size();

        // Create the StoreGroup
        StoreGroupDTO storeGroupDTO = storeGroupMapper.toDto(storeGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroup in the database
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeGroupRepository.saveAndFlush(storeGroup);

        int databaseSizeBeforeDelete = storeGroupRepository.findAll().size();

        // Get the storeGroup
        restStoreMockMvc.perform(delete("/api/stores/{id}", storeGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreGroup> storeGroupList = storeGroupRepository.findAll();
        assertThat(storeGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreGroup.class);
        StoreGroup storeGroup1 = new StoreGroup();
        storeGroup1.setId(1L);
        StoreGroup storeGroup2 = new StoreGroup();
        storeGroup2.setId(storeGroup1.getId());
        assertThat(storeGroup1).isEqualTo(storeGroup2);
        storeGroup2.setId(2L);
        assertThat(storeGroup1).isNotEqualTo(storeGroup2);
        storeGroup1.setId(null);
        assertThat(storeGroup1).isNotEqualTo(storeGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreGroupDTO.class);
        StoreGroupDTO storeGroupDTO1 = new StoreGroupDTO();
        storeGroupDTO1.setId(1L);
        StoreGroupDTO storeGroupDTO2 = new StoreGroupDTO();
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
        storeGroupDTO2.setId(storeGroupDTO1.getId());
        assertThat(storeGroupDTO1).isEqualTo(storeGroupDTO2);
        storeGroupDTO2.setId(2L);
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
        storeGroupDTO1.setId(null);
        assertThat(storeGroupDTO1).isNotEqualTo(storeGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storeGroupMapper.fromId(null)).isNull();
    }
}
