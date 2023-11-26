package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;
/**
 * EnquiryRepository class for managing enquiry entities in the data store.
 */
public class EnquiryRepository extends Repository{

    private CampRepository campRepository;
    private StudentRepository studentRepository;

    /**
     * Constructor for Enquiry Repository
     * @param campRepository
     * @param studentRepository
     */
    public EnquiryRepository(CampRepository campRepository, StudentRepository studentRepository) {
        super();
        setFilePath("enquiry.csv");
        this.campRepository = campRepository;
        this.studentRepository = studentRepository;
    }
    /**
     * Returns a formatter function for converting an Entity to a formatted String representation.
     */
    @Override
    protected Function<Entity, String> formatter() {
        return enquiry -> {
            Enquiry enquiryEntity = (Enquiry) enquiry;
            return enquiryEntity.getID() + SEPARATOR + enquiryEntity.getCamp().getID() + SEPARATOR + enquiryEntity.getStudent().getID() + SEPARATOR +enquiryEntity.getStaffID() + SEPARATOR + enquiryEntity.getQuery() + SEPARATOR + enquiryEntity.getAnswer();
        };
    }
    /**
     * Returns a LineMapper for converting a formatted String representation to a Enquiry entity.
     */
    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatStringToEnquiry(fields);
    }

    /**
     * get a list of all enquiries
     * @return list of Enquiry
     */
    public List<Enquiry> getAllEnquiry() {
        List<Entity> entities = super.getAll();
        List<Enquiry> enquiries = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Enquiry) {
                enquiries.add((Enquiry) entity);
            }
        }
        return enquiries;
    }

    /**
     * get an enquiry from the string ID
     * @param entityID
     * @return Enquiry
     * @throws EntityNotFoundException
     */
    public Enquiry getEnquiryByID(String entityID) throws EntityNotFoundException {
        Enquiry enquiry;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Enquiry) {
            enquiry = (Enquiry) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return enquiry;
    }

    /**
     * Format string to enquiry
     * @param fields
     * @return Enquiry
     */
    private Enquiry formatStringToEnquiry(String[] fields){
        Enquiry enquiry = null;
        try{
            String enquiryID = fields[0].trim();
            Camp camp = campRepository.getCampByID(fields[1].trim());
            Student student = studentRepository.getStudentByID(fields[2].trim());
            String staffID = fields[3].trim();
            String query = fields[4].trim();
            String answer = fields[5].trim();
            enquiry = new Enquiry(enquiryID, staffID, student, camp, query, answer);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to map Enquiry");
        }
        return enquiry;
    }
}
