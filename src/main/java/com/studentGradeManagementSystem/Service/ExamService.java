package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.ExamRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Exams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepo examRepo;

    public List<Exams> getAllExam() throws AssignmentException {
        List<Exams> examList = examRepo.findAll();
        if(examList.size() > 0) {
            return examList;
        } else {
            return new ArrayList<Exams>();
        }
    }

    public Exams getExamById(Long id) throws AssignmentException
    {
        Optional<Exams> exam = examRepo.findById(id);

        if(exam.isPresent()) {
            return exam.get();
        } else {
            throw new AssignmentException("No exam record exist for given id");
        }
    }

    public Exams createOrUpdateExam(Exams entity) throws AssignmentException
    {
        Optional<Exams> exam = examRepo.findById(entity.getId());
        if(exam.isPresent())
        {
            Exams newEntity = exam.get();
            newEntity.setMarksObtained(entity.getMarksObtained());
            newEntity.setPaperId(entity.getPaperId());
            newEntity.setStudentId(entity.getStudentId());
            newEntity.setSemester(entity.getSemester());
            newEntity = examRepo.save(newEntity);

            return newEntity;
        } else {
            entity = examRepo.save(entity);

            return entity;
        }
    }


    public String deleteExamById(Long id) throws AssignmentException
    {
        Optional<Exams> exam = examRepo.findById(id);
        try{
            if(exam.isPresent())
            {
                examRepo.delete(exam.get());
            } else {
                throw new AssignmentException("No exam record exist for given id");
            }
        } catch (AssignmentException e){
            e.printStackTrace();
        }

        return "exam deleted with id : " + id.toString();
    }


}
