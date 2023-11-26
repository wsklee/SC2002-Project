package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class responsible for managing and coordinating operations related to camps in the application.
 */
public class CampController {

    private CampRepository campRepository;

    /**
     * CampController Constructor
     * @param campRepository
     */
    public CampController(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    /**
     * Method to Create a Camp
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
     * @return Camp
     * @throws DuplicateEntityExistsException
     */
    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, boolean visibilityToStudent) throws DuplicateEntityExistsException{

        Camp camp = new Camp( campName,  description,  campStartDate,  campEndDate,  campRegistrationEndDate,  userGroupOpenTo,  location, totalSlots,  campCommitteeSlots, staffInChargeID, visibilityToStudent);

        try{
            campRepository.add(camp);
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
            throw e;
        }
        return camp;
    }

    /**
     * Method to Edit a Camp
     * @param camp
     * @return Camp
     * @throws EntityNotFoundException
     */
    public Camp editCamp(Camp camp) throws EntityNotFoundException{
        try{
            campRepository.update(camp);
            return camp;
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update entity: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Method to Delete a Camp
     * @param campId
     */
    public void deleteCamp(String campId){
        try{
            campRepository.remove(campId);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }

    /**
     * get Camp from campID
     * @param campId
     * @return Camp
     * @throws EntityNotFoundException
     */
    public Camp getCamp(String campId) throws EntityNotFoundException{
        return campRepository.getCampByID(campId);
    }

    /**
     * get a list of All Camps
     * @return List of camp
     */
    public List<Camp> getAllCamps(){
        return campRepository.getAllCamps();
    }
}

