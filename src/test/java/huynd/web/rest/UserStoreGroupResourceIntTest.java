package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.UserStoreGroup;
import huynd.repository.UserStoreGroupRepository;
import huynd.service.UserStoreGroupService;
import huynd.service.dto.UserStoreGroupDTO;
import huynd.service.mapper.UserStoreGroupMapper;
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
 * Test class for the UserStoreGroupResource REST controller.
 *
 * @see UserStoreGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class UserStoreGroupResourceIntTest {

    private static final Long DEFAULT_STORE_GROUP_ID = 1L;
    private static final Long UPDATED_STORE_GROUP_ID = 2L;

    @Autowired
    private UserStoreGroupRepository userStoreGroupRepository;

    @Autowired
    private UserStoreGroupMapper userStoreGroupMapper;
    
    @Autowired
    private UserStoreGroupService userStoreGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserStoreGroupMockMvc;

    private UserStoreGroup userStoreGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserStoreGroupResource userStoreGroupResource = new UserStoreGroupResource(userStoreGroupService);
        this.restUserStoreGroupMockMvc = MockMvcBuilders.standaloneSetup(userStoreGroupResource)
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
    public static UserStoreGroup createEntity(EntityManager em) {
        UserStoreGroup userStoreGroup = new UserStoreGroup()
            .storeGroupID(DEFAULT_STORE_GROUP_ID);
        return userStoreGroup;
    }

    @Before
    public void initTest() {
        userStoreGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStoreGroup() throws Exception {
        int databaseSizeBeforeCreate = userStoreGroupRepository.findAll().size();

        // Create the UserStoreGroup
        UserStoreGroupDTO userStoreGroupDTO = userStoreGroupMapper.toDto(userStoreGroup);
        restUserStoreGroupMockMvc.perform(post("/api/user-store-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStoreGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStoreGroup in the database
        List<UserStoreGroup> userStoreGroupList = userStoreGroupRepository.findAll();
        assertThat(userStoreGroupList).hasSize(databaseSizeBeforeCreate + 1);
        UserStoreGroup testUserStoreGroup = userStoreGroupList.get(userStoreGroupList.size() - 1);
        assertThat(testUserStoreGroup.getStoreGroupID()).isEqualTo(DEFAULT_STORE_GROUP_ID);
    }

    @Test
    @Transactional
    public void createUserStoreGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStoreGroupRepository.findAll().size();

        // Create the UserStoreGroup with an existing ID
        userStoreGroup.setId(1L);
        UserStoreGroupDTO userStoreGroupDTO = userStoreGroupMapper.toDto(userStoreGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStoreGroupMockMvc.perform(post("/api/user-store-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStoreGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStoreGroup in the database
        List<UserStoreGroup> userStoreGroupList = userStoreGroupRepository.findAll();
        assertThat(userStoreGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserStoreGroups() throws Exception {
        // Initialize the database
        userStoreGroupRepository.saveAndFlush(userStoreGroup);

        // Get all the userStoreGroupList
        restUserStoreGroupMockMvc.perform(get("/api/user-store-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStoreGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].storeGroupID").value(hasItem(DEFAULT_STORE_GROUP_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserStoreGroup() throws Exception {
        // Initialize the database
        userStoreGroupRepository.saveAndFlush(userStoreGroup);

        // Get the userStoreGroup
        restUserStoreGroupMockMvc.perform(get("/api/user-store-groups/{id}", userStoreGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userStoreGroup.getId().intValue()))
            .andExpect(jsonPath("$.storeGroupID").value(DEFAULT_STORE_GROUP_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserStoreGroup() throws Exception {
        // Get the userStoreGroup
        restUserStoreGroupMockMvc.perform(get("/api/user-store-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStoreGroup() throws Exception {
        // Initialize the database
        userStoreGroupRepository.saveAndFlush(userStoreGroup);

        int databaseSizeBeforeUpdate = userStoreGroupRepository.findAll().size();

        // Update the userStoreGroup
        UserStoreGroup updatedUserStoreGroup = userStoreGroupRepository.findById(userStoreGroup.getId()).get();
        // Disconnect from session so that the updates on updatedUserStoreGroup are not directly saved in db
        em.detach(updatedUserStoreGroup);
        updatedUserStoreGroup
            .storeGroupID(UPDATED_STORE_GROUP_ID);
        UserStoreGroupDTO userStoreGroupDTO = userStoreGroupMapper.toDto(updatedUserStoreGroup);

        restUserStoreGroupMockMvc.perform(put("/api/user-store-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStoreGroupDTO)))
            .andExpect(status().isOk());

        // Validate the UserStoreGroup in the database
        List<UserStoreGroup> userStoreGroupList = userStoreGroupRepository.findAll();
        assertThat(userStoreGroupList).hasSize(databaseSizeBeforeUpdate);
        UserStoreGroup testUserStoreGroup = userStoreGroupList.get(userStoreGroupList.size() - 1);
        assertThat(testUserStoreGroup.getStoreGroupID()).isEqualTo(UPDATED_STORE_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStoreGroup() throws Exception {
        int databaseSizeBeforeUpdate = userStoreGroupRepository.findAll().size();

        // Create the UserStoreGroup
        UserStoreGroupDTO userStoreGroupDTO = userStoreGroupMapper.toDto(userStoreGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserStoreGroupMockMvc.perform(put("/api/user-store-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStoreGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStoreGroup in the database
        List<UserStoreGroup> userStoreGroupList = userStoreGroupRepository.findAll();
        assertThat(userStoreGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserStoreGroup() throws Exception {
        // Initialize the database
        userStoreGroupRepository.saveAndFlush(userStoreGroup);

        int databaseSizeBeforeDelete = userStoreGroupRepository.findAll().size();

        // Get the userStoreGroup
        restUserStoreGroupMockMvc.perform(delete("/api/user-store-groups/{id}", userStoreGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStoreGroup> userStoreGroupList = userStoreGroupRepository.findAll();
        assertThat(userStoreGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStoreGroup.class);
        UserStoreGroup userStoreGroup1 = new UserStoreGroup();
        userStoreGroup1.setId(1L);
        UserStoreGroup userStoreGroup2 = new UserStoreGroup();
        userStoreGroup2.setId(userStoreGroup1.getId());
        assertThat(userStoreGroup1).isEqualTo(userStoreGroup2);
        userStoreGroup2.setId(2L);
        assertThat(userStoreGroup1).isNotEqualTo(userStoreGroup2);
        userStoreGroup1.setId(null);
        assertThat(userStoreGroup1).isNotEqualTo(userStoreGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStoreGroupDTO.class);
        UserStoreGroupDTO userStoreGroupDTO1 = new UserStoreGroupDTO();
        userStoreGroupDTO1.setId(1L);
        UserStoreGroupDTO userStoreGroupDTO2 = new UserStoreGroupDTO();
        assertThat(userStoreGroupDTO1).isNotEqualTo(userStoreGroupDTO2);
        userStoreGroupDTO2.setId(userStoreGroupDTO1.getId());
        assertThat(userStoreGroupDTO1).isEqualTo(userStoreGroupDTO2);
        userStoreGroupDTO2.setId(2L);
        assertThat(userStoreGroupDTO1).isNotEqualTo(userStoreGroupDTO2);
        userStoreGroupDTO1.setId(null);
        assertThat(userStoreGroupDTO1).isNotEqualTo(userStoreGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userStoreGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userStoreGroupMapper.fromId(null)).isNull();
    }
}
