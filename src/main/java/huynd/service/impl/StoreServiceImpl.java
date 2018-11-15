package huynd.service.impl;

import huynd.service.StoreService;
import huynd.domain.Store;
import huynd.repository.StoreRepository;
import huynd.service.dto.StoreDTO;
import huynd.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ListStore.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Save a listStore.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save ListStore : {}", storeDTO);
        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        return storeMapper.toDto(store);
    }

    /**
     * Get all the listStores.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> findAll() {
        log.debug("Request to get all ListStores");
        return storeRepository.findAll().stream()
            .map(storeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listStore by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDTO> findOne(Long id) {
        log.debug("Request to get ListStore : {}", id);
        return storeRepository.findById(id)
            .map(storeMapper::toDto);
    }

    /**
     * Delete the listStore by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListStore : {}", id);
        storeRepository.deleteById(id);
    }

    @Override
    public List<StoreDTO> findAllByStoreGroupID(Long id) {
        log.debug("Request to get all ListStores");
        return storeRepository.findAllByStoreGroupStoreGroupID(id).stream()
            .map(storeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
