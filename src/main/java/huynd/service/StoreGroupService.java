package huynd.service;

import huynd.service.dto.StoreGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StoreGroup.
 */
public interface StoreGroupService {

    /**
     * Save a store.
     *
     * @param storeGroupDTO the entity to save
     * @return the persisted entity
     */
    StoreGroupDTO save(StoreGroupDTO storeGroupDTO);

    /**
     * Get all the stores.
     *
     * @return the list of entities
     */
    List<StoreGroupDTO> findAll();


    /**
     * Get the "id" store.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreGroupDTO> findOne(Long id);

    /**
     * Delete the "id" store.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<StoreGroupDTO> findAllByStoreType(String id);
}
