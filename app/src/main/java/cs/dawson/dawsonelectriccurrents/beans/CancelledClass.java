package cs.dawson.dawsonelectriccurrents.beans;

import java.io.Serializable;

/**
 * This is the teacher bean.  Contains getters and setters.
 * @author Alessandrio Ciotola
 * @version 1.0
 */

public class CancelledClass implements Serializable
{
    private String title;
    private String course;
    private String teacher;
    private String dateTimeCancelled;

    /**
     * Default Constructor
     */
    public CancelledClass()
    {
        this("", "", "", "");
    }

    /**
     * Constructor
     * @param title
     * @param course
     * @param teacher
     * @param dateTimeCancelled
     */
    public CancelledClass(String title, String course, String teacher, String dateTimeCancelled)
    {
        this.title = title;
        this.course = course;
        this.teacher = teacher;
        this.dateTimeCancelled = dateTimeCancelled;
    }

    // Getters and setters

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public String getDateTimeCancelled()
    {
        return dateTimeCancelled;
    }

    public void setDateTimeCancelled(String dateTimeCancelled)
    {
        this.dateTimeCancelled = dateTimeCancelled;
    }
}
