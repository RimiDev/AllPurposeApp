package cs.dawson.dawsonelectriccurrents.beans;

import java.io.Serializable;

public class CancelledClass implements Serializable
{
    private String title;
    private String course;
    private String teacher;
    private String dateTimeCancelled;

    public CancelledClass()
    {
        this("", "", "", "");
    }

    public CancelledClass(String title, String course, String teacher, String dateTimeCancelled)
    {
        this.title = title;
        this.course = course;
        this.teacher = teacher;
        this.dateTimeCancelled = dateTimeCancelled;
    }

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
