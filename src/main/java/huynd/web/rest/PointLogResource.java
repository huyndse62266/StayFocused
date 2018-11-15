package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.service.PointLogService;
import huynd.service.PostService;
import huynd.service.UserService;
import huynd.service.VoucherService;
import huynd.service.dto.*;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.service.dto.response.PointLogResponse;
import huynd.service.dto.response.VoucherLogReponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing PointLog.
 */
@RestController
@RequestMapping("/api")
public class PointLogResource {

    private final Logger log = LoggerFactory.getLogger(PointLogResource.class);

    private static final String ENTITY_NAME = "pointLog";

    private final PointLogService pointLogService;

    @Autowired
    UserService userService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    PostService postService;

    public PointLogResource(PointLogService pointLogService) {
        this.pointLogService = pointLogService;
    }

    /**
     * POST  /point-logs : Create a new pointLog.
     *
     * @param pointLogDTO the pointLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pointLogDTO, or with status 400 (Bad Request) if the pointLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/point-logs")
    @Timed
    public AntiPhoneResponse createPointLog(@RequestBody PointLogDTO pointLogDTO) throws URISyntaxException {
        log.debug("REST request to save PointLog : {}", pointLogDTO);
        if (pointLogDTO.getVoucher().getVoucherID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (StringUtils.isEmpty(pointLogDTO.getUser().getUsername()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherService.findOne(pointLogDTO.getVoucher().getVoucherID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.ID_NOT_EXIST);
        if (userService.findByLogin(pointLogDTO.getUser().getUsername()) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        String username = pointLogDTO.getUser().getUsername();
        UserDTO userDTO = userService.findByLogin(pointLogDTO.getUser().getUsername());
        pointLogDTO.setUser(userDTO);
        PointLogDTO result = pointLogService.save(pointLogDTO);
        result.getUser().setUsername(username);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, result);
    }

    /**
     * PUT  /point-logs : Updates an existing pointLog.
     *
     * @param pointLogDTO the pointLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pointLogDTO,
     * or with status 400 (Bad Request) if the pointLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the pointLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/point-logs")
    @Timed
    public AntiPhoneResponse updatePointLog(@RequestBody PointLogDTO pointLogDTO) throws URISyntaxException {
        log.debug("REST request to update PointLog : {}", pointLogDTO);
        if (pointLogDTO.getPointLogID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Point Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (pointLogService.findOne(pointLogDTO.getPointLogID()).isPresent()) {
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Point Log ID " + Messages.ID_NOT_EXIST);
        }
        PointLogDTO existPointLogDTO = pointLogService.findOne(pointLogDTO.getPointLogID()).get();
        if (pointLogDTO.getVoucher().getVoucherID() != null) {
            if (pointLogService.findByVoucherID(pointLogDTO.getVoucher().getVoucherID()).isPresent())
                return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Point ID " + Messages.REQUEST_PARAM_EXIST);
            existPointLogDTO.getVoucher().setVoucherID(pointLogDTO.getVoucher().getVoucherID());
        }
        String username = "";

        if (StringUtils.isNotEmpty(pointLogDTO.getUser().getUsername()))
            username=  pointLogDTO.getUser().getUsername();
            Long id = userService.findByLogin(pointLogDTO.getUser().getUsername()).getId();
            existPointLogDTO.getUser().setId(id);
        if (pointLogDTO.getPointLogDateUsed() != null)
            existPointLogDTO.setPointLogPointSpent(pointLogDTO.getPointLogPointSpent());
        if (pointLogDTO.getPointLogPointSpent() != null)
            existPointLogDTO.setPointLogPointSpent(pointLogDTO.getPointLogPointSpent());
        PointLogDTO result = pointLogService.save(existPointLogDTO);
        result.getUser().setUsername(username);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /point-logs : get all the pointLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pointLogs in body
     */
    @GetMapping("/point-logs")
    @Timed
    public AntiPhoneResponse getAllPointLogs() {
        log.debug("REST request to get all PointLogs");
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, pointLogService.findAll());
    }

    /**
     * GET  /point-logs/:id : get the "id" pointLog.
     *
     * @param id the id of the pointLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pointLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/point-logs/{id}")
    @Timed
    public AntiPhoneResponse getPointLog(@PathVariable Long id) {
        log.debug("REST request to get PointLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (pointLogService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Log " + Messages.ID_NOT_EXIST);
        Optional<PointLogDTO> voucherLogDTO = pointLogService.findOne(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherLogDTO);
    }

    @GetMapping("/point-logs/vouchers/{voucherNumber}")
    @Timed
    public AntiPhoneResponse getPointLogByVoucherNumber(@PathVariable String voucherNumber) {
        log.debug("REST request to get PointLog : {}", voucherNumber);
        if (voucherNumber == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Number " + Messages.REQUEST_PARAM_REQUIRED);
        Long voucherID = voucherService.findOneByVoucherNumber(voucherNumber).get().getVoucherID();
        if (!pointLogService.findByVoucherID(voucherID).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Number " + Messages.ID_NOT_EXIST);
        Optional<PointLogDTO> pointLogDTO = pointLogService.findByVoucherID(voucherID);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, pointLogDTO);
    }

    @GetMapping("/point-logs/users/{username}")
    @Timed
    public AntiPhoneResponse findAllByUsername(@PathVariable String username) {
        log.debug("REST request to get VoucherLog : {}", username);
        if (StringUtils.isEmpty(username))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username" + Messages.REQUEST_PARAM_REQUIRED);
        if (userService.findByLogin(username) == null)
            return new AntiPhoneResponse(Constants.STATUS_OK, Messages.REQUEST_PARAM_ERROR, null);
        Long id = userService.findByLogin(username).getId();
        List<PointLogDTO> pointLogDTOS= pointLogService.findByUsername(id);
        if (pointLogDTOS.size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_OK, Messages.REQUEST_PARAM_ERROR, null);
        List<PointLogResponse> pointLogResponses = new ArrayList<>();
        for (int i = 0; i < pointLogDTOS.size(); i++) {
            Timestamp timestamp = pointLogDTOS.get(i).getPointLogDateUsed();
            timestamp.setTime(timestamp.getTime() + (1000*60*60*7));
            pointLogDTOS.get(i).setPointLogDateUsed(timestamp);
            VoucherDTO voucherDTO = voucherService.findOne(pointLogDTOS.get(i).getVoucher().getVoucherID()).get();
            PostDTO postDTO = postService.findOne(voucherDTO.getPost().getPostID()).get();

            PointLogResponse pointLogResponse = new PointLogResponse(pointLogDTOS.get(i).getPointLogID(),pointLogDTOS.get(i).getPointLogPointSpent(),pointLogDTOS.get(i).getPointLogDateUsed(),
                username,voucherDTO.getVoucherNumber(),postDTO.getPostTitle(),postDTO.getPostBanner(),postDTO.getPostDiscountRate(),postDTO.getStoreGroup().getStoreGroupName(),postDTO.getStoreGroup().getStoreGroupLogo());
            pointLogResponses.add(pointLogResponse);
        }
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, pointLogResponses);
    }

    /**
     * DELETE  /point-logs/:id : delete the "id" pointLog.
     *
     * @param id the id of the pointLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/point-logs/{id}")
    @Timed
    public AntiPhoneResponse deletePointLog(@PathVariable Long id) {
        log.debug("REST request to delete PointLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Point Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        pointLogService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete voucher id = " + id + " successfully");
    }

    @GetMapping("/point-logs/users/total-point/{username}")
    @Timed
    public AntiPhoneResponse totalPointByUser(@PathVariable String username) {
        log.debug("REST request to get PointLog : {}", username);
        if (username == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if(userService.findByLogin(username) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        Long id = userService.findByLogin(username).getId();
        Long totalPointLog = 0L;
        if(pointLogService.findByUsername(id).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, totalPointLog);

        if(pointLogService.sumPointByUser(id) != null)
            totalPointLog = pointLogService.sumPointByUser(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, totalPointLog);
    }
}
