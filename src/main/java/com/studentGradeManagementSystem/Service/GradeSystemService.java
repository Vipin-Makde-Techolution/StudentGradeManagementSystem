package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.StudentRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradeSystemService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    EntityManager entityManager;

    public List<Object> getResultForEachClass(int semester) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "call getResultForEachClass(?)");
        query.setParameter(1, semester);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }

    public List<Object> getTopperForSubject(String subjectData, int semester) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "call getTopperForSubject(?,?)");
        query.setParameter(1, subjectData);
        query.setParameter(2,semester);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }

    public List<Object> getClassTopper( int semester) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "call getClassTopper(?)");
        query.setParameter(1,semester);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }


  public List<Object> getTopperOfEachGenderInClass( int semester) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "call getTopperOfEachGenderInClass(?)");
        query.setParameter(1,semester);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }

  public List<Object> getDetailsOfEachStudent( int studentId) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "Select * from v_grades where studentId=?");
        query.setParameter(1,studentId);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }


public List<Object> getDetailsOfEachSubject( int subjectId) throws AssignmentException {
        Query query = entityManager.createNativeQuery(  "Select * from v_grades where subjectId=?");
        query.setParameter(1,subjectId);
        List<Object> val = (List<Object>) query.getResultList();
        if(val.size() > 0) {
            return val;
        } else {
            return new ArrayList<Object>();
        }
    }





}
