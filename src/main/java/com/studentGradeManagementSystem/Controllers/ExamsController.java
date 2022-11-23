package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.ExamService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Exams;
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
@RequestMapping(value = "/exam", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ExamsController {

    private final Logger logger = LoggerFactory.getLogger(ExamsController.class);

    @Autowired
    private ExamService examService;

    @ApiOperation(value = "/exam/all", notes = "Returns the list of exam")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCourses() {
        List<Exams> examList = new ArrayList<>();
        try{
            examList = examService.getAllExam();
            if (CollectionUtils.isEmpty(examList)) {
                return errorResponseBuilder(NO_CONTENT, "No Exams present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All exams : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity<List<Exams>>(examList, OK);
    }

    @ApiOperation(value = "/exam/{id}", notes = "Returns the data of exam")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Exams exam = null;
        try{
            exam = examService.getExamById(id);

        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching a exam by id : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Exams>(exam, OK);
    }

    @ApiOperation(value = "/exam", notes = "Create Or Update the data of exam")
    @PostMapping()
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody Exams exam)  throws AssignmentException {
        Exams updated = null;
        try{
            updated = examService.createOrUpdateExam(exam);
        } catch (AssignmentException ex) {
            logger.error("Error Occurred while updating or creating a exam : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Exams>(updated, OK);
    }

    @ApiOperation(value = "/exam/{id}", notes = "Deletes the data of exam")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") Long id) throws AssignmentException {
        String delete = null;
        try{
            delete = examService.deleteExamById(id);
        } catch (AssignmentException exception){
            logger.error("Error Occurred while updating or creating a exam : [{}]", exception.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
        return new ResponseEntity<String>(delete, OK);
    }

}
