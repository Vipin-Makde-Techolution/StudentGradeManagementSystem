package com.studentGradeManagementSystem.Repositories;

import com.studentGradeManagementSystem.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {



}
