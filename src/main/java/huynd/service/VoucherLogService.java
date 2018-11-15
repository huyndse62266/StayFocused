package huynd.service;

import huynd.domain.VoucherLog;
import huynd.service.dto.VoucherLogDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VoucherLog.
 */
public interface VoucherLogService {

    /**
     * Save a voucherLog.
     *
     * @param voucherLogDTO the entity to save
     * @return the persisted entity
     */
    VoucherLogDTO save(VoucherLogDTO voucherLogDTO);

    /**
     * Get all the voucherLogs.
     *
     * @return the list of entities
     */
    List<VoucherLogDTO> findAll();
    /**
     * Get all the VoucherLogDTO where VoucherID is null.
     *
     * @return the list of entities
     */
    List<VoucherLogDTO> findAllWhereVoucherIDIsNull();


    /**
     * Get the "id" voucherLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VoucherLogDTO> findOne(Long id);

    /**
     * Delete the "id" voucherLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<VoucherLogDTO> findByVoucherID(Long id);

    List<VoucherLogDTO> findByUsername(Long username);

    void deleteByVoucherID(Long voucherID);
}
