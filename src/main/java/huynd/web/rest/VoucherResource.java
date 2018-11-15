package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.domain.PointLog;
import huynd.domain.User;
import huynd.security.AuthoritiesConstants;
import huynd.service.*;
import huynd.service.dto.*;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.service.dto.response.NumberOfVoucher;
import huynd.service.dto.response.VoucherPointResponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

/**
 * REST controller for managing Voucher.
 */
@RestController
@RequestMapping("/api")
public class VoucherResource {

    private final Logger log = LoggerFactory.getLogger(VoucherResource.class);

    private static final String ENTITY_NAME = "voucher";

    private final VoucherService voucherService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    VoucherLogService voucherLogService;

    @Autowired
    ActivitiesLogService activitiesLogService;

    @Autowired
    PointLogService pointLogService;





    public VoucherResource(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * POST  /vouchers : Create a new voucher.
     *
     * @param voucherDTO the voucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherDTO, or with status 400 (Bad Request) if the voucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vouchers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse createVoucher(@RequestBody VoucherDTO voucherDTO) throws URISyntaxException {
        log.debug("REST request to save Voucher : {}", voucherDTO);
        if (StringUtils.isEmpty(voucherDTO.getVoucherNumber()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher number " + Messages.REQUEST_PARAM_REQUIRED);
        voucherDTO.setVoucherStatus(false);
        if (voucherService.findOneByVoucherNumber(voucherDTO.getVoucherNumber()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher Name " + Messages.REQUEST_PARAM_EXIST);
        if (voucherDTO.getPost().getPostID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!postService.findOne(voucherDTO.getPost().getPostID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post " + Messages.ID_NOT_EXIST);

        VoucherDTO result = voucherService.save(voucherDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY, result);
    }

    /**
     * PUT  /vouchers : Updates an existing voucher.
     *
     * @param voucherDTO the voucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherDTO,
     * or with status 400 (Bad Request) if the voucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vouchers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse updateVoucher(@RequestBody VoucherDTO voucherDTO) throws URISyntaxException {
        log.debug("REST request to update Voucher : {}", voucherDTO);
        if (voucherDTO.getVoucherID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherService.findOne(voucherDTO.getVoucherID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.ID_NOT_EXIST);
        VoucherDTO existVoucher = voucherService.findOne(voucherDTO.getVoucherID()).get();
        if (StringUtils.isNotEmpty(voucherDTO.getVoucherNumber()))
            existVoucher.setVoucherNumber(voucherDTO.getVoucherNumber());
        if (voucherDTO.isVoucherStatus() != null)
            existVoucher.setVoucherStatus(voucherDTO.isVoucherStatus());
        if (voucherDTO.getPost().getPostID() != null)
            existVoucher.setPost(voucherDTO.getPost());

        VoucherDTO result = voucherService.save(voucherDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /vouchers : get all the vouchers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vouchers in body
     */
    @GetMapping("/vouchers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse getAllVouchers() {
        log.debug("REST request to get all Vouchers");
        List<VoucherDTO> listVoucher = voucherService.findAll();
        for (int i = 0; i < listVoucher.size(); i++) {
            PostDTO postDTO = postService.findOne(listVoucher.get(i).getPost().getPostID()).get();
            postDTO.setPostRemainNumberVoucher(voucherService.countUnusedVoucher(listVoucher.get(i).getPost().getPostID()));
            postDTO.setPostTotalNumberVoucher(voucherService.countTotalVoucher(listVoucher.get(i).getPost().getPostID()));
            postService.save(postDTO);
        }
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherService.findAll());
    }

    /**
     * GET  /vouchers/:id : get the "id" voucher.
     *
     * @param id the id of the voucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vouchers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse getVoucher(@PathVariable Long id) {
        log.debug("REST request to get Voucher : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        Optional<VoucherDTO> voucherDTO = voucherService.findOne(id);
        if (!voucherDTO.isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher " + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherDTO.get());
    }

    /**
     * DELETE  /vouchers/:id : delete the "id" voucher.
     *
     * @param id the id of the voucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vouchers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse deleteVoucher(@PathVariable Long id) {
        log.debug("REST request to delete Voucher : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Voucher " + Messages.ID_NOT_EXIST);
        voucherService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete " + id + " successfully");
    }


    @GetMapping("/vouchers/count-unused/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse countUnused(@PathVariable Long id) {
        log.debug("REST request to get number of unused Voucher : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        Long totalVoucher = voucherService.countTotalVoucher(id);
        Long unusedVoucher = voucherService.countUnusedVoucher(id);
        Long usedVoucher = totalVoucher - unusedVoucher;
        return new AntiPhoneResponse(Constants.STATUS_OK, "Get successfully", new NumberOfVoucher(totalVoucher, usedVoucher, unusedVoucher));
    }

    @GetMapping("/vouchers/get-voucher-number/")
    @Timed
    public AntiPhoneResponse getVoucherNumber(@RequestParam("postID") Long postID, @RequestParam("username") String username) {
        log.debug("Request to get voucher name : {}", postID);
        if (postID == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "StoreGroup ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (!voucherService.getVoucherName(postID).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.VOUCHER_EMPTY, null);
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        if(postService.findOne(postID).get().getPostEndDate().compareTo(date) <0);
        VoucherDTO voucherDTO = voucherService.getVoucherName(postID).get();;
        UserDTO userDTO = userService.findByLogin(username);
        Long totalPointLog = Long.valueOf("0");
        Long totalActivitesLog = Long.valueOf("0");
        PostDTO postDTO = postService.findOne(postID).get();
        if(pointLogService.sumPointByUser(userDTO.getId()) != null)
            totalPointLog = pointLogService.sumPointByUser(userDTO.getId());
        if(activitiesLogService.sumTimeByUser(userDTO.getId())!= null)
            totalActivitesLog = activitiesLogService.sumPointByUser(userDTO.getId());
        if(postDTO.getPostPointRequired() > (totalActivitesLog-totalPointLog))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.POINT_REQUIRED, Messages.POINT_REQUIRED);
        String voucherNumber = voucherDTO.getVoucherNumber();
        voucherDTO.setVoucherStatus(true);

//        Optional<User> user = userService.getUserWithAuthorities();


        VoucherLogDTO voucherLogDTO = new VoucherLogDTO();
        voucherLogDTO.setVoucher(voucherDTO);
        voucherLogDTO.setVoucherLogUserUsed(false);
        voucherLogDTO.setUser(userDTO);

        Date getDate = new Date();

        PointLogDTO pointLogDTO = new PointLogDTO();
        pointLogDTO.setUser(userDTO);
        pointLogDTO.setVoucher(voucherDTO);
        pointLogDTO.setPointLogPointSpent(postDTO.getPostPointRequired());
        pointLogDTO.setPointLogDateUsed(new Timestamp(getDate.getTime()));


        voucherService.save(voucherDTO);
        voucherLogService.save(voucherLogDTO);
        pointLogService.save(pointLogDTO);

        postDTO.setPostRemainNumberVoucher(voucherService.countUnusedVoucher(voucherDTO.getPost().getPostID()));
        postDTO.setPostTotalNumberVoucher(voucherService.countTotalVoucher(voucherDTO.getPost().getPostID()));
        postService.save(postDTO);

        if (activitiesLogService.listActivitesByUsername(userDTO.getId()).size() == 0)
            return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.LIST_EMPTY);
        Long sumPointEarn = 0L;
        if (activitiesLogService.sumPointByUser(userDTO.getId()) != null)
            sumPointEarn = activitiesLogService.sumPointByUser(userDTO.getId());
        Long sumPointSpent = 0L;
        if (pointLogService.sumPointByUser(userDTO.getId()) != null)
            sumPointSpent = pointLogService.sumPointByUser(userDTO.getId());
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.GET_VOUCHER_MESSAGE, new VoucherPointResponse(voucherNumber,sumPointSpent, sumPointEarn - sumPointSpent, sumPointEarn));
    }

    @GetMapping("/vouchers/posts/{postID}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse getAllVouchersByPostID(@PathVariable Long postID) {
        log.debug("REST request to get all Vouchers");
        if (postID == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        if (voucherService.findAllByPostID(postID).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID" + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherService.findAllByPostID(postID));
    }

    @GetMapping("/vouchers/store-groups/{storeGroupID}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.SHOP_STAFF})
    public AntiPhoneResponse getAllVouchersByStoreGroup(@PathVariable Long storeGroupID) {
        log.debug("REST request to get all Vouchers");
        if (storeGroupID == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Post ID " + Messages.REQUEST_PARAM_REQUIRED);
        List<PostDTO> postDTOS = postService.findAllByStoreGroupID(storeGroupID);
        if(postDTOS.size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.LIST_EMPTY);
        List<VoucherDTO> voucherDTOS = new ArrayList<>();
        for (int i = 0; i < postDTOS.size(); i++) {
            voucherDTOS.addAll(voucherService.findAllByPostID(postDTOS.get(i).getPostID()));
        }
        if (voucherDTOS.size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, voucherDTOS);
    }



}


