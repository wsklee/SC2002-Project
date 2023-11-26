package org.sc2002.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.sc2002.utils.exception.*;
/**
 * Represents a Camp in the application.
 */
public class Camp implements Entity{

    private String campName;
    private String description;

    private LocalDate campStartDate;
    private LocalDate campEndDate;
    private LocalDate campRegistrationEndDate;

    private Faculty userGroupOpenTo;
    private String location;

    private String staffInChargeID;

    private boolean visibilityToStudent = true;

    private int totalSlots;
    private int campCommitteeSlots;

    private ArrayList<Student> studentsRegistered;
    
    private ArrayList<Student> committeeRegistered;

    private ArrayList<Student> studentBlacklist;


    /**
     * getter for campName
     * @return campName
     */
    public String getCampName() {
        return campName;
    }

    /**
     * setter for campName
     * @param campName
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * getter for camp ID
     * @return campName
     */
    public String getID() {
        return campName;
    } // ID to use unique campName

    /**
     * getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     * getter for start date of camp
     * @return campStartDate
     */
    public LocalDate getCampStartDate() {
        return campStartDate;
    }

    /**
     * setter for start date of camp
     * @param campStartDate
     */
    public void setCampStartDate(LocalDate campStartDate) {
        this.campStartDate = campStartDate;
    }

    /**
     * setter for end date of camp
     * @return campEndDate
     */
    public LocalDate getCampEndDate() {
        return campEndDate;
    }

    /**
     * setter for end date of camp
     * @param campEndDate
     */
    public void setCampEndDate(LocalDate campEndDate) {
        this.campEndDate = campEndDate;
    }

    /**
     * getter for registration end date
     * @return campRegistrationEndDate
     */
    public LocalDate getCampRegistrationEndDate() {
        return campRegistrationEndDate;
    }

    /**
     * setter for registration end date
     * @param campRegistrationEndDate
     */
    public void setCampRegistrationEndDate(LocalDate campRegistrationEndDate) {
        this.campRegistrationEndDate = campRegistrationEndDate;
    }

    /**
     * getter for usergroup (faculty) the camp is open to
     * @return userGroupOpenTo
     */
    public Faculty getUserGroupOpenTo() {
        return userGroupOpenTo;
    }

    /**
     * setter for usergroup (faculty) the camp is open to
     * @param userGroupOpenTo
     */
    public void setUserGroupOpenTo(Faculty userGroupOpenTo) {
        this.userGroupOpenTo = userGroupOpenTo;
    }

    /**
     * getter for location of camp
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter for location of camp
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter for total slots
     * @return totalSlots
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * setter for total slots
     * @param totalSlots
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * getter for camp committee slots
     * @return campCommitteeSlots
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * setter for camp committee slots
     * @param campCommitteeSlots
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * getter for staff in charge ID
     * @return staffInChargeID
     */
    public String getStaffInChargeID() {
        return staffInChargeID;
    }

    /**
     * setter for students registered
     * @param studentsArray
     */
    public void setStudentsRegistered(Student[] studentsArray) {
        this.studentsRegistered.clear();
        this.studentsRegistered.addAll(Arrays.asList(studentsArray));
    }

    /**
     * getter for committee registered
     * @return ArrayList of Student
     */
    public ArrayList<Student> getCommitteeRegistered() {
        return committeeRegistered;
    }

    /**
     * setter for committeeRegistered
     * @param committeeRegistered
     */
    public void setCommitteeRegistered(ArrayList<Student> committeeRegistered) {
        this.committeeRegistered = committeeRegistered;
    }

    /**
     * getter for visibility of camp to student
     * @return visibilityToStudent
     */
    public boolean getVisibilityToStudent() {
        return visibilityToStudent;
    }

    /**
     * setter for visibility of camp to student
     * @param visibilityToStudent
     */
    public void setVisibilityToStudent(boolean visibilityToStudent) {
        this.visibilityToStudent = visibilityToStudent;
    }

    /**
     * Camp Constructor
     * @param campName
     * @param description
     * @param campStartDate
     * @param campEndDate
     * @param campRegistrationEndDate
     * @param userGroupOpenTo
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param staffInChargeID
     * @param visibilityToStudent
     */
    public Camp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, Boolean visibilityToStudent) {
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        
        this.studentsRegistered = new ArrayList<>();
        this.studentBlacklist = new ArrayList<>();
        this.committeeRegistered = new ArrayList<>();
        this.staffInChargeID = staffInChargeID;
        this.visibilityToStudent = visibilityToStudent;
    }
    
    /**
     * register student to camp
     * @param student
     */
    public void registerStudent(Student student) {
        studentsRegistered.add(student);
    }

    /**
     * register camp committee member to camp
     * @param student
     */
    public void registerCampCommitteeMember(Student student) {
        committeeRegistered.add(student);
    }

    /**
     * add student to this camps blacklist
     * @param student
     */
    public void addToBlacklist(Student student){
        studentBlacklist.add(student);
    }
    
    
    /**
     * Withdraw student from camp
     * @param student
     * @throws EntityNotFoundException
     */
    public void withdrawStudent(Student student) throws EntityNotFoundException {
        boolean isRemoved = studentsRegistered.remove(student);
        if (!isRemoved) {
            throw new EntityNotFoundException("Student is not registered for this camp.");
        }
        studentBlacklist.add(student);
        totalSlots++;
    }

    /**
     * get list of registered students
     * @return ArrayList of Student
     */
    public ArrayList<Student> getStudentsRegistered() {
        return studentsRegistered;
    }

    /**
     * get Blacklist of students
     * @return ArrayList of Student
     */
    public ArrayList<Student> getStudentBlacklist() {
        return studentBlacklist;
    }



    /**
     * Check if student can register to camp
     * @param student
     * @return true if student can register
     * @throws RegistrationClosedException
     * @throws FacultyNotEligibleException
     * @throws CampFullException
     * @throws BlacklistedStudentException
     */
    public boolean canStudentRegister(Student student) throws RegistrationClosedException, FacultyNotEligibleException, CampFullException, BlacklistedStudentException {
        LocalDate now = LocalDate.now();
        if(now.isAfter(campRegistrationEndDate)){ //comment out for test
            throw new RegistrationClosedException();
        }
        if(student.getFaculty() != userGroupOpenTo && userGroupOpenTo != Faculty.ALL){
            throw new FacultyNotEligibleException();
        }
        if (studentsRegistered.size() >= (totalSlots - campCommitteeSlots)) {
            throw new CampFullException();
        }
        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        return true;
    }

    /**
     * Check if committee member can register
     * @param student
     * @return true if committee member can register
     * @throws RegistrationClosedException
     * @throws FacultyNotEligibleException
     * @throws CampFullException
     * @throws BlacklistedStudentException
     */
    public boolean canCampCommitteeMemberRegister(Student student) throws RegistrationClosedException, FacultyNotEligibleException, CampFullException, BlacklistedStudentException {
        LocalDate now = LocalDate.now();
        if(now.isAfter(campRegistrationEndDate)){ //comment out for test
            throw new RegistrationClosedException();
        }
        if(student.getFaculty() != userGroupOpenTo && userGroupOpenTo != Faculty.ALL){
            throw new FacultyNotEligibleException();
        }
        if (committeeRegistered.size() >= campCommitteeSlots) {
            throw new CampFullException();
        }
        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        return true;
    }

    /**
     * to string with separator
     * @param separator
     * @return String
     */
    public String toStringWithSeparator(String separator){
        StringBuilder sb = new StringBuilder();
        sb.append(campName).append(separator)
                .append(description).append(separator)
                .append(campStartDate).append(separator)
                .append(campEndDate).append(separator)
                .append(campRegistrationEndDate).append(separator)
                .append(userGroupOpenTo).append(separator)
                .append(location).append(separator)
                .append(totalSlots).append(separator)
                .append(campCommitteeSlots).append(separator)
                .append(staffInChargeID).append(separator)
                .append(visibilityToStudent);
        return sb.toString();

    }
}
