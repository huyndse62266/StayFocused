package huynd.service;

import huynd.service.dto.PostDTO;
import huynd.service.dto.response.StoreTypeResponse;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Post.
 */
public interface PostService {

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @return the list of entities
     */
    List<PostDTO> findAll();


    /**
     * Get the "id" post.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PostDTO> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<PostDTO> findAllByStoreGroupID(Long storeID);

    List<PostDTO> findAllByStoreTypeID(String storeTypeID, Date date);

    List<PostDTO> findAllByStoreTypeIDAndPostTitle(String storeTypeID, String postTitle);

    public List<StoreTypeResponse> countPostByStoreType();
}
