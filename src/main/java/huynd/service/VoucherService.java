package huynd.service;

import huynd.domain.Voucher;
import huynd.service.dto.VoucherDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Voucher.
 */
public interface VoucherService {

    /**
     * Save a voucher.
     *
     * @param voucherDTO the entity to save
     * @return the persisted entity
     */
    VoucherDTO save(VoucherDTO voucherDTO);

    /**
     * Get all the vouchers.
     *
     * @return the list of entities
     */
    List<VoucherDTO> findAll();


    /**
     * Get the "id" voucher.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VoucherDTO> findOne(Long id);

    /**
     * Delete the "id" voucher.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Long countUnusedVoucher(Long id);

    Long countTotalVoucher(Long id);

    Optional<VoucherDTO> getVoucherName(Long id);

    Optional<VoucherDTO> findOneByVoucherNumber(String voucherNumber);

    List<VoucherDTO> findAllByPostID(Long postID);
}
