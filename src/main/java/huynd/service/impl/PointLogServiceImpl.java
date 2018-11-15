package huynd.service.impl;

import huynd.service.PointLogService;
import huynd.domain.PointLog;
import huynd.repository.PointLogRepository;
import huynd.service.dto.PointLogDTO;
import huynd.service.mapper.PointLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing PointLog.
 */
@Service
@Transactional
public class PointLogServiceImpl implements PointLogService {

    private final Logger log = LoggerFactory.getLogger(PointLogServiceImpl.class);

    private final PointLogRepository pointLogRepository;

    private final PointLogMapper pointLogMapper;

    public PointLogServiceImpl(PointLogRepository pointLogRepository, PointLogMapper pointLogMapper) {
        this.pointLogRepository = pointLogRepository;
        this.pointLogMapper = pointLogMapper;
    }

    /**
     * Save a pointLog.
     *
     * @param pointLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional
    public PointLogDTO save(PointLogDTO pointLogDTO) {
        log.debug("Request to save PointLog : {}", pointLogDTO);
        PointLog pointLog = pointLogMapper.toEntity(pointLogDTO);
        pointLog = pointLogRepository.saveAndFlush(pointLog);
        return pointLogMapper.toDto(pointLog);
    }

    /**
     * Get all the pointLogs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PointLogDTO> findAll() {
        log.debug("Request to get all PointLogs");
        System.out.println(pointLogRepository.sumPointLogPointSpentByUserId(Long.parseLong("1")));
        return pointLogRepository.findAll().stream()
            .map(pointLogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pointLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PointLogDTO> findOne(Long id) {
        log.debug("Request to get PointLog : {}", id);
        return pointLogRepository.findById(id)
            .map(pointLogMapper::toDto);
    }

    /**
     * Delete the pointLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PointLog : {}", id);
        pointLogRepository.deleteById(id);
    }

    @Override
    public Optional<PointLogDTO> findByVoucherID(Long id) {
        log.debug("Request to get PointLog : {}", id);
        return  pointLogRepository.findByvoucherVoucherID(id).map(pointLogMapper::toDto);
    }

    @Override
    public List<PointLogDTO> findByUsername(Long id) {
        log.debug("Request to get PointLog : {}", id);
        return pointLogRepository.findAllByUserIdOrderByPointLogDateUsed(id).stream().map(pointLogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void deleteByVoucherID(Long voucherID) {
        log.debug("Request to delete PointLog : {}", voucherID);
        pointLogRepository.deleteByvoucherVoucherID(voucherID);
    }

    @Override
    public Long sumPointByUser(Long id) {
        return pointLogRepository.sumPointLogPointSpentByUserId(id);
    }

    @Override
    public List<PointLogDTO> listPointByUsername(Long id) {
        return pointLogRepository.findAllByUserId(id).stream().map(pointLogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
