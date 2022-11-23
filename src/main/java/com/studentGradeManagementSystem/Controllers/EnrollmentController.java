package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.EnrollmentService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Enrollment;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.studentGradeManagementSystem.Utills.ApplicationHelper.errorResponseBuilder;
import static org.springframework.http.HttpStatus.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/enrollment", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EnrollmentController {

    private final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @ApiOperation(value = "/enrollment/all", notes = "Returns the list of enrollment")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCourses() {
        List<Enrollment> enrollmentList = new ArrayList<>();
        try{
            enrollmentList = enrollmentService.getAllEnrollment();
            if (CollectionUtils.isEmpty(enrollmentList)) {
                return errorResponseBuilder(NO_CONTENT, "No Enrollments present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All enrollments : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity<List<Enrollment>>(enrollmentList, OK);
    }

    @ApiOperation(value = "/enrollment/{id}", notes = "Returns the data of enrollment")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Enrollment enrollment = null;
        try{
            enrollment = enrollmentService.getEnrollmentById(id);

        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching a enrollment by id : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Enrollment>(enrollment, OK);
    }

    @ApiOperation(value = "/enrollment", notes = "Create Or Update the data of enrollment")
    @PostMapping()
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody Enrollment enrollment)  throws AssignmentException {
        Enrollment updated = null;
        try{
            updated = enrollmentService.createOrUpdateEnrollment(enrollment);
        } catch (AssignmentException ex) {
            logger.error("Error Occurred while updating or creating a enrollment : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Enrollment>(updated, OK);
    }

    @ApiOperation(value = "/enrollment/{id}", notes = "Deletes the data of enrollment")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") Long id) throws AssignmentException {
        String delete = null;
        try{
            delete = enrollmentService.deleteEnrollmentById(id);
        } catch (AssignmentException exception){
            logger.error("Error Occurred while updating or creating a enrollment : [{}]", exception.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
        return new ResponseEntity<String>(delete, OK);
    }

}
