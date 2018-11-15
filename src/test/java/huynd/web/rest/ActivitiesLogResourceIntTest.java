package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.ActivitiesLog;
import huynd.repository.ActivitiesLogRepository;
import huynd.service.ActivitiesLogService;
import huynd.service.dto.ActivitiesLogDTO;
import huynd.service.mapper.ActivitiesLogMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static huynd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivitiesLogResource REST controller.
 *
 * @see ActivitiesLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class ActivitiesLogResourceIntTest {

    private static final Long DEFAULT_ACTIVITIES_LOG_ID = 1L;
    private static final Long UPDATED_ACTIVITIES_LOG_ID = 2L;

    private static final Instant DEFAULT_ACTIVITIES_LOG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITIES_LOG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ACTIVITIES_LOG_POINT_RECEIVED = 1L;
    private static final Long UPDATED_ACTIVITIES_LOG_POINT_RECEIVED = 2L;

    private static final Long DEFAULT_ACTIVITIES_LOG = 1L;
    private static final Long UPDATED_ACTIVITIES_LOG = 2L;

    @Autowired
    private ActivitiesLogRepository activitiesLogRepository;

    @Autowired
    private ActivitiesLogMapper activitiesLogMapper;
    
    @Autowired
    private ActivitiesLogService activitiesLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivitiesLogMockMvc;

    private ActivitiesLog activitiesLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivitiesLogResource activitiesLogResource = new ActivitiesLogResource(activitiesLogService);
        this.restActivitiesLogMockMvc = MockMvcBuilders.standaloneSetup(activitiesLogResource)
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
    public static ActivitiesLog createEntity(EntityManager em) {
        ActivitiesLog activitiesLog = new ActivitiesLog()
            .activitiesLogID(DEFAULT_ACTIVITIES_LOG_ID)
            .activitiesLogDate(DEFAULT_ACTIVITIES_LOG_DATE)
            .activitiesLogPointReceived(DEFAULT_ACTIVITIES_LOG_POINT_RECEIVED)
            .activitiesLog(DEFAULT_ACTIVITIES_LOG);
        return activitiesLog;
    }

    @Before
    public void initTest() {
        activitiesLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivitiesLog() throws Exception {
        int databaseSizeBeforeCreate = activitiesLogRepository.findAll().size();

        // Create the ActivitiesLog
        ActivitiesLogDTO activitiesLogDTO = activitiesLogMapper.toDto(activitiesLog);
        restActivitiesLogMockMvc.perform(post("/api/activities-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiesLogDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivitiesLog in the database
        List<ActivitiesLog> activitiesLogList = activitiesLogRepository.findAll();
        assertThat(activitiesLogList).hasSize(databaseSizeBeforeCreate + 1);
        ActivitiesLog testActivitiesLog = activitiesLogList.get(activitiesLogList.size() - 1);
        assertThat(testActivitiesLog.getActivitiesLogID()).isEqualTo(DEFAULT_ACTIVITIES_LOG_ID);
        assertThat(testActivitiesLog.getActivitiesLogDate()).isEqualTo(DEFAULT_ACTIVITIES_LOG_DATE);
        assertThat(testActivitiesLog.getActivitiesLogPointReceived()).isEqualTo(DEFAULT_ACTIVITIES_LOG_POINT_RECEIVED);
        assertThat(testActivitiesLog.getActivitiesLog()).isEqualTo(DEFAULT_ACTIVITIES_LOG);
    }

    @Test
    @Transactional
    public void createActivitiesLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activitiesLogRepository.findAll().size();

        // Create the ActivitiesLog with an existing ID
        activitiesLog.setId(1L);
        ActivitiesLogDTO activitiesLogDTO = activitiesLogMapper.toDto(activitiesLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivitiesLogMockMvc.perform(post("/api/activities-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiesLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivitiesLog in the database
        List<ActivitiesLog> activitiesLogList = activitiesLogRepository.findAll();
        assertThat(activitiesLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivitiesLogs() throws Exception {
        // Initialize the database
        activitiesLogRepository.saveAndFlush(activitiesLog);

        // Get all the activitiesLogList
        restActivitiesLogMockMvc.perform(get("/api/activities-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activitiesLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].activitiesLogID").value(hasItem(DEFAULT_ACTIVITIES_LOG_ID.intValue())))
            .andExpect(jsonPath("$.[*].activitiesLogDate").value(hasItem(DEFAULT_ACTIVITIES_LOG_DATE.toString())))
            .andExpect(jsonPath("$.[*].activitiesLogPointReceived").value(hasItem(DEFAULT_ACTIVITIES_LOG_POINT_RECEIVED.intValue())))
            .andExpect(jsonPath("$.[*].activitiesLog").value(hasItem(DEFAULT_ACTIVITIES_LOG.intValue())));
    }
    
    @Test
    @Transactional
    public void getActivitiesLog() throws Exception {
        // Initialize the database
        activitiesLogRepository.saveAndFlush(activitiesLog);

        // Get the activitiesLog
        restActivitiesLogMockMvc.perform(get("/api/activities-logs/{id}", activitiesLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activitiesLog.getId().intValue()))
            .andExpect(jsonPath("$.activitiesLogID").value(DEFAULT_ACTIVITIES_LOG_ID.intValue()))
            .andExpect(jsonPath("$.activitiesLogDate").value(DEFAULT_ACTIVITIES_LOG_DATE.toString()))
            .andExpect(jsonPath("$.activitiesLogPointReceived").value(DEFAULT_ACTIVITIES_LOG_POINT_RECEIVED.intValue()))
            .andExpect(jsonPath("$.activitiesLog").value(DEFAULT_ACTIVITIES_LOG.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivitiesLog() throws Exception {
        // Get the activitiesLog
        restActivitiesLogMockMvc.perform(get("/api/activities-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivitiesLog() throws Exception {
        // Initialize the database
        activitiesLogRepository.saveAndFlush(activitiesLog);

        int databaseSizeBeforeUpdate = activitiesLogRepository.findAll().size();

        // Update the activitiesLog
        ActivitiesLog updatedActivitiesLog = activitiesLogRepository.findById(activitiesLog.getId()).get();
        // Disconnect from session so that the updates on updatedActivitiesLog are not directly saved in db
        em.detach(updatedActivitiesLog);
        updatedActivitiesLog
            .activitiesLogID(UPDATED_ACTIVITIES_LOG_ID)
            .activitiesLogDate(UPDATED_ACTIVITIES_LOG_DATE)
            .activitiesLogPointReceived(UPDATED_ACTIVITIES_LOG_POINT_RECEIVED)
            .activitiesLog(UPDATED_ACTIVITIES_LOG);
        ActivitiesLogDTO activitiesLogDTO = activitiesLogMapper.toDto(updatedActivitiesLog);

        restActivitiesLogMockMvc.perform(put("/api/activities-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiesLogDTO)))
            .andExpect(status().isOk());

        // Validate the ActivitiesLog in the database
        List<ActivitiesLog> activitiesLogList = activitiesLogRepository.findAll();
        assertThat(activitiesLogList).hasSize(databaseSizeBeforeUpdate);
        ActivitiesLog testActivitiesLog = activitiesLogList.get(activitiesLogList.size() - 1);
        assertThat(testActivitiesLog.getActivitiesLogID()).isEqualTo(UPDATED_ACTIVITIES_LOG_ID);
        assertThat(testActivitiesLog.getActivitiesLogDate()).isEqualTo(UPDATED_ACTIVITIES_LOG_DATE);
        assertThat(testActivitiesLog.getActivitiesLogPointReceived()).isEqualTo(UPDATED_ACTIVITIES_LOG_POINT_RECEIVED);
        assertThat(testActivitiesLog.getActivitiesLog()).isEqualTo(UPDATED_ACTIVITIES_LOG);
    }

    @Test
    @Transactional
    public void updateNonExistingActivitiesLog() throws Exception {
        int databaseSizeBeforeUpdate = activitiesLogRepository.findAll().size();

        // Create the ActivitiesLog
        ActivitiesLogDTO activitiesLogDTO = activitiesLogMapper.toDto(activitiesLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivitiesLogMockMvc.perform(put("/api/activities-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activitiesLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivitiesLog in the database
        List<ActivitiesLog> activitiesLogList = activitiesLogRepository.findAll();
        assertThat(activitiesLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivitiesLog() throws Exception {
        // Initialize the database
        activitiesLogRepository.saveAndFlush(activitiesLog);

        int databaseSizeBeforeDelete = activitiesLogRepository.findAll().size();

        // Get the activitiesLog
        restActivitiesLogMockMvc.perform(delete("/api/activities-logs/{id}", activitiesLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivitiesLog> activitiesLogList = activitiesLogRepository.findAll();
        assertThat(activitiesLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivitiesLog.class);
        ActivitiesLog activitiesLog1 = new ActivitiesLog();
        activitiesLog1.setId(1L);
        ActivitiesLog activitiesLog2 = new ActivitiesLog();
        activitiesLog2.setId(activitiesLog1.getId());
        assertThat(activitiesLog1).isEqualTo(activitiesLog2);
        activitiesLog2.setId(2L);
        assertThat(activitiesLog1).isNotEqualTo(activitiesLog2);
        activitiesLog1.setId(null);
        assertThat(activitiesLog1).isNotEqualTo(activitiesLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivitiesLogDTO.class);
        ActivitiesLogDTO activitiesLogDTO1 = new ActivitiesLogDTO();
        activitiesLogDTO1.setId(1L);
        ActivitiesLogDTO activitiesLogDTO2 = new ActivitiesLogDTO();
        assertThat(activitiesLogDTO1).isNotEqualTo(activitiesLogDTO2);
        activitiesLogDTO2.setId(activitiesLogDTO1.getId());
        assertThat(activitiesLogDTO1).isEqualTo(activitiesLogDTO2);
        activitiesLogDTO2.setId(2L);
        assertThat(activitiesLogDTO1).isNotEqualTo(activitiesLogDTO2);
        activitiesLogDTO1.setId(null);
        assertThat(activitiesLogDTO1).isNotEqualTo(activitiesLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activitiesLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activitiesLogMapper.fromId(null)).isNull();
    }
}
