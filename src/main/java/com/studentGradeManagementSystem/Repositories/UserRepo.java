package com.studentGradeManagementSystem.Repositories;

import com.studentGradeManagementSystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends JpaRepository<Users, Long>, CrudRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Users getByUsername(String userName);


}
