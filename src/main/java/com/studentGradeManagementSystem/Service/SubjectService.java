package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.SubjectRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

    public List<Subject> getAllSubject() throws AssignmentException {
        List<Subject> subjects = subjectRepo.findAll();
        if(subjects.size() > 0) {
            return subjects;
        } else {
            return new ArrayList<Subject>();
        }
    }

    public Subject getSubjectById(Long id) throws AssignmentException
    {
        Optional<Subject> subject = subjectRepo.findById(id);

        if(subject.isPresent()) {
            return subject.get();
        } else {
            throw new AssignmentException("No subject record exist for given id");
        }
    }

    public Subject createOrUpdateSubject(Subject entity) throws AssignmentException
    {
        Optional<Subject> subject = subjectRepo.findById(entity.getPaperId());
        if(subject.isPresent())
        {
            Subject newEntity = subject.get();
            newEntity.setIsMandatory(entity.getIsMandatory());
            newEntity.setSubjectId(entity.getSubjectId());
            newEntity.setSubjectName(entity.getSubjectName());
            newEntity.setTypeOfExam(entity.getTypeOfExam());
            newEntity = subjectRepo.save(newEntity);

            return newEntity;
        } else {
            entity = subjectRepo.save(entity);

            return entity;
        }
    }


    public String deleteSubjectById(Long id) throws AssignmentException
    {
        Optional<Subject> subject = subjectRepo.findById(id);
        try{
            if(subject.isPresent())
            {
                subjectRepo.delete(subject.get());
            } else {
                throw new AssignmentException("No subject record exist for given id");
            }
        } catch (AssignmentException e){
            e.printStackTrace();
        }

        return "subject deleted with id : " + id.toString();
    }


}
