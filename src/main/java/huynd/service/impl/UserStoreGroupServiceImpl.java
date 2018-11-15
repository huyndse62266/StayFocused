package huynd.service.impl;

import huynd.service.UserStoreGroupService;
import huynd.domain.UserStoreGroup;
import huynd.repository.UserStoreGroupRepository;
import huynd.service.dto.UserStoreGroupDTO;
import huynd.service.mapper.UserStoreGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserStoreGroup.
 */
@Service
@Transactional
public class UserStoreGroupServiceImpl implements UserStoreGroupService {

    private final Logger log = LoggerFactory.getLogger(UserStoreGroupServiceImpl.class);

    private UserStoreGroupRepository userStoreGroupRepository;

    private UserStoreGroupMapper userStoreGroupMapper;

    public UserStoreGroupServiceImpl(UserStoreGroupRepository userStoreGroupRepository, UserStoreGroupMapper userStoreGroupMapper) {
        this.userStoreGroupRepository = userStoreGroupRepository;
        this.userStoreGroupMapper = userStoreGroupMapper;
    }

    /**
     * Save a userStoreGroup.
     *
     * @param userStoreGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserStoreGroupDTO save(UserStoreGroupDTO userStoreGroupDTO) {
        log.debug("Request to save UserStoreGroup : {}", userStoreGroupDTO);

        UserStoreGroup userStoreGroup = userStoreGroupMapper.toEntity(userStoreGroupDTO);
        userStoreGroup = userStoreGroupRepository.save(userStoreGroup);
        return userStoreGroupMapper.toDto(userStoreGroup);
    }

    /**
     * Get all the userStoreGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserStoreGroupDTO> findAll() {
        log.debug("Request to get all UserStoreGroups");
        return userStoreGroupRepository.findAll().stream()
            .map(userStoreGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userStoreGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserStoreGroupDTO> findOne(Long id) {
        log.debug("Request to get UserStoreGroup : {}", id);
        return userStoreGroupRepository.findById(id)
            .map(userStoreGroupMapper::toDto);
    }

    /**
     * Delete the userStoreGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserStoreGroup : {}", id);
        userStoreGroupRepository.deleteById(id);
    }

    @Override
    public Optional<UserStoreGroupDTO> findByUsername(Long id) {
        log.debug("Request to get UserStoreGroup : {}", id);
        return userStoreGroupRepository.findByUserId(id)
            .map(userStoreGroupMapper::toDto);
    }
}
