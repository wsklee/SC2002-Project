package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;


import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;

/**
 * Controller class responsible for managing and coordinating operations related to Staff in the application.
 */
public class StaffController {

    private CampController campController;
    private StaffRepository staffRepository;

    /**
     * StaffController Constructor
     * @param campController
     * @param staffRepository
     */
    public StaffController(CampController campController, StaffRepository staffRepository) {
        this.campController = campController;
        this.staffRepository = staffRepository;
    }

    /**
     * Create a Camp
     * @param campName
     * @param description
     * @param campStartDate
     * @param campEndDate
     * @param campRegistrationEndDate
     * @param userGroupOpenTo
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param staff
     * @param visibilityToStudent
     * @return Camp
     * @throws DuplicateEntityExistsException
     * @throws EntityNotFoundException
     */
    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staff, boolean visibilityToStudent) throws DuplicateEntityExistsException, EntityNotFoundException{
        Camp camp = campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staff.getID(), visibilityToStudent);
        staff.addToCreatedCamps(camp);
        staffRepository.update(staff);
        return camp;

    }

    /**
     * Edit a Camp
     * @param camp
     * @return camp
     * @throws EntityNotFoundException
     */
    public Camp editCamp(Camp camp) throws EntityNotFoundException{
        Camp editedCamp = campController.editCamp(camp);
        return editedCamp;
    }

    /**
     * Delete a Camp
     * @param camp
     * @param staff
     * @throws EntityNotFoundException
     */
    public void deleteCamp(Camp camp, Staff staff) throws EntityNotFoundException{
        campController.deleteCamp(camp.getID());
        staff.deleteFromCreatedCamps(camp);
        staffRepository.update(staff);
    }




    /**
     * Method to toggle the visibility of a camp
     * @param camp
     * @param staff
     * @throws WrongStaffException
     * @throws EntityNotFoundException
     */
    public void toggleVisibilityOfCamp(Camp camp, Staff staff) throws WrongStaffException, EntityNotFoundException {
        Camp findCamp = campController.getCamp(camp.getID());
        if(findCamp.getStaffInChargeID().equals(staff.getID())){
            findCamp.setVisibilityToStudent(!findCamp.getVisibilityToStudent());
            campController.editCamp(findCamp);
        } else {
            throw new WrongStaffException();
        }
    }

    /**
     * get All Camps Created by Staff
     * @param staffID
     * @return List of Camp
     */
    public List<Camp> getAllCampsICreated(String staffID) {
        return campController.getAllCamps().stream().filter(camp -> camp.getStaffInChargeID().equals(staffID)).collect(Collectors.toList());
    }

    /**
     * Change Password
     * @param staff
     * @param newPassword
     */
    public void changePassword(Staff staff, String newPassword){
        try{
            staff.setPassword(newPassword);
            staffRepository.update(staff);
            System.out.println("Successfully updated password");
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update password");
        }
    }
}
