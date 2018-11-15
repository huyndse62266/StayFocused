package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.VoucherLog;
import huynd.repository.VoucherLogRepository;
import huynd.service.VoucherLogService;
import huynd.service.dto.VoucherLogDTO;
import huynd.service.mapper.VoucherLogMapper;
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
 * Test class for the VoucherLogResource REST controller.
 *
 * @see VoucherLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class VoucherLogResourceIntTest {

    private static final Long DEFAULT_VOUCHER_LOG_ID = 1L;
    private static final Long UPDATED_VOUCHER_LOG_ID = 2L;

    private static final Boolean DEFAULT_VOUCHER_LOG_STATUS = false;
    private static final Boolean UPDATED_VOUCHER_LOG_STATUS = true;

    private static final Instant DEFAULT_VOUCHER_LOG_DATE_USED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VOUCHER_LOG_DATE_USED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VoucherLogRepository voucherLogRepository;

    @Autowired
    private VoucherLogMapper voucherLogMapper;
    
    @Autowired
    private VoucherLogService voucherLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherLogMockMvc;

    private VoucherLog voucherLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherLogResource voucherLogResource = new VoucherLogResource(voucherLogService);
        this.restVoucherLogMockMvc = MockMvcBuilders.standaloneSetup(voucherLogResource)
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
    public static VoucherLog createEntity(EntityManager em) {
        VoucherLog voucherLog = new VoucherLog()
            .voucherLogID(DEFAULT_VOUCHER_LOG_ID)
            .voucherLogStatus(DEFAULT_VOUCHER_LOG_STATUS)
            .voucherLogDateUsed(DEFAULT_VOUCHER_LOG_DATE_USED);
        return voucherLog;
    }

    @Before
    public void initTest() {
        voucherLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherLog() throws Exception {
        int databaseSizeBeforeCreate = voucherLogRepository.findAll().size();

        // Create the VoucherLog
        VoucherLogDTO voucherLogDTO = voucherLogMapper.toDto(voucherLog);
        restVoucherLogMockMvc.perform(post("/api/voucher-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherLogDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherLog in the database
        List<VoucherLog> voucherLogList = voucherLogRepository.findAll();
        assertThat(voucherLogList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherLog testVoucherLog = voucherLogList.get(voucherLogList.size() - 1);
        assertThat(testVoucherLog.getVoucherLogID()).isEqualTo(DEFAULT_VOUCHER_LOG_ID);
        assertThat(testVoucherLog.isVoucherLogStatus()).isEqualTo(DEFAULT_VOUCHER_LOG_STATUS);
        assertThat(testVoucherLog.getVoucherLogDateUsed()).isEqualTo(DEFAULT_VOUCHER_LOG_DATE_USED);
    }

    @Test
    @Transactional
    public void createVoucherLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherLogRepository.findAll().size();

        // Create the VoucherLog with an existing ID
        voucherLog.setId(1L);
        VoucherLogDTO voucherLogDTO = voucherLogMapper.toDto(voucherLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherLogMockMvc.perform(post("/api/voucher-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherLog in the database
        List<VoucherLog> voucherLogList = voucherLogRepository.findAll();
        assertThat(voucherLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVoucherLogs() throws Exception {
        // Initialize the database
        voucherLogRepository.saveAndFlush(voucherLog);

        // Get all the voucherLogList
        restVoucherLogMockMvc.perform(get("/api/voucher-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherLogID").value(hasItem(DEFAULT_VOUCHER_LOG_ID.intValue())))
            .andExpect(jsonPath("$.[*].voucherLogStatus").value(hasItem(DEFAULT_VOUCHER_LOG_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].voucherLogDateUsed").value(hasItem(DEFAULT_VOUCHER_LOG_DATE_USED.toString())));
    }
    
    @Test
    @Transactional
    public void getVoucherLog() throws Exception {
        // Initialize the database
        voucherLogRepository.saveAndFlush(voucherLog);

        // Get the voucherLog
        restVoucherLogMockMvc.perform(get("/api/voucher-logs/{id}", voucherLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherLog.getId().intValue()))
            .andExpect(jsonPath("$.voucherLogID").value(DEFAULT_VOUCHER_LOG_ID.intValue()))
            .andExpect(jsonPath("$.voucherLogStatus").value(DEFAULT_VOUCHER_LOG_STATUS.booleanValue()))
            .andExpect(jsonPath("$.voucherLogDateUsed").value(DEFAULT_VOUCHER_LOG_DATE_USED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherLog() throws Exception {
        // Get the voucherLog
        restVoucherLogMockMvc.perform(get("/api/voucher-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherLog() throws Exception {
        // Initialize the database
        voucherLogRepository.saveAndFlush(voucherLog);

        int databaseSizeBeforeUpdate = voucherLogRepository.findAll().size();

        // Update the voucherLog
        VoucherLog updatedVoucherLog = voucherLogRepository.findById(voucherLog.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherLog are not directly saved in db
        em.detach(updatedVoucherLog);
        updatedVoucherLog
            .voucherLogID(UPDATED_VOUCHER_LOG_ID)
            .voucherLogStatus(UPDATED_VOUCHER_LOG_STATUS)
            .voucherLogDateUsed(UPDATED_VOUCHER_LOG_DATE_USED);
        VoucherLogDTO voucherLogDTO = voucherLogMapper.toDto(updatedVoucherLog);

        restVoucherLogMockMvc.perform(put("/api/voucher-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherLogDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherLog in the database
        List<VoucherLog> voucherLogList = voucherLogRepository.findAll();
        assertThat(voucherLogList).hasSize(databaseSizeBeforeUpdate);
        VoucherLog testVoucherLog = voucherLogList.get(voucherLogList.size() - 1);
        assertThat(testVoucherLog.getVoucherLogID()).isEqualTo(UPDATED_VOUCHER_LOG_ID);
        assertThat(testVoucherLog.isVoucherLogStatus()).isEqualTo(UPDATED_VOUCHER_LOG_STATUS);
        assertThat(testVoucherLog.getVoucherLogDateUsed()).isEqualTo(UPDATED_VOUCHER_LOG_DATE_USED);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherLog() throws Exception {
        int databaseSizeBeforeUpdate = voucherLogRepository.findAll().size();

        // Create the VoucherLog
        VoucherLogDTO voucherLogDTO = voucherLogMapper.toDto(voucherLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherLogMockMvc.perform(put("/api/voucher-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherLog in the database
        List<VoucherLog> voucherLogList = voucherLogRepository.findAll();
        assertThat(voucherLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoucherLog() throws Exception {
        // Initialize the database
        voucherLogRepository.saveAndFlush(voucherLog);

        int databaseSizeBeforeDelete = voucherLogRepository.findAll().size();

        // Get the voucherLog
        restVoucherLogMockMvc.perform(delete("/api/voucher-logs/{id}", voucherLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherLog> voucherLogList = voucherLogRepository.findAll();
        assertThat(voucherLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherLog.class);
        VoucherLog voucherLog1 = new VoucherLog();
        voucherLog1.setId(1L);
        VoucherLog voucherLog2 = new VoucherLog();
        voucherLog2.setId(voucherLog1.getId());
        assertThat(voucherLog1).isEqualTo(voucherLog2);
        voucherLog2.setId(2L);
        assertThat(voucherLog1).isNotEqualTo(voucherLog2);
        voucherLog1.setId(null);
        assertThat(voucherLog1).isNotEqualTo(voucherLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherLogDTO.class);
        VoucherLogDTO voucherLogDTO1 = new VoucherLogDTO();
        voucherLogDTO1.setId(1L);
        VoucherLogDTO voucherLogDTO2 = new VoucherLogDTO();
        assertThat(voucherLogDTO1).isNotEqualTo(voucherLogDTO2);
        voucherLogDTO2.setId(voucherLogDTO1.getId());
        assertThat(voucherLogDTO1).isEqualTo(voucherLogDTO2);
        voucherLogDTO2.setId(2L);
        assertThat(voucherLogDTO1).isNotEqualTo(voucherLogDTO2);
        voucherLogDTO1.setId(null);
        assertThat(voucherLogDTO1).isNotEqualTo(voucherLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherLogMapper.fromId(null)).isNull();
    }
}
