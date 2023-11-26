package org.sc2002.controller;

import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.CampStudentRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller class responsible for managing and coordinating operations related to Student in the application.
 */
public class StudentController {

    private CampController campController;
    private CampStudentRepository campStudentRepository;
    private StudentRepository studentRepository;

    /**
     * Constructor for StudentController
     * @param campController
     * @param campStudentRepository
     * @param studentRepository
     */
    public StudentController(CampController campController, CampStudentRepository campStudentRepository, StudentRepository studentRepository) {
        this.campController = campController;
        this.campStudentRepository = campStudentRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Withdraw Student from Camp
     * @param student
     * @param camp
     * @throws EntityNotFoundException
     * @throws Exception
     */
    public void withdrawFromCamp(Student student, Camp camp) throws EntityNotFoundException, Exception {
        if (student.isCampCommitteeMember() && student.getCommitteeMemberCamp().equals(camp)){
            throw new Exception("Committee members not allowed to withdraw from the camp");
        }
        if (student.getRegisteredCamps().contains(camp)) {
            camp.withdrawStudent(student);
            student.withdrawFromCamp(camp);
            campStudentRepository.update(new CampStudent(camp, student, CampRole.ATTENDEE, true));
        } else {
            throw new EntityNotFoundException("Student is not registered for the camp.");
        }
    }
    
    /**
     * Register Student To Camp
     * @param student
     * @param camp
     * @throws FacultyNotEligibleException
     * @throws CampFullException
     * @throws RegistrationClosedException
     * @throws BlacklistedStudentException
     * @throws RegisteredAlreadyException
     * @throws DuplicateEntityExistsException
     * @throws CampConflictException
     */
    public void registerCampAsStudent(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException, BlacklistedStudentException, RegisteredAlreadyException, DuplicateEntityExistsException, CampConflictException {
        if (student.getRegisteredCamps().contains(camp)){
            throw new RegisteredAlreadyException();
        }

        if (isTimeConflict(camp, student)){
            throw new CampConflictException();
        }

        if (camp.canStudentRegister(student)) {
            camp.registerStudent(student);
            student.registerForCamp(camp);
            campStudentRepository.add(new CampStudent(camp, student, CampRole.ATTENDEE, false));

        }
    }

    /**
     * Register Student to Camp as Committee
     * @param student
     * @param camp
     * @throws FacultyNotEligibleException
     * @throws CampFullException
     * @throws RegistrationClosedException
     * @throws BlacklistedStudentException
     * @throws RegisteredAlreadyException
     * @throws AlreadyCampCommitteeMemberException
     * @throws DuplicateEntityExistsException
     * @throws CampConflictException
     */
    public void registerCampAsCampCommitteeMember(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException, BlacklistedStudentException, RegisteredAlreadyException, AlreadyCampCommitteeMemberException, DuplicateEntityExistsException, CampConflictException{
        if (student.getRegisteredCamps().contains(camp)){
            throw new RegisteredAlreadyException();
        }

        if (student.isCampCommitteeMember()){
            throw new AlreadyCampCommitteeMemberException();
        }

        if (isTimeConflict(camp, student)){
            throw new CampConflictException();
        }

        if(camp.canCampCommitteeMemberRegister(student)){
            camp.registerCampCommitteeMember(student);
            student.registerForCampAsCampCommitteeMember(camp);
            campStudentRepository.add(new CampStudent(camp, student, CampRole.COMMITTEE, false));
        }
    }
    

    /**
     * Print Registered Camps
     * @param student
     */
    public void printRegisteredCamps(Student student){
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
        registeredCamps.stream().forEach(camp -> {
            String role = "STUDENT";
            if(student.isCampCommitteeMember() && student.getCommitteeMemberCamp() == camp){
                role = "COMMITTEE";
            }
            System.out.println("CAMP: " + camp.getCampName() + "\t ROLE: " + role);
        });
    }

    /**
     * View List of Camps
     * @param student
     * @return List of Camp
     */
    public List<Camp> ViewCamp(Student student) {
        List<Camp> camps = campController.getAllCamps();

        List<Camp> filteredCamps = camps.stream()
                .filter(camp -> (camp.getUserGroupOpenTo().equals(student.getFaculty()) || camp.getUserGroupOpenTo().equals(Faculty.ALL)))
                .collect(Collectors.toList());

        return filteredCamps;
    }

    /**
     * get Camps with Remaining Slots
     * @param student
     * @return A map containing the camp names as keys and their respective remaining slots as values.
     */
    public Map<String, Integer> getCampRemainSlots(Student student) {
        List<Camp> camps = ViewCamp(student);

        Map<String, Integer> CampRemainSlots = new HashMap<>();
        for (Camp camp : camps) {
            String name = camp.getCampName();
            int remainSlots = camp.getTotalSlots() - camp.getStudentsRegistered().size() - camp.getCommitteeRegistered().size();
            CampRemainSlots.put(name, remainSlots);
        }
        return CampRemainSlots;
    }

    /**
     * get List of Camps with Remaining available Slots
     * @param student
     * @return List of Camp
     */
    public List<Camp> getCampsWithRemainSlots(Student student) {
        List<Camp> camps = ViewCamp(student);
        return camps.stream().filter(camp -> {
            int remainSlots = camp.getTotalSlots() - camp.getStudentsRegistered().size() - camp.getCommitteeRegistered().size();
            return remainSlots > 0;
        }).collect(Collectors.toList());
    }

    /**
     * Add a point to the Student Committee Member
     * @param student
     */
    public void studentAddOnePoint(Student student){
        student.setPoint(student.getPoint()+1);
        try{
            studentRepository.update(student);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update point of the student: " + e.getMessage());
        }
    }


    /**
     * Check if there is time conflict
     * @param camp
     * @param student
     * @return true if there is time conflict
     */
    public boolean isTimeConflict(Camp camp, Student student){
        LocalDate campStartDate = camp.getCampStartDate();
        LocalDate campEndDate = camp.getCampEndDate();
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();

        for (Camp registeredCamp : registeredCamps){
            if (!(campEndDate.isBefore(registeredCamp.getCampStartDate()) || campStartDate.isAfter(registeredCamp.getCampEndDate()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Change Password
     * @param student
     * @param newPassword
     */
    public void changePassword(Student student, String newPassword){
        try{
            student.setPassword(newPassword);
            studentRepository.update(student);
            System.out.println("Successfully updated password");
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update password");
        }
    }

    /**
     * get List of All Students
     * @return List of Student
     */
    public List<Student> getAllStudents(){
        return studentRepository.getAllStudents();

    }

}


