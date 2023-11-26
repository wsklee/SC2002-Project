package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;
import static org.sc2002.utils.CAMSDateFormat.formatStringToDate;
/**
 * CampRepository class for managing camp entities in the data store.
 */
public class CampRepository extends Repository{

    /**
     * Constructor for CampRepository
     */
    public CampRepository() {
        super();
        setFilePath("camp.csv");
    }
    /**
     * Returns a formatter function for converting an Entity to a formatted String representation.
     */
    @Override
    protected Function<Entity, String> formatter() {
        return camp -> formatEntityToCampString(camp);
    }
    /**
     * Returns a LineMapper for converting a formatted String representation to a Camp entity.
     */
    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatCampStringToCamp(fields);
    }

    /**
     * Gets the list of camps stored in the repository
     *
     * @return the list of camps
     */
    public List<Camp> getAllCamps() {
        List<Entity> entities = super.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Camp) {
                camps.add((Camp) entity);
            }
        }
        return camps;
    }

    /**
     * get Camp by string entityID
     * @param entityID
     * @return Camp
     * @throws EntityNotFoundException
     */
    public Camp getCampByID(String entityID) throws EntityNotFoundException {
        Camp camp;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Camp) {
            camp = (Camp) entity;
        } 
        else {
            throw new EntityNotFoundException();
        }
        return camp;
    }

    /**
     * format entity to camp string
     * @param entity
     * @return String of camp
     */
    private String formatEntityToCampString(Entity entity){
        Camp camp = (Camp) entity;
        return camp.toStringWithSeparator(SEPARATOR);
    }

    /**
     * format camp String to Camp class
     * @param fields
     * @return Camp
     */
    private Camp formatCampStringToCamp(String[] fields){
        return new Camp(
                fields[0].trim(), fields[1].trim(), formatStringToDate(fields[2].trim()),
                formatStringToDate(fields[3].trim()), formatStringToDate(fields[4].trim()),
                Faculty.valueOf(fields[5].trim()),
                fields[6].trim(), Integer.parseInt(fields[7].trim()), Integer.parseInt(fields[8].trim()) , fields[9].trim(), fields[10].trim().equals("true")
        );

    }
}
