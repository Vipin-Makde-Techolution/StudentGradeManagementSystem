package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.StudentRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getAllStudent() throws AssignmentException {
        List<Student> studentInfoList = studentRepo.findAll();
        if(studentInfoList.size() > 0) {
            return studentInfoList;
        } else {
            return new ArrayList<Student>();
        }
    }

    public Student getStudentById(Long id) throws AssignmentException
    {
        Optional<Student> student = studentRepo.findById(id);

        if(student.isPresent()) {
            return student.get();
        } else {
            throw new AssignmentException("No student record exist for given id");
        }
    }

    public Student createOrUpdateStudent(Student entity) throws AssignmentException
    {
        Optional<Student> student = studentRepo.findById(entity.getId());
        if(student.isPresent())
        {
            Student newEntity = student.get();
            newEntity.setName(entity.getName());
            newEntity.setAddress(entity.getAddress());
            newEntity.setContact(entity.getContact());
            newEntity.setGender(entity.getGender());
            newEntity.setSemester(entity.getSemester());
            newEntity = studentRepo.save(newEntity);

            return newEntity;
        } else {
            entity = studentRepo.save(entity);

            return entity;
        }
    }


    public String deleteStudentById(Long id) throws AssignmentException
    {
        Optional<Student> student = studentRepo.findById(id);
        try{
            if(student.isPresent())
            {
                studentRepo.delete(student.get());
            } else {
                throw new AssignmentException("No student record exist for given id");
            }
        } catch (AssignmentException e){
            e.printStackTrace();
        }

        return "student deleted with id : " + id.toString();
    }


}
