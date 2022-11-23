package com.studentGradeManagementSystem.Repositories;

import com.studentGradeManagementSystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject, Long> {



}
