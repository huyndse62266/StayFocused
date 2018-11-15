package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.PointLog;
import huynd.repository.PointLogRepository;
import huynd.service.PointLogService;
import huynd.service.dto.PointLogDTO;
import huynd.service.mapper.PointLogMapper;
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
 * Test class for the PointLogResource REST controller.
 *
 * @see PointLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class PointLogResourceIntTest {

    private static final Long DEFAULT_POINT_LOG_ID = 1L;
    private static final Long UPDATED_POINT_LOG_ID = 2L;

    private static final Long DEFAULT_POINT_LOG_POINT_SPENT = 1L;
    private static final Long UPDATED_POINT_LOG_POINT_SPENT = 2L;

    private static final Instant DEFAULT_POINT_LOG_DATE_USED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POINT_LOG_DATE_USED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private PointLogMapper pointLogMapper;
    
    @Autowired
    private PointLogService pointLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPointLogMockMvc;

    private PointLog pointLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PointLogResource pointLogResource = new PointLogResource(pointLogService);
        this.restPointLogMockMvc = MockMvcBuilders.standaloneSetup(pointLogResource)
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
    public static PointLog createEntity(EntityManager em) {
        PointLog pointLog = new PointLog()
            .pointLogID(DEFAULT_POINT_LOG_ID)
            .pointLogPointSpent(DEFAULT_POINT_LOG_POINT_SPENT)
            .pointLogDateUsed(DEFAULT_POINT_LOG_DATE_USED);
        return pointLog;
    }

    @Before
    public void initTest() {
        pointLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPointLog() throws Exception {
        int databaseSizeBeforeCreate = pointLogRepository.findAll().size();

        // Create the PointLog
        PointLogDTO pointLogDTO = pointLogMapper.toDto(pointLog);
        restPointLogMockMvc.perform(post("/api/point-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointLogDTO)))
            .andExpect(status().isCreated());

        // Validate the PointLog in the database
        List<PointLog> pointLogList = pointLogRepository.findAll();
        assertThat(pointLogList).hasSize(databaseSizeBeforeCreate + 1);
        PointLog testPointLog = pointLogList.get(pointLogList.size() - 1);
        assertThat(testPointLog.getPointLogID()).isEqualTo(DEFAULT_POINT_LOG_ID);
        assertThat(testPointLog.getPointLogPointSpent()).isEqualTo(DEFAULT_POINT_LOG_POINT_SPENT);
        assertThat(testPointLog.getPointLogDateUsed()).isEqualTo(DEFAULT_POINT_LOG_DATE_USED);
    }

    @Test
    @Transactional
    public void createPointLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointLogRepository.findAll().size();

        // Create the PointLog with an existing ID
        pointLog.setId(1L);
        PointLogDTO pointLogDTO = pointLogMapper.toDto(pointLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointLogMockMvc.perform(post("/api/point-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PointLog in the database
        List<PointLog> pointLogList = pointLogRepository.findAll();
        assertThat(pointLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPointLogs() throws Exception {
        // Initialize the database
        pointLogRepository.saveAndFlush(pointLog);

        // Get all the pointLogList
        restPointLogMockMvc.perform(get("/api/point-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].pointLogID").value(hasItem(DEFAULT_POINT_LOG_ID.intValue())))
            .andExpect(jsonPath("$.[*].pointLogPointSpent").value(hasItem(DEFAULT_POINT_LOG_POINT_SPENT.intValue())))
            .andExpect(jsonPath("$.[*].pointLogDateUsed").value(hasItem(DEFAULT_POINT_LOG_DATE_USED.toString())));
    }
    
    @Test
    @Transactional
    public void getPointLog() throws Exception {
        // Initialize the database
        pointLogRepository.saveAndFlush(pointLog);

        // Get the pointLog
        restPointLogMockMvc.perform(get("/api/point-logs/{id}", pointLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pointLog.getId().intValue()))
            .andExpect(jsonPath("$.pointLogID").value(DEFAULT_POINT_LOG_ID.intValue()))
            .andExpect(jsonPath("$.pointLogPointSpent").value(DEFAULT_POINT_LOG_POINT_SPENT.intValue()))
            .andExpect(jsonPath("$.pointLogDateUsed").value(DEFAULT_POINT_LOG_DATE_USED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPointLog() throws Exception {
        // Get the pointLog
        restPointLogMockMvc.perform(get("/api/point-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePointLog() throws Exception {
        // Initialize the database
        pointLogRepository.saveAndFlush(pointLog);

        int databaseSizeBeforeUpdate = pointLogRepository.findAll().size();

        // Update the pointLog
        PointLog updatedPointLog = pointLogRepository.findById(pointLog.getId()).get();
        // Disconnect from session so that the updates on updatedPointLog are not directly saved in db
        em.detach(updatedPointLog);
        updatedPointLog
            .pointLogID(UPDATED_POINT_LOG_ID)
            .pointLogPointSpent(UPDATED_POINT_LOG_POINT_SPENT)
            .pointLogDateUsed(UPDATED_POINT_LOG_DATE_USED);
        PointLogDTO pointLogDTO = pointLogMapper.toDto(updatedPointLog);

        restPointLogMockMvc.perform(put("/api/point-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointLogDTO)))
            .andExpect(status().isOk());

        // Validate the PointLog in the database
        List<PointLog> pointLogList = pointLogRepository.findAll();
        assertThat(pointLogList).hasSize(databaseSizeBeforeUpdate);
        PointLog testPointLog = pointLogList.get(pointLogList.size() - 1);
        assertThat(testPointLog.getPointLogID()).isEqualTo(UPDATED_POINT_LOG_ID);
        assertThat(testPointLog.getPointLogPointSpent()).isEqualTo(UPDATED_POINT_LOG_POINT_SPENT);
        assertThat(testPointLog.getPointLogDateUsed()).isEqualTo(UPDATED_POINT_LOG_DATE_USED);
    }

    @Test
    @Transactional
    public void updateNonExistingPointLog() throws Exception {
        int databaseSizeBeforeUpdate = pointLogRepository.findAll().size();

        // Create the PointLog
        PointLogDTO pointLogDTO = pointLogMapper.toDto(pointLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointLogMockMvc.perform(put("/api/point-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PointLog in the database
        List<PointLog> pointLogList = pointLogRepository.findAll();
        assertThat(pointLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePointLog() throws Exception {
        // Initialize the database
        pointLogRepository.saveAndFlush(pointLog);

        int databaseSizeBeforeDelete = pointLogRepository.findAll().size();

        // Get the pointLog
        restPointLogMockMvc.perform(delete("/api/point-logs/{id}", pointLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PointLog> pointLogList = pointLogRepository.findAll();
        assertThat(pointLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointLog.class);
        PointLog pointLog1 = new PointLog();
        pointLog1.setId(1L);
        PointLog pointLog2 = new PointLog();
        pointLog2.setId(pointLog1.getId());
        assertThat(pointLog1).isEqualTo(pointLog2);
        pointLog2.setId(2L);
        assertThat(pointLog1).isNotEqualTo(pointLog2);
        pointLog1.setId(null);
        assertThat(pointLog1).isNotEqualTo(pointLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointLogDTO.class);
        PointLogDTO pointLogDTO1 = new PointLogDTO();
        pointLogDTO1.setId(1L);
        PointLogDTO pointLogDTO2 = new PointLogDTO();
        assertThat(pointLogDTO1).isNotEqualTo(pointLogDTO2);
        pointLogDTO2.setId(pointLogDTO1.getId());
        assertThat(pointLogDTO1).isEqualTo(pointLogDTO2);
        pointLogDTO2.setId(2L);
        assertThat(pointLogDTO1).isNotEqualTo(pointLogDTO2);
        pointLogDTO1.setId(null);
        assertThat(pointLogDTO1).isNotEqualTo(pointLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pointLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pointLogMapper.fromId(null)).isNull();
    }
}
