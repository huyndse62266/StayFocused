package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.security.AuthoritiesConstants;
import huynd.service.PostService;
import huynd.service.StoreGroupService;
import huynd.service.dto.PostDTO;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Optional;

/**
 * REST controller for managing Post.
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "post";

    private final PostService postService;

    @Autowired
    StoreGroupService storeGroupService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    /**
     * POST  /posts : Create a new post.
     *
     * @param postDTO the postDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postDTO, or with status 400 (Bad Request) if the post has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posts")
    @Timed
    public AntiPhoneResponse createPost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (StringUtils.isEmpty(postDTO.getPostName()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post Name " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(postDTO.getPostDescription()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post Description " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(postDTO.getPostBanner()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post Banner " + Messages.REQUEST_PARAM_REQUIRED);
        if (postDTO.getPostStartDate() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post Start Date " + Messages.REQUEST_PARAM_REQUIRED);
        if (postDTO.getPostEndDate() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post End Date " + Messages.REQUEST_PARAM_REQUIRED);
        if (postDTO.getPostStartDate().compareTo(postDTO.getPostEndDate()) > 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Start Date can't be after End Date!!!");
        if (postDTO.getStoreGroup().getStoreGroupID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!storeGroupService.findOne(postDTO.getStoreGroup().getStoreGroupID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup " + Messages.ID_NOT_EXIST);
        PostDTO result = postService.save(postDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY, result);
    }

    /**
     * PUT  /posts : Updates an existing post.
     *
     * @param postDTO the postDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postDTO,
     * or with status 400 (Bad Request) if the postDTO is not valid,
     * or with status 500 (Internal Server Error) if the postDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posts")
    @Timed
    public AntiPhoneResponse updatePost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to update Post : {}", postDTO);

        if (postDTO.getPostID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!postService.findOne(postDTO.getPostID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.ID_NOT_EXIST);
        PostDTO existPost = postService.findOne(postDTO.getPostID()).get();
        if (StringUtils.isNotEmpty(postDTO.getPostName()))
            existPost.setPostName(postDTO.getPostName());
        if (StringUtils.isNotEmpty(postDTO.getPostDescription()))
            existPost.setPostDescription(postDTO.getPostDescription());
        if (StringUtils.isNotEmpty(postDTO.getPostBanner()))
            existPost.setPostBanner(postDTO.getPostBanner());
        if (postDTO.getPostStartDate() != null) {
            if (postDTO.getPostStartDate().compareTo(postDTO.getPostEndDate()) > 0) {
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Start Date can't be after End Date!!!");
            }
            existPost.setPostStartDate(postDTO.getPostStartDate());
        }
        if (postDTO.getPostEndDate() != null) {
            if (postDTO.getPostEndDate().compareTo(postDTO.getPostStartDate()) < 0) {
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Start Date can't be after End Date!!!");
            }
            existPost.setPostEndDate(postDTO.getPostEndDate());
        }
        if (postDTO.getStoreGroup() != null) {
            if (!storeGroupService.findOne(postDTO.getStoreGroup().getStoreGroupID()).isPresent())
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup " + Messages.ID_NOT_EXIST);
            StoreGroupDTO storeGroupDTO = new StoreGroupDTO();
            storeGroupDTO.setStoreGroupID(postDTO.getStoreGroup().getStoreGroupID());
            existPost.setStoreGroup(storeGroupDTO);
        }
        PostDTO result = postService.save(existPost);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /posts : get all the posts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posts in body
     */
    @GetMapping("/posts")
    @Timed
    public AntiPhoneResponse getAllPosts() {
        log.debug("REST request to get all Posts");
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, postService.findAll());
    }

    @GetMapping("/posts/store-groups/store-type/{id}")
    @Timed
    public AntiPhoneResponse getAllPostByStoreType(@PathVariable String id) {
        log.debug("REST request to get all Posts");
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());

        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, postService.findAllByStoreTypeID(id,date));
    }

    /**
     * GET  /posts/:id : get the "id" post.
     *
     * @param id the id of the postDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postDTO, or with status 404 (Not Found)
     */
    @GetMapping("/posts/{id}")
    @Timed
    public AntiPhoneResponse getPost(@PathVariable Long id) {
        log.debug("REST request to get Post : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        Optional<PostDTO> postDTO = postService.findOne(id);
        if (!postDTO.isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, postDTO.get());
    }

    @GetMapping("/posts/store-groups/{storeGroupID}")
    @Timed
    public AntiPhoneResponse getAllByStoreGroupID(@PathVariable Long storeGroupID) {
        log.debug("REST request to get Post : {}", storeGroupID);
        if (storeGroupID == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (postService.findAllByStoreGroupID(storeGroupID).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group  " + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, postService.findAllByStoreGroupID(storeGroupID));
    }

    /**
     * DELETE  /posts/:id : delete the "id" post.
     *
     * @param id the id of the postDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posts/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!postService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.ID_NOT_EXIST);
        postService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete " + id + " successfully");
    }

    @GetMapping("/posts/store-types/count")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public AntiPhoneResponse getCountStoreTypes() {
        log.debug("REST request to get all Stores");
        return new AntiPhoneResponse(200,Messages.FIND_SUCCESFULLY, postService.countPostByStoreType());
    }
}
