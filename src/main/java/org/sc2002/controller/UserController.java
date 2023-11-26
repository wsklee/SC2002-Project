package org.sc2002.controller;

import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.User;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for managing and coordinating operations related to User in the application.
 */
public class UserController {
    private List<User> users;

    /**
     * Constructor for UserController
     * @param studentRepository
     * @param staffRepository
     */
    public UserController(StudentRepository studentRepository, StaffRepository staffRepository) {
        users = new ArrayList<>();
        users.addAll(studentRepository.getAllStudents());
        users.addAll(staffRepository.getAllStaff());
    }

    /**
     * Authenticate user
     * @param userId
     * @param password
     * @return User
     */
    public User authenticateUser(String userId, String password) {
        for (User user : users) {
            //System.out.println(user.getID()+" "+user.getPassword());
            if (user.getID().equalsIgnoreCase(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * get String of User Role
     * @param user
     * @return String
     */
    public String getUserRole(User user) {
        if (user instanceof Staff) {
            return "Staff Member";
        } else if (user instanceof Student) {
            return "Student";
        } else {
            return "Unknown";
        }
    }
}
