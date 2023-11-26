package org.sc2002.entity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.sc2002.utils.exception.CampFullException;
import org.sc2002.utils.exception.EntityNotFoundException;

/**
 * Represents a Student in the application
 */
public class Student extends User {

    private boolean isCampCommitteeMember;

    private Camp committeeMemberCamp;
    private List<Camp> registeredCamps;

    private int point;

    /**
     * Student Constructor
     * @param name
     * @param email
     * @param password
     * @param faculty
     */
    public Student(String name, String email, String password, String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
        this.isCampCommitteeMember = false;
        this.registeredCamps = new ArrayList<>();
    }

    /**
     * get ID of Student
     * @return String
     */
    @Override
    public String getID() {
        Pattern pattern = Pattern.compile("^(.*?)@");
        Matcher matcher = pattern.matcher(super.getEmail());
        if (matcher.find()) {
            // Group 1 contains the user ID
            return matcher.group(1);
        } else {
            return null;
        }
    }
    
    /**
     * Check if Student is a camp committee member
     * @return true if student is a camp committee member
     */
    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    /**
     * Set camp committee member
     * @param campCommitteeMember
     */
    public void setCampCommitteeMember(boolean campCommitteeMember) {
        isCampCommitteeMember = campCommitteeMember;
    }

    /**
     * Register for camp
     * @param camp
     */
    public void registerForCamp(Camp camp) {
        registeredCamps.add(camp);
    }

    /**
     * Register for camp as a committee member
     * @param camp
     */
    public void registerForCampAsCampCommitteeMember(Camp camp){
        registeredCamps.add(camp);
        committeeMemberCamp = camp;
        isCampCommitteeMember = true;
    }

    /**
     * Withdraw from Camp
     * @param camp
     */
    public void withdrawFromCamp(Camp camp) {
        registeredCamps.remove(camp);
    }


    /**
     * get List of registered camps
     * @return ArrayList of Camp
     */
    public ArrayList<Camp> getRegisteredCamps() {
        return new ArrayList<>(registeredCamps);
    }

    /**
     * get committee members camp
     * @return committeeMemberCamp
     */
    public Camp getCommitteeMemberCamp() {
        return committeeMemberCamp;
    }

    /**
     * get Point
     * @return point
     */
    public int getPoint() {
        return point;
    }

    /**
     * set Point
     * @param point
     */
    public void setPoint(int point) {
        this.point = point;
    }
}

