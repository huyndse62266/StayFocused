package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.service.UserService;
import huynd.service.UserStoreGroupService;
import huynd.web.rest.errors.BadRequestAlertException;
import huynd.web.rest.util.HeaderUtil;
import huynd.service.dto.UserStoreGroupDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserStoreGroup.
 */
@RestController
@RequestMapping("/api")
public class UserStoreGroupResource {

    private final Logger log = LoggerFactory.getLogger(UserStoreGroupResource.class);

    private static final String ENTITY_NAME = "userStoreGroup";

    private UserStoreGroupService userStoreGroupService;

    @Autowired
    UserService userService;

    public UserStoreGroupResource(UserStoreGroupService userStoreGroupService) {
        this.userStoreGroupService = userStoreGroupService;
    }

    /**
     * POST  /user-store-groups : Create a new userStoreGroup.
     *
     * @param userStoreGroupDTO the userStoreGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStoreGroupDTO, or with status 400 (Bad Request) if the userStoreGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-store-groups")
    @Timed
    public ResponseEntity<UserStoreGroupDTO> createUserStoreGroup(@RequestBody UserStoreGroupDTO userStoreGroupDTO) throws URISyntaxException {
        log.debug("REST request to save UserStoreGroup : {}", userStoreGroupDTO);
        if (userStoreGroupDTO.getUserId() != null) {
            throw new BadRequestAlertException("A new userStoreGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserStoreGroupDTO result = userStoreGroupService.save(userStoreGroupDTO);
        return ResponseEntity.created(new URI("/api/user-store-groups/" + result.getUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getUserId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-store-groups : Updates an existing userStoreGroup.
     *
     * @param userStoreGroupDTO the userStoreGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStoreGroupDTO,
     * or with status 400 (Bad Request) if the userStoreGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the userStoreGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-store-groups")
    @Timed
    public ResponseEntity<UserStoreGroupDTO> updateUserStoreGroup(@RequestBody UserStoreGroupDTO userStoreGroupDTO) throws URISyntaxException {
        log.debug("REST request to update UserStoreGroup : {}", userStoreGroupDTO);
        if (userStoreGroupDTO.getUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserStoreGroupDTO result = userStoreGroupService.save(userStoreGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userStoreGroupDTO.getUserId().toString()))
            .body(result);
    }

    /**
     * GET  /user-store-groups : get all the userStoreGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStoreGroups in body
     */
    @GetMapping("/user-store-groups")
    @Timed
    public List<UserStoreGroupDTO> getAllUserStoreGroups() {
        log.debug("REST request to get all UserStoreGroups");
        return userStoreGroupService.findAll();
    }

    /**
     * GET  /user-store-groups/:id : get the "id" userStoreGroup.
     *
     * @param id the id of the userStoreGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStoreGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-store-groups/{id}")
    @Timed
    public ResponseEntity<UserStoreGroupDTO> getUserStoreGroup(@PathVariable Long id) {
        log.debug("REST request to get UserStoreGroup : {}", id);
        Optional<UserStoreGroupDTO> userStoreGroupDTO = userStoreGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userStoreGroupDTO);
    }

    /**
     * DELETE  /user-store-groups/:id : delete the "id" userStoreGroup.
     *
     * @param id the id of the userStoreGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-store-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserStoreGroup(@PathVariable Long id) {
        log.debug("REST request to delete UserStoreGroup : {}", id);
        userStoreGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/user-store-groups/users/{username}")
    @Timed
    public Long getStoreGroupIDByUsername(@PathVariable String username) {
        log.debug("REST request to get UserStoreGroup : {}", username);
        Long id = userService.findByLogin(username).getId();
        return userStoreGroupService.findByUsername(id).get().getStoreGroupID();
    }
}
