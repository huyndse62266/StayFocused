package huynd.service;

import huynd.service.dto.StoreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ListStore.
 */
public interface StoreService {

    /**
     * Save a listStore.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    StoreDTO save(StoreDTO storeDTO);

    /**
     * Get all the listStores.
     *
     * @return the list of entities
     */
    List<StoreDTO> findAll();


    /**
     * Get the "id" listStore.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreDTO> findOne(Long id);

    /**
     * Delete the "id" listStore.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<StoreDTO> findAllByStoreGroupID(Long id);
}
