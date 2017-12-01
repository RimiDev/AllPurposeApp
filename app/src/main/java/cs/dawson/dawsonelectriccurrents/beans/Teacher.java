package cs.dawson.dawsonelectriccurrents.beans;

import java.io.Serializable;

/**
 * This is the teacher bean.  Contains getters and setters.
 * @author  Kevin
 * @version 1.0
 */

public class Teacher implements Serializable {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String office;
    private String local;
    private String website;
    private String bio;
    private String position;
    private String department;
    private String sector;

    /**
     * Default constructor
     */
    public Teacher() {
        this.firstName = "";
        this.lastName = "";
        this.fullName = "";
        this.email = "";
        this.office = "";
        this.local = "";
        this.website = "";
        this.bio = "";
        this.position = "";
        this.department = "";
        this.sector = "";
    }

    /**
     * Constructor with parameters
     * @param fn
     * @param ln
     * @param email
     * @param office
     * @param local
     * @param website
     * @param bio
     * @param position
     * @param department
     * @param sector
     */
    public Teacher(String fn, String ln, String email, String office,
                   String local, String website, String bio, String position,
                   String department, String sector) {
        this.firstName = fn;
        this.lastName = ln;
        this.fullName = fn + " " + ln;
        this.email = email;
        this.office = office;
        this.local = local;
        this.website = website;
        this.bio = bio;
        this.position = position;
        this.department = department;
        this.sector = sector;
    }

    // Getters and setters

    public String getFirstName() { return firstName; }

    public void setFirstName(String fn) { this.firstName = fn; }

    public String getLastName() { return lastName; }

    public void setLastName(String ln) { this.lastName = ln; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getOffice() { return office; }

    public void setOffice(String office) { this.office = office; }

    public String getLocal() { return local; }

    public void setLocal(String local) { this.local = local; }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    public String getSector() { return sector; }

    public void setSector(String sector) { this.sector = sector; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Teacher other = (Teacher) obj;
        if (!this.getFirstName().equals(other.getFirstName())) {
            return false;
        }
        if (!this.getLastName().equals(other.getLastName())) {
            return false;
        }
        if (!this.getFirstName().equals(other.getFirstName())) {
            return false;
        }
        if (!this.getEmail().equals(other.getEmail())) {
            return false;
        }
        if (!this.getOffice().equals(other.getOffice())) {
            return false;
        }
        if (!this.getLocal().equals(other.getLocal())) {
            return false;
        }
        if (!this.getWebsite().equals(other.getWebsite())) {
            return false;
        }
        if (!this.getBio().equals(other.getBio())) {
            return false;
        }
        if (!this.getPosition().equals(other.getPosition())) {
            return false;
        }
        if (!this.getDepartment().equals(other.getDepartment())) {
            return false;
        }
        if (!this.getSector().equals(other.getSector())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Teacher{First name: " + firstName + ", Last name: " + lastName +
                "Full name: " + firstName + " " + lastName + ", Email: " + email +
                ", Office: " + office + ", Local: " + local + ", Website: " + website +
                ", Bio: " + bio + ", Position: " + position + ", Department: " +
                department + ", Sector: " + sector + "}";
    }

}
