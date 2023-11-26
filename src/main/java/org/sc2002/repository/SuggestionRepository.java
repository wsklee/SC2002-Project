package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

/**
 * SuggestionRepository class for managing suggestion entities in the data store.
 */
public class SuggestionRepository extends Repository{

    private CampRepository campRepository;
    private StudentRepository studentRepository;
    /**
     * Constructor for SuggestionRepository
     * @param campRepository
     * @param studentRepository
     */
    public SuggestionRepository(CampRepository campRepository, StudentRepository studentRepository) {
        super();
        setFilePath("suggestion.csv");
        this.campRepository = campRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Returns a formatter function for converting an Entity to a formatted String representation.
     */
    @Override
    protected Function<Entity, String> formatter() {
        return suggestion -> {
            Suggestion suggestionEntity = (Suggestion) suggestion;
            return suggestionEntity.getID() + SEPARATOR + suggestionEntity.getCamp().getID()+ SEPARATOR + suggestionEntity.getStudent().getID() + SEPARATOR + suggestionEntity.getStaffID() + SEPARATOR + suggestionEntity.getSuggestion() + SEPARATOR + suggestionEntity.getApproved();
        };
    }

    /**
     * Returns a LineMapper for converting a formatted String representation to a Suggestion entity.
     */
    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatStringToSuggestion(fields);
    }

    /**
     * Get a list of suggestions
     * @return List of Suggestions
     */
    public List<Suggestion> getAllSuggestions() {
        List<Entity> entities = super.getAll();
        List<Suggestion> suggestions = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Suggestion) {
                suggestions.add((Suggestion) entity);
            }
        }
        return suggestions;
    }

    /**
     * Returns a Suggestion from the entityID
     * @param entityID
     * @return Suggestion
     * @throws EntityNotFoundException
     */
    public Suggestion getSuggestionByID(String entityID) throws EntityNotFoundException {
        Suggestion suggestion;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Suggestion) {
            suggestion = (Suggestion) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return suggestion;
    }

    /**
     * Format String to Suggestion
     * @param fields
     * @return Suggestion
     */
    private Suggestion formatStringToSuggestion(String[] fields){
        Suggestion suggestion = null;
        try{
            String suggestionID = fields[0].trim();
            Camp camp = campRepository.getCampByID(fields[1].trim());
            Student student = studentRepository.getStudentByID(fields[2].trim());
            String staffID = fields[3].trim();
            String suggestionContent = fields[4].trim();
            Boolean isApproved = fields[5].trim().equalsIgnoreCase("true");
            suggestion = new Suggestion(suggestionID, staffID, student, camp,suggestionContent, isApproved );
        } catch (EntityNotFoundException e){
            System.out.println("Failed to map Enquiry");
        }
        return suggestion;
    }

}
