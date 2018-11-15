package huynd.service.impl;

import huynd.service.ActivitiesLogService;
import huynd.domain.ActivitiesLog;
import huynd.repository.ActivitiesLogRepository;
import huynd.service.dto.ActivitiesLogDTO;
import huynd.service.mapper.ActivitiesLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ActivitiesLog.
 */
@Service
@Transactional
public class ActivitiesLogServiceImpl implements ActivitiesLogService {

    private final Logger log = LoggerFactory.getLogger(ActivitiesLogServiceImpl.class);

    private final ActivitiesLogRepository activitiesLogRepository;

    private final ActivitiesLogMapper activitiesLogMapper;

    public ActivitiesLogServiceImpl(ActivitiesLogRepository activitiesLogRepository, ActivitiesLogMapper activitiesLogMapper) {
        this.activitiesLogRepository = activitiesLogRepository;
        this.activitiesLogMapper = activitiesLogMapper;
    }

    /**
     * Save a activitiesLog.
     *
     * @param activitiesLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivitiesLogDTO save(ActivitiesLogDTO activitiesLogDTO) {
        log.debug("Request to save ActivitiesLog : {}", activitiesLogDTO);
        ActivitiesLog activitiesLog = activitiesLogMapper.toEntity(activitiesLogDTO);
        System.out.println(activitiesLog);
        activitiesLog = activitiesLogRepository.saveAndFlush(activitiesLog);
        return activitiesLogMapper.toDto(activitiesLog);
    }

    /**
     * Get all the activitiesLogs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesLogDTO> findAll() {
        log.debug("Request to get all ActivitiesLogs");

        return activitiesLogRepository.findAll().stream()
            .map(activitiesLogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one activitiesLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivitiesLogDTO> findOne(Long id) {
        log.debug("Request to get ActivitiesLog : {}", id);
        return activitiesLogRepository.findById(id)
            .map(activitiesLogMapper::toDto);
    }

    /**
     * Delete the activitiesLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivitiesLog : {}", id);
        activitiesLogRepository.deleteById(id);
    }

    @Override
    public List<ActivitiesLogDTO> listActivitesByUsername(Long id) {
        log.debug("Request to get all ActivitiesLogs by Username: {}" +id);
        return activitiesLogRepository.findAllByUserId(id).stream().map(activitiesLogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ActivitiesLogDTO> listActtiviesIn7Days(Date startDate, Date endDate, Long id) {
        return  activitiesLogRepository.findByActivitiesLogDateBetweenAndUserIdOrderByActivitiesLogDate(startDate,endDate,id).stream().map(activitiesLogMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ActivitiesLogDTO> findOneByDate(Date date, Long id) {
        return  activitiesLogRepository.findByActivitiesLogDateAndUserId(date,id).map(activitiesLogMapper::toDto);
    }

    @Override
    public Long sumPointByUser(Long id) {
        return  activitiesLogRepository.sumAtivitiesLogPointReceivedByUserID(id);
    }

    @Override
    public Long sumTimeByUser(Long id) {
        return  activitiesLogRepository.sumActivitiesLogAchievedTimeByUserID(id);
    }


}
