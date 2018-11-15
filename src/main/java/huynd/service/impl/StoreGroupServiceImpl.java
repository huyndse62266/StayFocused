package huynd.service.impl;

import huynd.domain.StoreGroup;
import huynd.service.StoreGroupService;
import huynd.repository.StoreGroupRepository;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.mapper.StoreGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing StoreGroup.
 */
@Service
@Transactional
public class StoreGroupServiceImpl implements StoreGroupService {

    private final Logger log = LoggerFactory.getLogger(StoreGroupServiceImpl.class);

    private final StoreGroupRepository storeGroupRepository;

    private final StoreGroupMapper storeGroupMapper;

    public StoreGroupServiceImpl(StoreGroupRepository storeGroupRepository, StoreGroupMapper storeGroupMapper) {
        this.storeGroupRepository = storeGroupRepository;
        this.storeGroupMapper = storeGroupMapper;
    }

    /**
     * Save a store.
     *
     * @param storeGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreGroupDTO save(StoreGroupDTO storeGroupDTO) {
        log.debug("Request to save StoreGroup : {}", storeGroupDTO);
        StoreGroup storeGroup = storeGroupMapper.toEntity(storeGroupDTO);
        storeGroup = storeGroupRepository.save(storeGroup);
        System.out.println(storeGroup.getStoreType().getStoreTypeID());
        return storeGroupMapper.toDto(storeGroup);
    }

    /**
     * Get all the stores.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StoreGroupDTO> findAll() {
        log.debug("Request to get all Stores");
        return storeGroupRepository.findAll().stream()
            .map(storeGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreGroupDTO> findOne(Long id) {
        log.debug("Request to get StoreGroup : {}", id);
        return storeGroupRepository.findById(id)
            .map(storeGroupMapper::toDto);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreGroup : {}", id);
        storeGroupRepository.deleteById(id);
    }

    @Override
    public List<StoreGroupDTO> findAllByStoreType(String id) {
        log.debug("Request to get StoreGroup: {}", id);
        return storeGroupRepository.findAllByStoreTypeStoreTypeID(id).stream()
            .map(storeGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
