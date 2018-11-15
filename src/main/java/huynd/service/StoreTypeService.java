package huynd.service;

import huynd.service.dto.StoreTypeDTO;
import huynd.service.dto.response.StoreTypeResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StoreType.
 */
public interface StoreTypeService {

    /**
     * Save a storeType.
     *
     * @param storeTypeDTO the entity to save
     * @return the persisted entity
     */
    StoreTypeDTO save(StoreTypeDTO storeTypeDTO);

    /**
     * Get all the storeTypes.
     *
     * @return the list of entities
     */
    List<StoreTypeDTO> findAll();


    /**
     * Get the "id" storeType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreTypeDTO> findOne(String id);

    /**
     * Delete the "id" storeType.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    List<StoreTypeResponse> findAllStoreType();
}
