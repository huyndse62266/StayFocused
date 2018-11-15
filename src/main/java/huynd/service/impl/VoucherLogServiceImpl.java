package huynd.service.impl;

import huynd.service.VoucherLogService;
import huynd.domain.VoucherLog;
import huynd.repository.VoucherLogRepository;
import huynd.service.dto.VoucherLogDTO;
import huynd.service.mapper.VoucherLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing VoucherLog.
 */
@Service
@Transactional
public class VoucherLogServiceImpl implements VoucherLogService {

    private final Logger log = LoggerFactory.getLogger(VoucherLogServiceImpl.class);

    private final VoucherLogRepository voucherLogRepository;

    private final VoucherLogMapper voucherLogMapper;

    public VoucherLogServiceImpl(VoucherLogRepository voucherLogRepository, VoucherLogMapper voucherLogMapper) {
        this.voucherLogRepository = voucherLogRepository;
        this.voucherLogMapper = voucherLogMapper;
    }

    /**
     * Save a voucherLog.
     *
     * @param voucherLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VoucherLogDTO save(VoucherLogDTO voucherLogDTO) {
        log.debug("Request to save VoucherLog : {}", voucherLogDTO);
        VoucherLog voucherLog = voucherLogMapper.toEntity(voucherLogDTO);
        voucherLog = voucherLogRepository.save(voucherLog);
        return voucherLogMapper.toDto(voucherLog);
    }

    /**
     * Get all the voucherLogs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VoucherLogDTO> findAll() {
        log.debug("Request to get all VoucherLogs");
        return voucherLogRepository.findAll().stream()
            .map(voucherLogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the voucherLogs where VoucherID is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<VoucherLogDTO> findAllWhereVoucherIDIsNull() {
        log.debug("Request to get all voucherLogs where VoucherID is null");
        return StreamSupport
            .stream(voucherLogRepository.findAll().spliterator(), false)
            .filter(voucherLog -> voucherLog.getVoucher() == null)
            .map(voucherLogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one voucherLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherLogDTO> findOne(Long id) {
        log.debug("Request to get VoucherLog : {}", id);
        return voucherLogRepository.findById(id)
            .map(voucherLogMapper::toDto);
    }

    /**
     * Delete the voucherLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherLog : {}", id);
        voucherLogRepository.deleteById(id);
    }

    @Override
    public Optional<VoucherLogDTO> findByVoucherID(Long id) {
        log.debug("Request to get VoucherLog : {}", id);
        return  voucherLogRepository.findByvoucherVoucherID(id).map(voucherLogMapper::toDto);
    }

    @Override
    public List<VoucherLogDTO> findByUsername(Long id) {
        log.debug("Request to get VoucherLog : {}", id);
        return voucherLogRepository.findAllByUserIdOrderByVoucherLogDateUsed(id).stream().map(voucherLogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void deleteByVoucherID(Long voucherID) {
        log.debug("Request to delete VoucherLog : {}", voucherID);
        voucherLogRepository.deleteByvoucherVoucherID(voucherID);
    }
}
