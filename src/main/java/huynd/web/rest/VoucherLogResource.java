package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.security.AuthoritiesConstants;
import huynd.service.*;
import huynd.service.dto.*;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.service.dto.response.VoucherLogReponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VoucherLog.
 */
@RestController
@RequestMapping("/api")
public class VoucherLogResource {

    private final Logger log = LoggerFactory.getLogger(VoucherLogResource.class);

    private static final String ENTITY_NAME = "voucherLog";

    private final VoucherLogService voucherLogService;

    @Autowired
    UserService userService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    PostService postService;

    @Autowired
    PointLogService pointLogService;

    public VoucherLogResource(VoucherLogService voucherLogService) {
        this.voucherLogService = voucherLogService;
    }

    /**
     * POST  /voucher-logs : Create a new voucherLog.
     *
     * @param voucherLogDTO the voucherLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherLogDTO, or with status 400 (Bad Request) if the voucherLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-logs")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse createVoucherLog(@RequestBody VoucherLogDTO voucherLogDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherLog : {}", voucherLogDTO);
        if (voucherLogDTO.getVoucher().getVoucherID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(voucherLogDTO.getUser().getUsername()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherService.findOne(voucherLogDTO.getVoucher().getVoucherID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.ID_NOT_EXIST);
        if (userService.findByLogin(voucherLogDTO.getUser().getUsername()) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        String username = voucherLogDTO.getUser().getUsername();
        Long id = userService.findByLogin(voucherLogDTO.getUser().getUsername()).getId();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        voucherLogDTO.setUser(userDTO);
        voucherLogDTO.setVoucherLogUserUsed(false);
        VoucherLogDTO result = voucherLogService.save(voucherLogDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY, result);
    }

    /**
     * PUT  /voucher-logs : Updates an existing voucherLog.
     *
     * @param voucherLogDTO the voucherLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherLogDTO,
     * or with status 400 (Bad Request) if the voucherLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-logs")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse updateVoucherLog(@RequestBody VoucherLogDTO voucherLogDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherLog : {}", voucherLogDTO);
        if (voucherLogDTO.getVoucherLogID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherLogService.findOne(voucherLogDTO.getVoucherLogID()).isPresent()) {
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log ID " + Messages.ID_NOT_EXIST);
        }

        VoucherLogDTO existVoucherLogDTO = voucherLogService.findOne(voucherLogDTO.getVoucherLogID()).get();
        if (voucherLogDTO.getVoucher().getVoucherID() != null) {
            if (!voucherLogService.findByVoucherID(voucherLogDTO.getVoucher().getVoucherID()).isPresent())
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_EXIST);
            VoucherDTO voucherDTO = new VoucherDTO();
            voucherDTO.setVoucherID(voucherLogDTO.getVoucher().getVoucherID());
            existVoucherLogDTO.setVoucher(voucherDTO);
        }
        Long id = 0L;
        String username = "";
        if (StringUtils.isNotEmpty(voucherLogDTO.getUser().getUsername())){
            username = voucherLogDTO.getUser().getUsername();
            if(userService.findByLogin(username) == null)
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
            id = userService.findByLogin(voucherLogDTO.getUser().getUsername()).getId();

            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            existVoucherLogDTO.setUser(userDTO);
        }

        if (voucherLogDTO.getVoucherLogDateUsed() != null)
            existVoucherLogDTO.setVoucherLogDateUsed(voucherLogDTO.getVoucherLogDateUsed());
        if (voucherLogDTO.getVoucherLogUserUsed() != null)
            existVoucherLogDTO.setVoucherLogUserUsed(voucherLogDTO.getVoucherLogUserUsed());
        VoucherLogDTO result = voucherLogService.save(existVoucherLogDTO);
//        result.setUsername(voucherLogDTO.getUsername());
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /voucher-logs : get all the voucherLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voucherLogs in body
     */
    @GetMapping("/voucher-logs")
    @Timed
    public AntiPhoneResponse getAllVoucherLogs() {
        log.debug("REST request to get all VoucherLogs");
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherLogService.findAll());
    }

    /**
     * GET  /voucher-logs/:id : get the "id" voucherLog.
     *
     * @param id the id of the voucherLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-logs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.USER})
    public AntiPhoneResponse getVoucherLogByVoucherLogID(@PathVariable Long id) {
        log.debug("REST request to get VoucherLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherLogService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log " + Messages.ID_NOT_EXIST);
        VoucherLogDTO voucherLogDTO = voucherLogService.findOne(id).get();
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherLogDTO);
    }

    @GetMapping("/voucher-logs/vouchers/{voucherNumber}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse getVoucherLogByVoucherName(@PathVariable String voucherNumber) {
        log.debug("REST request to get VoucherLog : {}", voucherNumber);
        if (StringUtils.isEmpty(voucherNumber))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        Long voucherID = voucherService.findOneByVoucherNumber(voucherNumber).get().getVoucherID();
        Optional<VoucherLogDTO> voucherLogDTO = voucherLogService.findByVoucherID(voucherID);
        if (!voucherLogDTO.isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher  " + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherLogDTO.get());
    }

    @GetMapping("/voucher-logs/users/{username}")
    @Timed
    public AntiPhoneResponse getAllVoucherLogByUsername(@PathVariable String username) {
        log.debug("REST request to get VoucherLog : {}", username);
        if (StringUtils.isEmpty(username))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username" + Messages.REQUEST_PARAM_REQUIRED);
        if (userService.findByLogin(username) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        Long id = userService.findByLogin(username).getId();
        List<VoucherLogDTO>  voucherLogDTOS= voucherLogService.findByUsername(id);
        if (voucherLogDTOS.size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username" + Messages.ID_NOT_EXIST);
        List<VoucherLogReponse> voucherLogReponses = new ArrayList<>();
        for (int i = 0; i < voucherLogDTOS.size(); i++) {
            PostDTO postDTO = postService.findOne(voucherLogDTOS.get(i).getVoucher().getPost().getPostID()).get();
//            PointLogDTO pointLogDTO = pointLogService.findByVoucherID(voucherLogDTOS.get(i).getVoucher().getVoucherID()).get();
            VoucherLogReponse voucherLogReponse = new VoucherLogReponse(voucherLogDTOS.get(i).getVoucherLogID(), voucherLogDTOS.get(i).getVoucherLogUserUsed(),
                voucherLogDTOS.get(i).getVoucherLogDateUsed(),voucherLogDTOS.get(i).getUser().getUsername(),voucherLogDTOS.get(i).getVoucher().getVoucherNumber(),
                postDTO.getPostTitle(), postDTO.getPostBanner(), postDTO.getPostDiscountRate(), postDTO.getStoreGroup().getStoreGroupName(), postDTO.getStoreGroup().getStoreGroupLogo());
            voucherLogReponses.add(voucherLogReponse);

        }
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherLogReponses);
    }


    /**
     * DELETE  /voucher-logs/:id : delete the "id" voucherLog.
     *
     * @param id the id of the voucherLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-logs/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse deleteVoucherLogByVoucherLogID(@PathVariable Long id) {
        log.debug("REST request to delete VoucherLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        voucherLogService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete " + id + " successfully");
    }

    @DeleteMapping("/voucher-logs/vouchers/{voucherNumber}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public AntiPhoneResponse deleteVoucherLogByVoucherNumber(@PathVariable String voucherNumber) {
        log.debug("REST request to delete VoucherLog : {}", voucherNumber);
        if (StringUtils.isEmpty(voucherNumber))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        Long voucherID = voucherService.findOneByVoucherNumber(voucherNumber).get().getVoucherID();
        voucherLogService.deleteByVoucherID(voucherID);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete Voucher Log with Voucher Number: " + voucherNumber + " successfully");
    }
}
