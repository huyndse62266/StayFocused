package huynd.service.impl;

import huynd.service.VoucherService;
import huynd.domain.Voucher;
import huynd.repository.VoucherRepository;
import huynd.service.dto.VoucherDTO;
import huynd.service.mapper.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Voucher.
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    /**
     * Save a voucher.
     *
     * @param voucherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VoucherDTO save(VoucherDTO voucherDTO) {
        log.debug("Request to save Voucher : {}", voucherDTO);
        Voucher voucher = voucherMapper.toEntity(voucherDTO);
        System.out.println(voucher.getPost().getPostID());
        voucher = voucherRepository.save(voucher);
        return voucherMapper.toDto(voucher);
    }

    /**
     * Get all the vouchers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VoucherDTO> findAll() {
        log.debug("Request to get all Vouchers");
        return voucherRepository.findAll().stream()
            .map(voucherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one voucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherDTO> findOne(Long id) {
        log.debug("Request to get Voucher : {}", id);
        return voucherRepository.findById(id)
            .map(voucherMapper::toDto);
    }

    /**
     * Delete the voucher by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voucher : {}", id);
        voucherRepository.deleteById(id);
    }

    @Override
    public Long countUnusedVoucher(Long id) {
        log.debug("Request to get number of unused Voucher : {}", id);
        return voucherRepository.countUnusedVoucherByPostID(id);
    }

    @Override
    public Long countTotalVoucher(Long id) {
        log.debug("Request to get number of total Voucher : {}", id);
        return voucherRepository.countVoucherByPostPostID(id);
    }

    @Override
    public Optional<VoucherDTO> getVoucherName(Long id) {
        log.debug("Request to get voucher name : {}", id);
        return voucherRepository.getTopByVoucherStatusFalseAndPostPostID(id).map(voucherMapper::toDto);
    }

    @Override
    public Optional<VoucherDTO> findOneByVoucherNumber(String voucherNumber) {
        return voucherRepository.findOneByVoucherNumber(voucherNumber).map(voucherMapper::toDto);
    }

    @Override
    public List<VoucherDTO> findAllByPostID(Long postID) {
        return voucherRepository.findAllByPostPostID(postID).stream()
            .map(voucherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
