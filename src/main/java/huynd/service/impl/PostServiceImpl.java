package huynd.service.impl;

import huynd.domain.StoreType;
import huynd.service.PostService;
import huynd.domain.Post;
import huynd.repository.PostRepository;
import huynd.service.StoreTypeService;
import huynd.service.dto.PostDTO;
import huynd.service.dto.StoreTypeDTO;
import huynd.service.dto.response.StoreTypeResponse;
import huynd.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Post.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Autowired
    StoreTypeService storeTypeService;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    /**
     * Get all the posts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> findAll() {
        log.debug("Request to get all Posts");
        return postRepository.findAll().stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one post by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id)
            .map(postMapper::toDto);
    }

    /**
     * Delete the post by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> findAllByStoreGroupID(Long storeID) {
        log.debug("Request to get all Posts");
        return postRepository.findAllByStoreGroupStoreGroupID(storeID).stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<PostDTO> findAllByStoreTypeID(String storeTypeID, Date date) {

        return postRepository.findAllByStoreGroupStoreTypeStoreTypeIDAndPostEndDateGreaterThan(storeTypeID, date).stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> findAllByStoreTypeIDAndPostTitle(String storeTypeID, String postTitle) {
        return postRepository.findAllByStoreGroupStoreTypeStoreTypeIDAndPostTitleContaining(storeTypeID,postTitle).stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<StoreTypeResponse> countPostByStoreType() {
        List<StoreTypeDTO> storeTypes = storeTypeService.findAll();
        List<StoreTypeResponse> storeTypeResponses = new ArrayList<>();
        for (StoreTypeDTO storeTypeDTO : storeTypes) {
            StoreTypeResponse storeTypeResponse = new StoreTypeResponse();
            storeTypeResponse.setStoreTypeName(storeTypeDTO.getStoreTypeName());
            storeTypeResponse.setStoreTypeID(storeTypeDTO.getStoreTypeID());
            storeTypeResponse.setSize(postRepository.countPostByStoreGroupStoreTypeStoreTypeID(storeTypeDTO.getStoreTypeID()));
            storeTypeResponses.add(storeTypeResponse);
        }
        return storeTypeResponses;
    }
}
