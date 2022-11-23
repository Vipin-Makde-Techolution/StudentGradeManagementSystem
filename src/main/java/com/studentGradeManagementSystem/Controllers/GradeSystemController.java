package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.GradeSystemService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
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
@RequestMapping(value = "/gradeSystem", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GradeSystemController {

    private final Logger logger = LoggerFactory.getLogger(GradeSystemController.class);

    @Autowired
    private GradeSystemService gradeSystemService;

    @ApiOperation(value = "/gradeSystem/getResultForEachClass", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getResultForEachClass/{semester}")
    public ResponseEntity<?> getResultForEachClass(@PathVariable int semester) {
        List<Object>  resultForEachClassList = new ArrayList<>();
        try{
           resultForEachClassList = gradeSystemService.getResultForEachClass(semester);
            if (CollectionUtils.isEmpty(resultForEachClassList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(resultForEachClassList, OK);
    }


    @ApiOperation(value = "/gradeSystem/getTopperForSubject", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getTopperForSubject/{subjectData}/{semester}")
    public ResponseEntity<?> getTopperForSubject(@PathVariable String subjectData, @PathVariable int semester) {
        List<Object>  topperForSubjectList = new ArrayList<>();
        try{
            topperForSubjectList = gradeSystemService.getTopperForSubject(subjectData, semester);
            if (CollectionUtils.isEmpty(topperForSubjectList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(topperForSubjectList, OK);
    }

    @ApiOperation(value = "/gradeSystem/getClassTopper", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getClassTopper/{semester}")
    public ResponseEntity<?> getClassTopper( @PathVariable int semester) {
        List<Object>  classTopperList = new ArrayList<>();
        try{
            classTopperList = gradeSystemService.getClassTopper(semester);
            if (CollectionUtils.isEmpty(classTopperList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(classTopperList, OK);
    }


    @ApiOperation(value = "/gradeSystem/getTopperOfEachGenderInClass", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getTopperOfEachGenderInClass/{semester}")
    public ResponseEntity<?> getTopperOfEachGenderInClass( @PathVariable int semester) {
        List<Object>  topperOfEachGenderInClassList = new ArrayList<>();
        try{
            topperOfEachGenderInClassList = gradeSystemService.getTopperOfEachGenderInClass(semester);
            if (CollectionUtils.isEmpty(topperOfEachGenderInClassList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(topperOfEachGenderInClassList, OK);
    }

    @ApiOperation(value = "/gradeSystem/getDetailsOfEachStudent", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getDetailsOfEachStudent/{studentId}")
    public ResponseEntity<?> getDetailsOfEachStudentList( @PathVariable int studentId) {
        List<Object>  detailsOfEachStudentList = new ArrayList<>();
        try{
            detailsOfEachStudentList = gradeSystemService.getDetailsOfEachStudent(studentId);
            if (CollectionUtils.isEmpty(detailsOfEachStudentList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(detailsOfEachStudentList, OK);
    }


    @ApiOperation(value = "/gradeSystem/getDetailsOfEachSubject", notes = "Returns the list of enrollment")
    @GetMapping(value = "/getDetailsOfEachSubject/{studentId}")
    public ResponseEntity<?> getDetailsOfEachSubject( @PathVariable int studentId) {
        List<Object>  detailsOfEachSubjectList = new ArrayList<>();
        try{
            detailsOfEachSubjectList = gradeSystemService.getDetailsOfEachSubject(studentId);
            if (CollectionUtils.isEmpty(detailsOfEachSubjectList)) {
                return errorResponseBuilder(NO_CONTENT, "No class results present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All the results of class : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity< List<Object>>(detailsOfEachSubjectList, OK);
    }

}
