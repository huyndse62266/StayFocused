package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.security.AuthoritiesConstants;
import huynd.service.StoreGroupService;
import huynd.service.StoreService;
import huynd.service.dto.StoreDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.service.dto.response.LocationStoreResponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * REST controller for managing ListStore.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "listStore";

    private final StoreService storeService;

    @Autowired
    StoreGroupService storeGroupService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * POST  /list-stores : Create a new listStore.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO, or with status 400 (Bad Request) if the listStore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse createListStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save ListStore : {}", storeDTO);
        if(StringUtils.isEmpty(storeDTO.getStoreName()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store name "+Messages.REQUEST_PARAM_REQUIRED);
        if(StringUtils.isEmpty(storeDTO.getStoreAddress()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store address "+Messages.REQUEST_PARAM_REQUIRED);
        if(storeDTO.getStoreGroup().getStoreGroupID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Group ID"+Messages.REQUEST_PARAM_REQUIRED);
        if(!storeGroupService.findOne(storeDTO.getStoreGroup().getStoreGroupID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup "+Messages.REQUEST_PARAM_EXIST);
        String regex = "\\d+.+\\d+";
        if(!storeDTO.getStoreLatitude().matches(regex))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Latitude "+Messages.PARAM_INVALID);
        if(!storeDTO.getStoreLongitude().matches(regex))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Longitude\n "+Messages.PARAM_INVALID);
        StoreDTO result = storeService.save(storeDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.CREATE_SUCCESFULLY,result);
    }

    /**
     * PUT  /list-stores : Updates an existing listStore.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 400 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse updateStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update ListStore : {}", storeDTO);
        if(storeDTO.getStoreID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"List store ID "+Messages.REQUEST_PARAM_REQUIRED);
        if(!storeService.findOne(storeDTO.getStoreID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup "+Messages.ID_NOT_EXIST);
        StoreDTO existListStore = storeService.findOne(storeDTO.getStoreID()).get();
        if(StringUtils.isNotEmpty(storeDTO.getStoreAddress()))
            existListStore.setStoreAddress(storeDTO.getStoreAddress());
        if(StringUtils.isNotEmpty(storeDTO.getStoreName()))
            existListStore.setStoreName(storeDTO.getStoreName());
        if(!storeGroupService.findOne(storeDTO.getStoreGroup().getStoreGroupID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup "+Messages.REQUEST_PARAM_EXIST);
        if(storeDTO.getStoreGroup().getStoreGroupID() != null)
            existListStore.setStoreID(storeDTO.getStoreID());
        String regex = "\\d+.+\\d+";
        if(storeDTO.getStoreLatitude() != null){
            if(!storeDTO.getStoreLatitude().matches(regex))
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Latitude "+Messages.PARAM_INVALID);
            existListStore.setStoreLatitude(storeDTO.getStoreLatitude());
        }
        if(storeDTO.getStoreLongitude() != null){
            if(!storeDTO.getStoreLatitude().matches(regex))
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Longitude "+Messages.PARAM_INVALID);
            existListStore.setStoreLongitude(storeDTO.getStoreLongitude());
        }
        StoreDTO result = storeService.save(existListStore);
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.UPDATE_SUCCESFULLY,result);

    }

    /**
     * GET  /list-stores : get all the listStores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of listStores in body
     */
    @GetMapping("/stores")
    @Timed
    public AntiPhoneResponse getAllStores() {
        log.debug("REST request to get all ListStores");
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.FIND_SUCCESFULLY, storeService.findAll());
    }

    /**
     * GET  /list-stores/:id : get the "id" listStore.
     *
     * @param id the id of the listStoreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listStoreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    @Timed
    public AntiPhoneResponse getStore(@PathVariable Long id) {
        log.debug("REST request to get ListStore : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, "List StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!storeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup "+Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.FIND_SUCCESFULLY, storeService.findOne(id).get());
    }

    @GetMapping("/stores/location/{id}")
    @Timed
    public AntiPhoneResponse getLocationStore(@PathVariable Long id) {
        log.debug("REST request to get ListStore : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, "List StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!storeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup "+Messages.ID_NOT_EXIST);
        StoreDTO storeDTO = storeService.findOne(id).get();
        LocationStoreResponse storeResponse = new LocationStoreResponse();
        storeResponse.setStoreLatitude(storeDTO.getStoreLatitude());
        storeResponse.setStoreLongitude(storeDTO.getStoreLongitude());
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.FIND_SUCCESFULLY, storeResponse);
    }

    @GetMapping("/stores/store-groups/{storeGroupID}")
    @Timed
    public AntiPhoneResponse getListStoreByStoreGroupID(@PathVariable Long storeGroupID) {
        log.debug("REST request to get ListStore : {}", storeGroupID);
        if (storeGroupID == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, " Store Group ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(storeService.findAllByStoreGroupID(storeGroupID).size()==0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.LIST_EMPTY);
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.FIND_SUCCESFULLY, storeService.findAllByStoreGroupID(storeGroupID));
    }

    /**
     * DELETE  /list-stores/:id : delete the "id" listStore.
     *
     * @param id the id of the listStoreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete ListStore : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, "List StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!storeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"List StoreGroup "+Messages.ID_NOT_EXIST);
        storeService.delete(id);
        return new  AntiPhoneResponse(Constants.STATUS_OK,Messages.DELETE_SUCCESFULLY,"Delete ID = "+id+" is successfully");
    }
}
