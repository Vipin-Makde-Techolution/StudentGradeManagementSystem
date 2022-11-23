package com.studentGradeManagementSystem.Repositories;

import com.studentGradeManagementSystem.entities.Exams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exams, Long> {



}
