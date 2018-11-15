package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.security.AuthoritiesConstants;
import huynd.service.StoreGroupService;
import huynd.service.StoreTypeService;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StoreGroup.
 */
@RestController
@RequestMapping("/api")
public class StoreGroupResource {

    private final Logger log = LoggerFactory.getLogger(StoreGroupResource.class);

    private static final String ENTITY_NAME = "store";

    private final StoreGroupService storeGroupService;

    @Autowired
    StoreTypeService storeTypeService;

    public StoreGroupResource(StoreGroupService storeGroupService) {
        this.storeGroupService = storeGroupService;
    }

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeGroupDTO the storeGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeGroupDTO, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-groups")
    @Timed

    public AntiPhoneResponse createStore(@RequestBody StoreGroupDTO storeGroupDTO) throws URISyntaxException {
        log.debug("REST request to save StoreGroup : {}", storeGroupDTO);
        if (StringUtils.isEmpty(storeGroupDTO.getStoreGroupMail()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Mail " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(storeGroupDTO.getStoreGroupDescription()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Description " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(storeGroupDTO.getStoreGroupName()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Name  " + Messages.REQUEST_PARAM_REQUIRED);
        if (storeGroupDTO.getStoreGroupPhone() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Phone  " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(storeGroupDTO.getStoreGroupWeb()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Web  " + Messages.REQUEST_PARAM_REQUIRED);
        String emailPattern = "\\w+@\\w+[.]\\w+";
        if(!storeGroupDTO.getStoreGroupMail().matches(emailPattern))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Email  " + Messages.PARAM_INVALID);
        String webPattern = "\\w+.\\w+[.]\\w+";
        if(!storeGroupDTO.getStoreGroupWeb().matches(webPattern))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group Web  " + Messages.PARAM_INVALID);
        if (StringUtils.isEmpty(storeGroupDTO.getStoreType().getStoreTypeID()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Type ID " + Messages.ID_NOT_EXIST);
        if (!storeTypeService.findOne(storeGroupDTO.getStoreType().getStoreTypeID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Type " + Messages.ID_NOT_EXIST);
        StoreGroupDTO result = storeGroupService.save(storeGroupDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY, result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeGroupDTO the storeGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeGroupDTO,
     * or with status 400 (Bad Request) if the storeGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-groups")
    @Timed
    public AntiPhoneResponse updateStore(@RequestBody StoreGroupDTO storeGroupDTO) throws URISyntaxException {
        log.debug("REST request to update StoreGroup : {}", storeGroupDTO);
        if (storeGroupDTO.getStoreGroupID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID  " + Messages.REQUEST_PARAM_REQUIRED);
        if (!storeGroupService.findOne(storeGroupDTO.getStoreGroupID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID not found");
        StoreGroupDTO storeExist = storeGroupService.findOne(storeGroupDTO.getStoreGroupID()).get();
        if (StringUtils.isNotEmpty(storeGroupDTO.getStoreGroupMail()))
            storeExist.setStoreGroupMail(storeGroupDTO.getStoreGroupMail());
        if (StringUtils.isNotEmpty(storeGroupDTO.getStoreGroupDescription()))
            storeExist.setStoreGroupDescription(storeGroupDTO.getStoreGroupDescription());
        if (StringUtils.isNotEmpty(storeGroupDTO.getStoreGroupName()))
            storeExist.setStoreGroupName(storeGroupDTO.getStoreGroupName());
        if (storeGroupDTO.getStoreGroupPhone() == null)
            storeExist.setStoreGroupPhone(storeGroupDTO.getStoreGroupPhone());
        if (StringUtils.isNotEmpty(storeGroupDTO.getStoreGroupWeb()))
            storeExist.setStoreGroupWeb(storeGroupDTO.getStoreGroupWeb());
        if(StringUtils.isNotEmpty(storeGroupDTO.getStoreGroupLogo()))
            storeExist.setStoreGroupLogo(storeGroupDTO.getStoreGroupLogo());
        StoreGroupDTO result = storeGroupService.save(storeExist);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/store-groups")
    @Timed
    public AntiPhoneResponse getAllStores() {
        log.debug("REST request to get all Stores");
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());

        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        System.out.println(RandomStringUtils.randomAlphabetic(10).toUpperCase());
        return new AntiPhoneResponse(200, Messages.FIND_SUCCESFULLY, storeGroupService.findAll());
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/store-groups/{id}")
    @Timed
    public AntiPhoneResponse getStore(@PathVariable Long id) {
        log.debug("REST request to get StoreGroup : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!storeGroupService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group " + Messages.ID_NOT_EXIST);
        Optional<StoreGroupDTO> storeDTO = storeGroupService.findOne(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, storeDTO.get());
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-groups/{id}")
    @Timed
    public AntiPhoneResponse deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete StoreGroup : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!storeGroupService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Group " + Messages.ID_NOT_EXIST);
        storeGroupService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete ID = " + id + " is successfully");
    }

    @GetMapping("/store-groups/store-types/{id}")
    @Timed
    public AntiPhoneResponse getStoresByStoreType(@PathVariable String id) {
        log.debug("REST request to get all Stores by Group Type: {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Type " + Messages.REQUEST_PARAM_REQUIRED);
        if (!storeTypeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Store Type " + Messages.ID_NOT_EXIST);
        if (storeGroupService.findAllByStoreType(id).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.LIST_EMPTY);
        List<StoreGroupDTO> storeGroups = storeGroupService.findAllByStoreType(id);
        return new AntiPhoneResponse(200, Messages.FIND_SUCCESFULLY, storeGroups);
    }
}
