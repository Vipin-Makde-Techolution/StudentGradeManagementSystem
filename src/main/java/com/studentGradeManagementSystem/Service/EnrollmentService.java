package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.EnrollmentRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    public List<Enrollment> getAllEnrollment() throws AssignmentException {
        List<Enrollment> enrollmentList = enrollmentRepo.findAll();
        if(enrollmentList.size() > 0) {
            return enrollmentList;
        } else {
            return new ArrayList<Enrollment>();
        }
    }

    public Enrollment getEnrollmentById(Long id) throws AssignmentException
    {
        Optional<Enrollment> enrollment = enrollmentRepo.findById(id);

        if(enrollment.isPresent()) {
            return enrollment.get();
        } else {
            throw new AssignmentException("No enrollment record exist for given id");
        }
    }

    public Enrollment createOrUpdateEnrollment(Enrollment entity) throws AssignmentException
    {
        Optional<Enrollment> enrollment = enrollmentRepo.findById(entity.getId());
        if(enrollment.isPresent())
        {
            Enrollment newEntity = enrollment.get();
            newEntity.setApprovalId(entity.getApprovalId());
            newEntity.setCurrentlyEnrolled(entity.getCurrentlyEnrolled());
            newEntity.setSemester(entity.getSemester());
            newEntity.setStudentId(entity.getStudentId());
            newEntity.setSubjectId(entity.getSubjectId());
            newEntity = enrollmentRepo.save(newEntity);

            return newEntity;
        } else {
            entity = enrollmentRepo.save(entity);

            return entity;
        }
    }


    public String deleteEnrollmentById(Long id) throws AssignmentException
    {
        Optional<Enrollment> enrollment = enrollmentRepo.findById(id);
        try{
            if(enrollment.isPresent())
            {
                enrollmentRepo.delete(enrollment.get());
            } else {
                throw new AssignmentException("No enrollment record exist for given id");
            }
        } catch (AssignmentException e){
            e.printStackTrace();
        }

        return "enrollment deleted with id : " + id.toString();
    }


}
