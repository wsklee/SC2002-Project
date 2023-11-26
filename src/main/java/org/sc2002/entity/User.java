package org.sc2002.entity;

/**
 * Represents User in application
 */
public abstract class User implements Entity{

    private String name;
    private String email;
    private Faculty faculty;

    private String password;

    /**
     * User Constructor
     * @param name
     * @param email
     * @param password
     * @param faculty
     */
    public User(String name, String email, String password, Faculty faculty) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.faculty = faculty;

    }

    /**
     * get Email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * get Password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * get Name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get Faculty
     * @return Faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }
    
    /**
     * set Password
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * get ID abstract
     */
    public abstract String getID();
}
