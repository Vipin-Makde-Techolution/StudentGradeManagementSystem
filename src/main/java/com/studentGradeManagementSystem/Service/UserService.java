package com.studentGradeManagementSystem.Service;

import com.studentGradeManagementSystem.Repositories.UserRepo;
import com.studentGradeManagementSystem.Utills.AssignmentException;
import com.studentGradeManagementSystem.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //Logic to get the user form the Database
        Users userData = userRepo.getByUsername(userName);
        return new User(userData.getUsername(),userData.getPassword(),new ArrayList<>());
    }

    public List<Users> getAllUsers() throws AssignmentException {
        List<Users> users = userRepo.findAll();
        if(users.size() > 0) {
            return users;
        } else {
            return new ArrayList<Users>();
        }
    }

    public Users getUsersById(Long id) throws AssignmentException
    {
        Optional<Users> user = userRepo.findById(id);

        if(user.isPresent()) {
            return user.get();
        } else {
            throw new AssignmentException("No user record exist for given id");
        }
    }

    public Users createOrUpdateUsers(Users entity) throws AssignmentException
    {
        Users newEntity = userRepo.getByUsername(entity.getUsername());

        if(newEntity != null)
        {
            newEntity.setCompletename(entity.getCompletename());
            newEntity.setPassword(entity.getPassword());
            newEntity.setUsername(entity.getUsername());
            newEntity.setRoles(entity.getRoles());
            newEntity = userRepo.save(newEntity);
            return newEntity;
        } else {
            entity = userRepo.save(entity);

            return entity;
        }
    }

    public String deleteUsersById(Long id) throws AssignmentException
    {
        Optional<Users> user = userRepo.findById(id);

        if(user.isPresent())
        {
            userRepo.delete(user.get());
        } else {
            throw new AssignmentException("No user record exist for given id");
        }
        return "user deleted with user id : " + id.toString();
    }


}