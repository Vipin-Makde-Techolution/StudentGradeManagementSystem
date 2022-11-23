package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.UserService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.Utills.JWTUtility;
import com.studentGradeManagementSystem.entities.JwtRequest;
import com.studentGradeManagementSystem.entities.JwtResponse;
import com.studentGradeManagementSystem.entities.Users;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.studentGradeManagementSystem.Utills.ApplicationHelper.errorResponseBuilder;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/user")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "/user/authenticate", notes = "get the token for authentication")
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }


    @ApiOperation(value = "/user/all", notes = "Returns the list of all year levels")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllYearLevel() {
        List<Users> users = new ArrayList<>();
        try{
            users = userService.getAllUsers();
            if (CollectionUtils.isEmpty(users)) {
                return errorResponseBuilder(NO_CONTENT, "No user present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All users : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity<List<Users>>(users, OK);
    }


    @ApiOperation(value = "/user/{id}", notes = "Returns the user for id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable Long id){
        Users user = null;
        try{
            user = userService.getUsersById(id);

        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching a user by user_Id : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Users>(user, OK);
    }

    @ApiOperation(value = "/user", notes = "Create or Update user")
    @PostMapping()
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody Users user)  throws AssignmentException {
        Users userUpdated = null;
        try{
            userUpdated = userService.createOrUpdateUsers(user);
        } catch (AssignmentException ex) {
            logger.error("Error Occurred while updating or creating a course : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Users>(userUpdated, OK);
    }

    @ApiOperation(value = "/user/{id}", notes = "delete Users for id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUsersById(@PathVariable("id") Long id) throws AssignmentException {
        String delete = null;
        try{
            delete = userService.deleteUsersById(id);
        } catch (AssignmentException exception){
            logger.error("Error Occurred while deleting a Users : [{}]", exception.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
        return new ResponseEntity<String>(delete, OK);
    }

}