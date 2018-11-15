package huynd.service;

import huynd.service.dto.ActivitiesLogDTO;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ActivitiesLog.
 */
public interface ActivitiesLogService {

    /**
     * Save a activitiesLog.
     *
     * @param activitiesLogDTO the entity to save
     * @return the persisted entity
     */
    ActivitiesLogDTO save(ActivitiesLogDTO activitiesLogDTO);

    /**
     * Get all the activitiesLogs.
     *
     * @return the list of entities
     */
    List<ActivitiesLogDTO> findAll();


    /**
     * Get the "id" activitiesLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActivitiesLogDTO> findOne(Long id);

    /**
     * Delete the "id" activitiesLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<ActivitiesLogDTO> listActivitesByUsername(Long id);

    List<ActivitiesLogDTO> listActtiviesIn7Days(Date startDate, Date endDate, Long id);

    Optional<ActivitiesLogDTO> findOneByDate(Date date, Long id);

    Long sumPointByUser(Long id);

    Long sumTimeByUser(Long id);

}
