package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;
/**
 * StaffRepository class for managing staff entities in the data store.
 */
public class StaffRepository extends Repository {

    /**
     * Constructor for Staff Repository
     */
    public StaffRepository() {
        super();
        setFilePath("staff.csv");
    }
    /**
     * Returns a formatter function for converting an Entity to a formatted String representation.
     */
    @Override
    protected Function<Entity, String> formatter() {
        return staff -> {
            Staff staffEntity = (Staff) staff;
            return staffEntity.getName() + SEPARATOR + staffEntity.getEmail() + SEPARATOR + staffEntity.getPassword() + SEPARATOR + staffEntity.getFaculty();
        };
    }
    /**
     * Returns a LineMapper for converting a formatted String representation to a Staff entity.
     */
    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> new Staff(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
    }

    /**
     * Gets the list of staff members stored in the repository
     *
     * @return the list of staff members
     */
    public List<Staff> getAllStaff() {
        List<Entity> entities = super.getAll();
        List<Staff> staffMembers = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Staff) {
                staffMembers.add((Staff) entity);
            }
        }
        return staffMembers;
    }

    /**
     * Gets a staff member by ID
     *
     * @param entityID the ID of the staff member to find
     * @return the staff member with the given ID
     */
    public Staff getStaffByID(String entityID) throws EntityNotFoundException {
        Staff staff;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Staff) {
            staff = (Staff) entity;
        } 
        else {
            throw new EntityNotFoundException();
        }
        return staff;
    }
}
