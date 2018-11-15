package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.service.ActivitiesLogService;
import huynd.service.UserService;
import huynd.service.dto.ActivitiesLogDTO;
import huynd.service.dto.UserDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.web.rest.util.Constants;
import huynd.web.rest.util.Messages;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * REST controller for managing ActivitiesLog.
 */
@RestController
@RequestMapping("/api")
public class ActivitiesLogResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiesLogResource.class);

    private static final String ENTITY_NAME = "activitiesLog";

    private final ActivitiesLogService activitiesLogService;

    @Autowired
    UserService userService;

    public ActivitiesLogResource(ActivitiesLogService activitiesLogService) {
        this.activitiesLogService = activitiesLogService;
    }

    /**
     * POST  /activities-logs : Create a new activitiesLog.
     *
     * @param activitiesLogDTO the activitiesLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activitiesLogDTO, or with status 400 (Bad Request) if the activitiesLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activities-logs")
    @Timed
    public AntiPhoneResponse createActivitiesLog(@RequestBody ActivitiesLogDTO activitiesLogDTO) throws URISyntaxException {
        log.debug("REST request to save ActivitiesLog : {}", activitiesLogDTO);
        if(StringUtils.isEmpty(activitiesLogDTO.getUserDTO().getUsername()))
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if (userService.findByLogin(activitiesLogDTO.getUserDTO().getUsername()) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        if(activitiesLogDTO.getActivitiesLogDate() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log Date " + Messages.REQUEST_PARAM_REQUIRED);
        if(activitiesLogDTO.getActivitiesLogAchievedTime() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log Achieved Time " + Messages.REQUEST_PARAM_REQUIRED);
        if(activitiesLogDTO.getActivitiesLogPointReceived() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log Point Received " + Messages.REQUEST_PARAM_REQUIRED);
        Long id = userService.findByLogin(activitiesLogDTO.getUserDTO().getUsername()).getId();

        if(activitiesLogService.findOneByDate(activitiesLogDTO.getActivitiesLogDate(),id).isPresent()){
            ActivitiesLogDTO activitiesLogDTOExist = activitiesLogService.findOneByDate(activitiesLogDTO.getActivitiesLogDate(),id).get();
            Long newTime = activitiesLogDTOExist.getActivitiesLogAchievedTime() + activitiesLogDTO.getActivitiesLogAchievedTime();
            Long newPoint = activitiesLogDTOExist.getActivitiesLogPointReceived() + activitiesLogDTO.getActivitiesLogPointReceived();
            activitiesLogDTOExist.setActivitiesLogAchievedTime(newTime);
            activitiesLogDTOExist.setActivitiesLogPointReceived(newPoint);
            activitiesLogDTOExist.getUserDTO().setId(id);
            ActivitiesLogDTO result = activitiesLogService.save(activitiesLogDTOExist);
            return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY,result);
        }
        String username = activitiesLogDTO.getUserDTO().getUsername();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(activitiesLogDTO.getUserDTO().getUsername());
        activitiesLogDTO.setUserDTO(userDTO);
        ActivitiesLogDTO result = activitiesLogService.save(activitiesLogDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.CREATE_SUCCESFULLY,result);
    }

    /**
     * PUT  /activities-logs : Updates an existing activitiesLog.
     *
     * @param activitiesLogDTO the activitiesLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activitiesLogDTO,
     * or with status 400 (Bad Request) if the activitiesLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the activitiesLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activities-logs")
    @Timed
    public AntiPhoneResponse updateActivitiesLog(@RequestBody ActivitiesLogDTO activitiesLogDTO) throws URISyntaxException {
        log.debug("REST request to update ActivitiesLog : {}", activitiesLogDTO);
        if (activitiesLogDTO.getActivitiesLogID() == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(activitiesLogService.findOne(activitiesLogDTO.getActivitiesLogID()).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log ID " + Messages.ID_NOT_EXIST);
        ActivitiesLogDTO existActivitiesDTO = activitiesLogService.findOne(activitiesLogDTO.getActivitiesLogID()).get();
        if(activitiesLogDTO.getActivitiesLogAchievedTime() != null){
            existActivitiesDTO.setActivitiesLogAchievedTime(activitiesLogDTO.getActivitiesLogAchievedTime());
        }
        if(activitiesLogDTO.getActivitiesLogDate() != null){
            existActivitiesDTO.setActivitiesLogDate(activitiesLogDTO.getActivitiesLogDate());
        }
        if(activitiesLogDTO.getActivitiesLogPointReceived() != null){
            existActivitiesDTO.setActivitiesLogPointReceived(activitiesLogDTO.getActivitiesLogPointReceived());
        }
        ActivitiesLogDTO result = activitiesLogService.save(existActivitiesDTO);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET  /activities-logs : get all the activitiesLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activitiesLogs in body
     */
    @GetMapping("/activities-logs")
    @Timed
    public AntiPhoneResponse getAllActivitiesLogs() {
        log.debug("REST request to get all ActivitiesLogs");
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, activitiesLogService.findAll());
    }

    /**
     * GET  /activities-logs/:id : get the "id" activitiesLog.
     *
     * @param id the id of the activitiesLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activitiesLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activities-logs/{id}")
    @Timed
    public AntiPhoneResponse getActivitiesLog(@PathVariable Long id) {
        log.debug("REST request to get ActivitiesLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activites Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        if(!activitiesLogService.findOne(id).isPresent())
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activities Log ID " + Messages.ID_NOT_EXIST);
        Optional<ActivitiesLogDTO> activitiesLogDTO = activitiesLogService.findOne(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, activitiesLogDTO);
    }

    @GetMapping("/activities-logs/users/{username}")
    @Timed
    public AntiPhoneResponse getListActivitiesLogByUsername(@PathVariable String username) {
        log.debug("REST request to get ActivitiesLog : {}", username);
        if (username == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activites Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        Long id = userService.findByLogin(username).getId();
        if(activitiesLogService.listActivitesByUsername(id).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username" + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, activitiesLogService.listActivitesByUsername(id));
    }

    /**
     * DELETE  /activities-logs/:id : delete the "id" activitiesLog.
     *
     * @param id the id of the activitiesLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activities-logs/{id}")
    @Timed
    public AntiPhoneResponse deleteActivitiesLog(@PathVariable Long id) {
        log.debug("REST request to delete ActivitiesLog : {}", id);
        if (id == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Activites Log ID " + Messages.REQUEST_PARAM_REQUIRED);
        activitiesLogService.delete(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.DELETE_SUCCESFULLY, "Delete " + id + " successfully");

    }


    @GetMapping("/activities-logs/near-date")
    @Timed
    public AntiPhoneResponse getNearlyDate(@RequestParam("date") Date date, @RequestParam("user") String user) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -6);
        Date datePast = new java.sql.Date(gregorianCalendar.getTime().getTime());
        if (userService.findByLogin(user) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, activitiesLogService.listActtiviesIn7Days(datePast,date,userService.findByLogin(user).getId()));
    }

    @GetMapping("/activities-logs/users/total-point/{username}")
    @Timed
    public AntiPhoneResponse totalPointByUser(@PathVariable String username) {
        log.debug("REST request to get PointLog : {}", username);
        if (username == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if(userService.findByLogin(username) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        Long id = userService.findByLogin(username).getId();
        Long sumPoint = 0L;
        if(activitiesLogService.listActivitesByUsername(id).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, sumPoint);

        if(activitiesLogService.sumPointByUser(id) != null)
            sumPoint = activitiesLogService.sumPointByUser(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, sumPoint);
    }

    @GetMapping("/activities-logs/users/total-time/{username}")
    @Timed
    public AntiPhoneResponse totalTimeByUser(@PathVariable String username) {
        log.debug("REST request to get PointLog : {}", username);
        if (username == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if(userService.findByLogin(username) == null)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        Long id = userService.findByLogin(username).getId();
        Long sum = 0L;
        if(activitiesLogService.listActivitesByUsername(id).size() == 0)
            return new AntiPhoneResponse(Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, sum);
        if(activitiesLogService.sumTimeByUser(id)!= null)
            sum = activitiesLogService.sumTimeByUser(id);
        return new AntiPhoneResponse(Constants.STATUS_OK, Messages.FIND_SUCCESFULLY,sum);
    }

}
