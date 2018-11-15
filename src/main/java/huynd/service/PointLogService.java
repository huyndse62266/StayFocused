package huynd.service;

import huynd.service.dto.PointLogDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PointLog.
 */
public interface PointLogService {

    /**
     * Save a pointLog.
     *
     * @param pointLogDTO the entity to save
     * @return the persisted entity
     */
    PointLogDTO save(PointLogDTO pointLogDTO);

    /**
     * Get all the pointLogs.
     *
     * @return the list of entities
     */
    List<PointLogDTO> findAll();


    /**
     * Get the "id" pointLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PointLogDTO> findOne(Long id);

    /**
     * Delete the "id" pointLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<PointLogDTO> findByVoucherID(Long id);

    List<PointLogDTO> findByUsername(Long id);

    void deleteByVoucherID(Long voucherID);

    Long sumPointByUser(Long id);

    List<PointLogDTO> listPointByUsername(Long id);
}
