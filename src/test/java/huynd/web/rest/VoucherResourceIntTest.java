package huynd.web.rest;

import huynd.MyProject7App;

import huynd.domain.Voucher;
import huynd.repository.VoucherRepository;
import huynd.service.VoucherService;
import huynd.service.dto.VoucherDTO;
import huynd.service.mapper.VoucherMapper;
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
 * Test class for the VoucherResource REST controller.
 *
 * @see VoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyProject7App.class)
public class VoucherResourceIntTest {

    private static final Long DEFAULT_VOUCHER_ID = 1L;
    private static final Long UPDATED_VOUCHER_ID = 2L;

    private static final String DEFAULT_VOUCHER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VOUCHER_STATUS = false;
    private static final Boolean UPDATED_VOUCHER_STATUS = true;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;
    
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherResource voucherResource = new VoucherResource(voucherService);
        this.restVoucherMockMvc = MockMvcBuilders.standaloneSetup(voucherResource)
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
    public static Voucher createEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .voucherID(DEFAULT_VOUCHER_ID)
            .voucherNumber(DEFAULT_VOUCHER_NUMBER)
            .voucherStatus(DEFAULT_VOUCHER_STATUS);
        return voucher;
    }

    @Before
    public void initTest() {
        voucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucher() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isCreated());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate + 1);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getVoucherID()).isEqualTo(DEFAULT_VOUCHER_ID);
        assertThat(testVoucher.getVoucherNumber()).isEqualTo(DEFAULT_VOUCHER_NUMBER);
        assertThat(testVoucher.isVoucherStatus()).isEqualTo(DEFAULT_VOUCHER_STATUS);
    }

    @Test
    @Transactional
    public void createVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher with an existing ID
        voucher.setId(1L);
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVouchers() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherID").value(hasItem(DEFAULT_VOUCHER_ID.intValue())))
            .andExpect(jsonPath("$.[*].voucherNumber").value(hasItem(DEFAULT_VOUCHER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].voucherStatus").value(hasItem(DEFAULT_VOUCHER_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherID").value(DEFAULT_VOUCHER_ID.intValue()))
            .andExpect(jsonPath("$.voucherNumber").value(DEFAULT_VOUCHER_NUMBER.toString()))
            .andExpect(jsonPath("$.voucherStatus").value(DEFAULT_VOUCHER_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .voucherID(UPDATED_VOUCHER_ID)
            .voucherNumber(UPDATED_VOUCHER_NUMBER)
            .voucherStatus(UPDATED_VOUCHER_STATUS);
        VoucherDTO voucherDTO = voucherMapper.toDto(updatedVoucher);

        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getVoucherID()).isEqualTo(UPDATED_VOUCHER_ID);
        assertThat(testVoucher.getVoucherNumber()).isEqualTo(UPDATED_VOUCHER_NUMBER);
        assertThat(testVoucher.isVoucherStatus()).isEqualTo(UPDATED_VOUCHER_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Create the Voucher
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeDelete = voucherRepository.findAll().size();

        // Get the voucher
        restVoucherMockMvc.perform(delete("/api/vouchers/{id}", voucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voucher.class);
        Voucher voucher1 = new Voucher();
        voucher1.setId(1L);
        Voucher voucher2 = new Voucher();
        voucher2.setId(voucher1.getId());
        assertThat(voucher1).isEqualTo(voucher2);
        voucher2.setId(2L);
        assertThat(voucher1).isNotEqualTo(voucher2);
        voucher1.setId(null);
        assertThat(voucher1).isNotEqualTo(voucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherDTO.class);
        VoucherDTO voucherDTO1 = new VoucherDTO();
        voucherDTO1.setId(1L);
        VoucherDTO voucherDTO2 = new VoucherDTO();
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
        voucherDTO2.setId(voucherDTO1.getId());
        assertThat(voucherDTO1).isEqualTo(voucherDTO2);
        voucherDTO2.setId(2L);
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
        voucherDTO1.setId(null);
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherMapper.fromId(null)).isNull();
    }
}
