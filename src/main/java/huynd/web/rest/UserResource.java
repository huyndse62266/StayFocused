package huynd.web.rest;

import com.codahale.metrics.annotation.Timed;
import huynd.config.Constants;
import huynd.domain.Authority;
import huynd.domain.User;
import huynd.repository.UserRepository;
import huynd.security.AuthoritiesConstants;
import huynd.service.ActivitiesLogService;
import huynd.service.MailService;
import huynd.service.PointLogService;
import huynd.service.UserService;
import huynd.service.dto.UserDTO;
import huynd.service.dto.response.AntiPhoneResponse;
import huynd.service.dto.response.PointResponse;
import huynd.service.dto.response.VoucherPointResponse;
import huynd.web.rest.errors.BadRequestAlertException;
import huynd.web.rest.errors.EmailAlreadyUsedException;
import huynd.web.rest.errors.LoginAlreadyUsedException;
import huynd.web.rest.util.HeaderUtil;
import huynd.web.rest.util.Messages;
import huynd.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    @Autowired
    PointLogService pointLogService;

    @Autowired
    ActivitiesLogService activitiesLogService;

    public UserResource(UserService userService, UserRepository userRepository, MailService mailService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getUsername() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUserByAdmin(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    public AntiPhoneResponse updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
//        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getUsername()))) {
//            throw new EmailAlreadyUsedException();
//        }
//        existingUser = userRepository.findOneByLogin(userDTO.getUsername().toLowerCase());
//        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getUsername()))) {
//            throw new LoginAlreadyUsedException();
//        }
//        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);
//
//        return ResponseUtil.wrapOrNotFound(updatedUser,
//            HeaderUtil.createAlert("A user is updated with identifier " + userDTO.getUsername(), userDTO.getUsername()));
        huynd.web.rest.util.Constants constants = new huynd.web.rest.util.Constants();
        if (StringUtils.isEmpty(userDTO.getUsername()))
            return new AntiPhoneResponse(constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if (userService.findByLogin(userDTO.getUsername()) == null)
            return new AntiPhoneResponse(constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.ID_NOT_EXIST);
        UserDTO existUser = userService.findByLogin(userDTO.getUsername());
        if (userDTO.getDateOfBirth() != null)
            existUser.setDateOfBirth(userDTO.getDateOfBirth());
        if (userDTO.getPhone() != null)
            existUser.setPhone(userDTO.getPhone());
        if (StringUtils.isNotEmpty(userDTO.getFirstName()))
            existUser.setFirstName(userDTO.getFirstName());
        if (StringUtils.isNotEmpty(userDTO.getLocation()))
            existUser.setLocation(userDTO.getLocation());
        if (userDTO.getGender() != null)
            existUser.setGender(userDTO.getGender());
        User result = userService.updateExistUser(existUser);
        return new AntiPhoneResponse(constants.STATUS_OK, Messages.UPDATE_SUCCESFULLY, result);
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login}")
    @Timed
    public AntiPhoneResponse getUser(@PathVariable String login) {
        huynd.web.rest.util.Constants constants = new huynd.web.rest.util.Constants();
        log.debug("REST request to get User : {}", login);
        if (userService.findByLogin(login) == null)
            return new AntiPhoneResponse(constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, Messages.ID_NOT_EXIST);
        return new AntiPhoneResponse(constants.STATUS_OK, Messages.FIND_SUCCESFULLY, userService.findByLogin(login));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A user is deleted with identifier " + login, login)).build();
    }

    @PostMapping("/users/register")
    @Timed
    public Object register(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to register User: {}" + userDTO);
        if (StringUtils.isEmpty(userDTO.getUsername()))
            return new AntiPhoneResponse(400, "Wrong request param", "Username is required !!!!");
        if (StringUtils.isEmpty(userDTO.getFirstName()))
            return new AntiPhoneResponse(400, "Wrong request param", "FirstName is required !!!!");
        if (StringUtils.isEmpty(userDTO.getEmail()))
            return new AntiPhoneResponse(400, "Wrong request param", "Email is required !!!!");
        if (StringUtils.isEmpty(userDTO.getPassword()))
            return new AntiPhoneResponse(400, "Wrong request param", "Password is required !!!!");
        if (StringUtils.isEmpty(userDTO.getRePassword()))
            return new AntiPhoneResponse(400, "Wrong request param", "password is required !!!!");
        if (!userDTO.getPassword().equalsIgnoreCase(userDTO.getRePassword()))
            return new AntiPhoneResponse(400, "Wrong request param", "Password and Re-password aren't match");
        if (userRepository.findOneByLogin(userDTO.getUsername().toLowerCase()).isPresent())
            return new AntiPhoneResponse(400, "Wrong request param", "Username is exist !!!!");
        if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail().toLowerCase()).isPresent())
            return new AntiPhoneResponse(400, "Wrong request param", "Email was registered !!!!");
        User user = userService.createUser(userDTO);

        return new AntiPhoneResponse(200, "Create New Account Succesfully", user);
    }

    @GetMapping("/users/find-role")
    @Timed
    public String findROle() {
        Optional<User> user = userService.getUserWithAuthorities();
        Iterator<Authority> iterator = user.get().getAuthorities().iterator();
        Authority authority = new Authority();
        String role = "";
        while (iterator.hasNext()) {
            authority = iterator.next();
            role = authority.getName();
        }
        return role;
    }

    @PostMapping("/users/register-without-password")
    @Timed
    public Object registerWithoutPassword(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to register User: {}" + userDTO);
        if (StringUtils.isEmpty(userDTO.getUsername()))
            return new AntiPhoneResponse(400, "Wrong request param", "Username is required !!!!");
        if (StringUtils.isEmpty(userDTO.getFirstName()))
            return new AntiPhoneResponse(400, "Wrong request param", "FirstName is required !!!!");
        if (StringUtils.isEmpty(userDTO.getEmail()))
            return new AntiPhoneResponse(400, "Wrong request param", "Email is required !!!!");
        if (userRepository.findOneByLogin(userDTO.getUsername().toLowerCase()).isPresent())
            return new AntiPhoneResponse(400, "Wrong request param", "Username is exist !!!!");
        if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail().toLowerCase()).isPresent())
            return new AntiPhoneResponse(400, "Wrong request param", "Email was registered !!!!");
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        userDTO.setDateOfBirth(date);
        User user = userService.createUser(userDTO);

        return new AntiPhoneResponse(200, "Create New Account Succesfully", user);
    }

    @GetMapping("/users/point/{userID}")
    @Timed
    public AntiPhoneResponse getAboutPoint(@PathVariable String userID) {
        log.debug("REST request to get Point : {}", userID);
        if (userID == null)
            return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_BAD_REQUEST, Messages.REQUEST_PARAM_ERROR, "Username " + Messages.REQUEST_PARAM_REQUIRED);
        if (userService.findByLogin(userID) == null)
            return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_OK, Messages.REQUEST_PARAM_ERROR, new VoucherPointResponse( Long.valueOf(0),Long.valueOf(0), Long.valueOf(0)));
        Long id = userService.findByLogin(userID).getId();
        Long sumPointEarn = 0L;
        Long sumPointSpent = 0L;
        if (activitiesLogService.listActivitesByUsername(id).size() == 0)
            return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_OK, Messages.REQUEST_PARAM_ERROR,new VoucherPointResponse( Long.valueOf(0),Long.valueOf(0), Long.valueOf(0)));
        if (activitiesLogService.sumPointByUser(id) != null)
            sumPointEarn = activitiesLogService.sumPointByUser(id);
        if(pointLogService.listPointByUsername(id).size() == 0)
            return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_OK, Messages.FIND_SUCCESFULLY,new VoucherPointResponse( sumPointSpent,sumPointEarn - sumPointSpent, sumPointEarn));
        if (pointLogService.sumPointByUser(id) != null)
            sumPointSpent = pointLogService.sumPointByUser(id);

        return new AntiPhoneResponse(huynd.web.rest.util.Constants.STATUS_OK, Messages.FIND_SUCCESFULLY, new VoucherPointResponse(sumPointSpent, sumPointEarn - sumPointSpent, sumPointEarn));
    }
}
