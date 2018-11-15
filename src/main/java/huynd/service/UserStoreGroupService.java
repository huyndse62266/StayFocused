package huynd.service;

import huynd.service.dto.UserStoreGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserStoreGroup.
 */
public interface UserStoreGroupService {

    /**
     * Save a userStoreGroup.
     *
     * @param userStoreGroupDTO the entity to save
     * @return the persisted entity
     */
    UserStoreGroupDTO save(UserStoreGroupDTO userStoreGroupDTO);

    /**
     * Get all the userStoreGroups.
     *
     * @return the list of entities
     */
    List<UserStoreGroupDTO> findAll();


    /**
     * Get the "id" userStoreGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserStoreGroupDTO> findOne(Long id);

    /**
     * Delete the "id" userStoreGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<UserStoreGroupDTO> findByUsername(Long id);
}
