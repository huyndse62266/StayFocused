package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.security.AuthoritiesConstants;
import huynd.service.StoreTypeService;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.web.rest.errors.BadRequestAlertException;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.HeaderUtil;
import huynd.service.dto.StoreTypeDTO;
import huynd.web.rest.util.Messages;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StoreType.
 */
@RestController
@RequestMapping("/api")
public class StoreTypeResource {

    private final Logger log = LoggerFactory.getLogger(StoreTypeResource.class);

    private static final String ENTITY_NAME = "storeType";

    private final StoreTypeService storeTypeService;

    public StoreTypeResource(StoreTypeService storeTypeService) {
        this.storeTypeService = storeTypeService;
    }

    /**
     * POST  /store-types : Create a new storeType.
     *
     * @param storeTypeDTO the storeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeTypeDTO, or with status 400 (Bad Request) if the storeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-types")
    @Timed
    public AntiPhoneResponse createStoreType(@RequestBody StoreTypeDTO storeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save StoreType : {}", storeTypeDTO);
        if(StringUtils.isEmpty(storeTypeDTO.getStoreTypeID()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(StringUtils.isEmpty(storeTypeDTO.getStoreTypeName()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type Name  " + Messages.REQUEST_PARAM_REQUIRED);
        if(storeTypeService.findOne(storeTypeDTO.getStoreTypeID()).isPresent()){
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type ID " + Messages.REQUEST_PARAM_EXIST);
        }
        StoreTypeDTO result = storeTypeService.save(storeTypeDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY,result);
    }

    /**
     * PUT  /store-types : Updates an existing storeType.
     *
     * @param storeTypeDTO the storeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeTypeDTO,
     * or with status 400 (Bad Request) if the storeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-types")
    @Timed
    public AntiPhoneResponse updateStoreType(@RequestBody StoreTypeDTO storeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update StoreType : {}", storeTypeDTO);
        if(StringUtils.isEmpty(storeTypeDTO.getStoreTypeID()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(storeTypeService.findOne(storeTypeDTO.getStoreTypeID()).isPresent()){
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type ID " + Messages.ID_NOT_EXIST);
        }
        StoreTypeDTO existStoreType = storeTypeService.findOne(storeTypeDTO.getStoreTypeID()).get();
        if(StringUtils.isNotEmpty(storeTypeDTO.getStoreTypeName()))
            existStoreType.setStoreTypeName(storeTypeDTO.getStoreTypeName());
        StoreTypeDTO result = storeTypeService.save(existStoreType);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY,result);
    }

    /**
     * GET  /store-types : get all the storeTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storeTypes in body
     */
    @GetMapping("/store-types")
    @Timed
    public AntiPhoneResponse getAllStoreTypes() {
        log.debug("REST request to get all Stores");
        return new AntiPhoneResponse(200,Messages.FIND_SUCCESFULLY, storeTypeService.findAll());
    }

    /**
     * GET  /store-types/:id : get the "id" storeType.
     *
     * @param id the id of the storeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/store-types/{id}")
    @Timed
    public AntiPhoneResponse getStoreType(@PathVariable String id) {
        log.debug("REST request to get StoreType : {}", id);
        if (StringUtils.isEmpty(id))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, "Store Type ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!storeTypeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"Store Type ID not found");
        StoreTypeDTO storeTypeDTO = storeTypeService.findOne(id).get();
        return new AntiPhoneResponse(Constants.STATUS_OK,Messages.FIND_SUCCESFULLY,storeTypeDTO);
    }

    /**
     * DELETE  /store-types/:id : delete the "id" storeType.
     *
     * @param id the id of the storeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-types/{id}")
    @Timed
    public AntiPhoneResponse deleteStoreType(@PathVariable String id) {
        log.debug("REST request to delete Store Type : {}", id);
        if (StringUtils.isEmpty(id))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST,  Messages.REQUEST_PARAM_ERROR, "StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!storeTypeService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR,"StoreGroup ID not found");
        storeTypeService.delete(id);
        return new  AntiPhoneResponse(Constants.STATUS_OK,Messages.DELETE_SUCCESFULLY,"Delete Store Type ID = "+id+" is successfully");
    }

    @GetMapping("/store-types/count")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse getCountStoreTypes() {
        log.debug("REST request to get all Stores");
        return new AntiPhoneResponse(200,Messages.FIND_SUCCESFULLY, storeTypeService.findAllStoreType());
    }

}
