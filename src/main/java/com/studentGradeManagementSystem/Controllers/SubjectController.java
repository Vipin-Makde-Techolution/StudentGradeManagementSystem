package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.SubjectService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Subject;
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
@RequestMapping(value = "/subject", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SubjectController {

    private final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "/subject/all", notes = "Returns the list of subject")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCourses() {
        List<Subject> subjectList = new ArrayList<>();
        try{
            subjectList = subjectService.getAllSubject();
            if (CollectionUtils.isEmpty(subjectList)) {
                return errorResponseBuilder(NO_CONTENT, "No Subjects present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All subjects : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity<List<Subject>>(subjectList, OK);
    }

    @ApiOperation(value = "/subject/{id}", notes = "Returns the data of subject")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Subject subject = null;
        try{
            subject = subjectService.getSubjectById(id);

        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching a subject by id : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Subject>(subject, OK);
    }

    @ApiOperation(value = "/subject", notes = "Create Or Update the data of subject")
    @PostMapping()
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody Subject subject)  throws AssignmentException {
        Subject updated = null;
        try{
            updated = subjectService.createOrUpdateSubject(subject);
        } catch (AssignmentException ex) {
            logger.error("Error Occurred while updating or creating a subject : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Subject>(updated, OK);
    }

    @ApiOperation(value = "/subject/{id}", notes = "Deletes the data of subject")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") Long id) throws AssignmentException {
        String delete = null;
        try{
            delete = subjectService.deleteSubjectById(id);
        } catch (AssignmentException exception){
            logger.error("Error Occurred while updating or creating a subject : [{}]", exception.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
        return new ResponseEntity<String>(delete, OK);
    }

}
