package org.sc2002.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Represents a Staff in the application
 */
public class Staff extends User {

    private List<Camp> createdCamps;

    /**
     * Staff Constructor
     * @param name
     * @param email
     * @param password
     * @param faculty
     */
    public Staff(String name, String email, String password ,String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
        this.createdCamps = new ArrayList<>();
    }
    
    /**
     * getID of Staff
     * @return String
     */
    @Override
    public String getID() {
        Pattern pattern = Pattern.compile("^(.*?)@");
        Matcher matcher = pattern.matcher(super.getEmail());
        if (matcher.find()) {
            // Group 1 contains the user ID
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * get Created Camps
     * @return List of Camp
     */
    public List<Camp> getCreatedCamps() {
        return createdCamps;
    }

    /**
     * add to Created Camps
     * @param newCamp
     */
    public void addToCreatedCamps(Camp newCamp) {
        this.createdCamps.add(newCamp); // need to filter out duplicate camps
    }

    /**
     * delete from Created Camps
     * @param campToDelete
     */
    public void deleteFromCreatedCamps(Camp campToDelete){
        this.createdCamps.remove(campToDelete); // need to filter out if camp exists
    }
}
