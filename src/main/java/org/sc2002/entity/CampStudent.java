package org.sc2002.entity;
/**
 * Represents CampStudent in the application
 */
public class CampStudent implements Entity{

    private Camp camp;
    private Student student;
    private CampRole campRole;
    private Boolean hasWithdrawn;

    /**
     * Constructor for CampStudent
     * @param camp
     * @param student
     * @param campRole
     * @param hasWithdrawn
     */
    public CampStudent( Camp camp,Student student,CampRole campRole, Boolean hasWithdrawn) {
        this.camp = camp;
        this.student = student;
        this.campRole = campRole;
        this.hasWithdrawn = hasWithdrawn;

    }

    /**
     * getter for Student
     * @return Student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * getter for Camp
     * @return Camp
     */
    public Camp getCamp() {
        return camp;
    }

    /**
     * getter for if CampStudent has withdrawn
     * @return true if campStudent has withdrawn
     */
    public Boolean getHasWithdrawn() {
        return hasWithdrawn;
    }

    /**
     * getter for Camp Role
     * @return CampRole
     */
    public CampRole getCampRole() {
        return campRole;
    }

    /**
     * getID of CampStudent
     * @return String
     */
    @Override
    public String getID() {
        return camp.getID()+"-"+student.getID();
    }
}
