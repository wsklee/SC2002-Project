package org.sc2002.entity;

import java.util.Random;
/**
 * Represents Enquiry in the application
 */
public class Enquiry implements Entity{

    private String enquiryID;
    private String staffID;
    private Student student;
    private Camp camp;

    private String query;
    private String answer;

    /**
     * Constructor for Enquiry
     * @param student
     * @param camp
     * @param query
     */
    public Enquiry(Student student, Camp camp, String query) {
        this.enquiryID = getRandomID();
        this.student = student;
        this.camp = camp;
        this.staffID = camp.getStaffInChargeID();
        this.query = query;
        this.answer = "NOT BEEN ANSWERED";
    }

    /**
     * Constructor for Enquiry
     * @param enquiryID
     * @param staffID
     * @param student
     * @param camp
     * @param query
     * @param answer
     */
    public Enquiry(String enquiryID, String staffID,  Student student, Camp camp, String query, String answer) {
        this.enquiryID = enquiryID;
        this.staffID = staffID;
        this.student = student;
        this.camp = camp;
        this.query = query;
        this.answer = answer;
    }
    /**
     * getID of enquiry ID
     * @return String
     */
    @Override
    public String getID() {
        return enquiryID;
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
     * getter for StaffID
     * @return staffID
     */
    public String getStaffID() {
        return staffID;
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
     * getter for Query
     * @return query
     */
    public String getQuery() {
        return query;
    }

    /**
     * getter for Answer
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * setter for Query
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * setter for Answer
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
