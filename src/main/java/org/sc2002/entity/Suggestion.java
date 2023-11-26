package org.sc2002.entity;

import java.util.Random;
/**
 * Represents Suggestion in the application
 */
public class Suggestion implements Entity{

    private String suggestionID;
    private String staffID;
    private Student student;
    private Camp camp;
    private String suggestion;
    private Boolean isApproved;

    /**
     * Suggestion Constructor
     * @param student
     * @param camp
     * @param suggestion
     */
    public Suggestion(Student student, Camp camp, String suggestion) {
        this.suggestionID = getRandomID();
        this.student = student;
        this.camp = camp;
        this.staffID = camp.getStaffInChargeID();
        this.suggestion = suggestion;
        this.isApproved = false;
    }

    /**
     * Suggestion Constructor
     * @param suggestionID
     * @param staffID
     * @param student
     * @param camp
     * @param suggestion
     * @param isApproved
     */
    public Suggestion(String suggestionID, String staffID, Student student, Camp camp, String suggestion, Boolean isApproved) {
        this.suggestionID = suggestionID;
        this.staffID = staffID;
        this.student = student;
        this.camp = camp;
        this.suggestion = suggestion;
        this.isApproved = isApproved;
    }

    /**
     * get ID of Suggestion
     * @return String
     */
    @Override
    public String getID() {
        return suggestionID;
    }

    /**
     * Gets random id.
     *
     * @return the random id
     */
    public String getRandomID() {
        int length = 8;
        long timestamp = System.currentTimeMillis();
        Random random = new Random(timestamp);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();

    }

    /**
     * get SuggestionID
     * @return String 
     */
    public String getSuggestionID() {
        return suggestionID;
    }

    /**
     * get StaffID
     * @return String
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * get StudentID
     * @return String
     */
    public Student getStudent() {
        return student;
    }

    /**
     * get Camp
     * @return Camp
     */
    public Camp getCamp() {
        return camp;
    }

    /**
     * get Suggestion
     * @return suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * get Approved
     * @return true, if suggestion is approved
     */
    public Boolean getApproved() {
        return isApproved;
    }

    /**
     * set Suggestion
     * @param suggestion
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * set Approved
     * @param approved
     */
    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
