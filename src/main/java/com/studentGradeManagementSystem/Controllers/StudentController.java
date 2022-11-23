package com.studentGradeManagementSystem.Controllers;

import com.studentGradeManagementSystem.Service.StudentService;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Student;
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
@RequestMapping(value = "/student", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "/student/all", notes = "Returns the list of student")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCourses() {
        List<Student> studentInfoList = new ArrayList<>();
        try{
            studentInfoList = studentService.getAllStudent();
            if (CollectionUtils.isEmpty(studentInfoList)) {
                return errorResponseBuilder(NO_CONTENT, "No Students present");
            }
        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching All students : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR , ex.getErrorMessage());
        }
        return new ResponseEntity<List<Student>>(studentInfoList, OK);
    }

    @ApiOperation(value = "/student/{id}", notes = "Returns the data of student")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Student studentInfo = null;
        try{
            studentInfo = studentService.getStudentById(id);

        } catch (AssignmentException ex) {
            logger.error("Error occurred while fetching a student by id : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Student>(studentInfo, OK);
    }

    @ApiOperation(value = "/student", notes = "Create Or Update the data of student")
    @PostMapping()
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody Student studentInfo)  throws AssignmentException {
        Student updated = null;
        try{
            updated = studentService.createOrUpdateStudent(studentInfo);
        } catch (AssignmentException ex) {
            logger.error("Error Occurred while updating or creating a student : [{}]", ex.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, ex.getErrorMessage());
        }
        return new ResponseEntity<Student>(updated, OK);
    }

    @ApiOperation(value = "/student/{id}", notes = "Deletes the data of student")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") Long id) throws AssignmentException {
        String delete = null;
        try{
            delete = studentService.deleteStudentById(id);
        } catch (AssignmentException exception){
            logger.error("Error Occurred while updating or creating a student : [{}]", exception.getExcep());
            return errorResponseBuilder(INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
        return new ResponseEntity<String>(delete, OK);
    }

}
