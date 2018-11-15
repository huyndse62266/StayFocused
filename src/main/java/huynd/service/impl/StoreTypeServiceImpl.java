package huynd.service.impl;

import huynd.repository.StoreGroupRepository;
import huynd.service.StoreGroupService;
import huynd.service.StoreTypeService;
import huynd.domain.StoreType;
import huynd.repository.StoreTypeRepository;
import huynd.service.dto.StoreTypeDTO;
import huynd.service.dto.response.StoreTypeResponse;
import huynd.service.mapper.StoreTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing StoreType.
 */
@Service
@Transactional
public class StoreTypeServiceImpl implements StoreTypeService {

    private final Logger log = LoggerFactory.getLogger(StoreTypeServiceImpl.class);

    private final StoreTypeRepository storeTypeRepository;

    private final StoreTypeMapper storeTypeMapper;

    @Autowired
    StoreGroupRepository storeGroupRepository;

    public StoreTypeServiceImpl(StoreTypeRepository storeTypeRepository, StoreTypeMapper storeTypeMapper) {
        this.storeTypeRepository = storeTypeRepository;
        this.storeTypeMapper = storeTypeMapper;
    }

    /**
     * Save a storeType.
     *
     * @param storeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreTypeDTO save(StoreTypeDTO storeTypeDTO) {
        log.debug("Request to save StoreType : {}", storeTypeDTO);
        StoreType storeType = storeTypeMapper.toEntity(storeTypeDTO);
        storeType = storeTypeRepository.save(storeType);
        return storeTypeMapper.toDto(storeType);
    }

    /**
     * Get all the storeTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StoreTypeDTO> findAll() {
        log.debug("Request to get all StoreTypes");
        return storeTypeRepository.findAll().stream()
            .map(storeTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one storeType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreTypeDTO> findOne(String id) {
        log.debug("Request to get StoreType : {}", id);
        return storeTypeRepository.findById(id)
            .map(storeTypeMapper::toDto);
    }

    /**
     * Delete the storeType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete StoreType : {}", id);
        storeTypeRepository.deleteById(id);
    }

    @Override
    public List<StoreTypeResponse> findAllStoreType() {
        List<StoreType> storeTypes = storeTypeRepository.findAll();
        List<StoreTypeResponse> storeTypeResponses = new ArrayList<>();
        for (StoreType storeType : storeTypes) {
            StoreTypeResponse storeTypeResponse = new StoreTypeResponse();
            storeTypeResponse.setStoreTypeName(storeType.getStoreTypeName());
            storeTypeResponse.setStoreTypeID(storeType.getStoreTypeID());
            storeTypeResponse.setSize(storeGroupRepository.countStoreGroupByStoreTypeStoreTypeID(storeType.getStoreTypeID()));
            storeTypeResponses.add(storeTypeResponse);
        }
        return storeTypeResponses;
    }
}
