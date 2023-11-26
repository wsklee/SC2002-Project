package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.Suggestion;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.SuggestionRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class responsible for managing and coordinating operations related to Suggestion in the application.
 */
public class SuggestionController {

    private SuggestionRepository suggestionRepository;

    private CampRepository campRepository;

    /**
     * SuggestionController Constructor
     * @param suggestionRepository
     * @param campRepository
     */
    public SuggestionController(SuggestionRepository suggestionRepository, CampRepository campRepository) {
        this.suggestionRepository = suggestionRepository;
        this.campRepository = campRepository;
    }

    /**
     * Add a Suggestion
     * @param suggestion
     * @return true, if successful
     */
    public boolean addSuggestion(Suggestion suggestion){
        try{
            suggestionRepository.add(suggestion);
            return true;
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
        }
        return false;
    }

    /**
     * Edit a Suggestion
     * @param suggestion
     */
    public void editSuggestion(Suggestion suggestion){
        try{
            suggestionRepository.update(suggestion);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to edit entity: " + e.getMessage());
        }
    }

    /**
     * Delete a Suggestion
     * @param suggestionID
     */
    public void deleteSuggestion(String suggestionID){
        try{
            suggestionRepository.remove(suggestionID);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }

    /**
     * get Suggestions by Staff
     * @param staff
     * @return List of Suggestion
     */
    public List<Suggestion> getSuggestionByStaff(Staff staff){
        return suggestionRepository.getAllSuggestions().stream().filter(suggestion ->
                suggestion.getStaffID().equals(staff.getID())).collect(Collectors.toList());
    }

    /**
     * get Suggestions by Camp
     * @param camp
     * @return List of Suggestion
     */
    public List<Suggestion> getSuggestionByCamp(Camp camp){
        return suggestionRepository.getAllSuggestions().stream().filter(suggestion ->
                suggestion.getCamp().equals(camp)).collect(Collectors.toList());
    }

    /**
     * get Suggestions by Student
     * @param student
     * @return List of Suggestion
     */
    public List<Suggestion> getSuggestionByStudent(Student student){
        return suggestionRepository.getAllSuggestions().stream().filter(suggestion ->
                suggestion.getStudent().equals(student)).collect(Collectors.toList());
    }
}
