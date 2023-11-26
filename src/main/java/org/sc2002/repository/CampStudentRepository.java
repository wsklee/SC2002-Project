package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static org.sc2002.repository.DBcsv.SEPARATOR;

/**
 * CampStudentRepository class for managing campstudent entities in the data store.
 */
public class CampStudentRepository extends Repository{

    private CampRepository campRepository;
    private StudentRepository studentRepository;


    /**
     * Constructor for CampStudentRepository
     * @param campRepository
     * @param studentRepository
     */
    public CampStudentRepository(CampRepository campRepository, StudentRepository studentRepository) {
        super();
        setFilePath("campStudent.csv");
        this.campRepository = campRepository;
        this.studentRepository = studentRepository;
    }
    /**
     * Returns a formatter function for converting an Entity to a formatted String representation.
     */
    @Override
    protected Function<Entity, String> formatter() {
        return campStudent -> {
            CampStudent campStudentEntity = (CampStudent) campStudent;
            return campStudentEntity.getCamp().getID() + SEPARATOR + campStudentEntity.getStudent().getID() + SEPARATOR + campStudentEntity.getCampRole() + SEPARATOR + campStudentEntity.getHasWithdrawn();
        };
    }
    /**
     * Returns a LineMapper for converting a formatted String representation to a CampStudent entity.
     */
    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatStringToCampStudent(fields);
    }

    /**
     * Loads entities from the data store.
     */
    @Override
    public void load() {
        super.load();
        for(CampStudent campStudent : getAllCampStudent()){
            Student student = campStudent.getStudent();
            Camp camp = campStudent.getCamp();
            if(campStudent.getHasWithdrawn()){
                camp.addToBlacklist(student);
            } else if(campStudent.getCampRole().equals(CampRole.COMMITTEE)){
                camp.registerCampCommitteeMember(student);
                student.registerForCampAsCampCommitteeMember(camp);
            } else {
                camp.registerStudent(student);
                student.registerForCamp(camp);
            }
        }
    }

    /**
     * Get a list of all camp students
     * @return list of CampStudent
     */
    public List<CampStudent> getAllCampStudent() {
        List<Entity> entities = super.getAll();
        List<CampStudent> campStudents = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof CampStudent) {
                campStudents.add((CampStudent) entity);
            }
        }
        return campStudents;
    }

    /**
     * Format String to Camp Student
     * @param fields
     * @return CampStudent
     */
    private CampStudent formatStringToCampStudent(String[] fields){
        CampStudent campStudent = null;
        try{
            Camp camp = campRepository.getCampByID(fields[0].trim());
            Student student = studentRepository.getStudentByID(fields[1].trim());
            campStudent = new CampStudent(camp, student, CampRole.valueOf(fields[2].trim()), fields[3].trim().equals("true"));
        } catch (EntityNotFoundException e){
            System.out.println("Failed to map CampStudent");
        }
        return campStudent;
    }
}
