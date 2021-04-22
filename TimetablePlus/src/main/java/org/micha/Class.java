package org.micha;

public class Class {

    private static int count = 0;
    private final int id;
    private final String name;
    private final String lecturer;
    private final String type;
    private final String day;
    private final String time;
    private final String length;
    private final String notes;

    public Class(String theName, String theLecturer, String theType, String theDay, String theTime, String theLength, String theNotes) {
        id = count++;
        name = theName;
        lecturer = theLecturer;
        type = theType;
        day = theDay;
        time = theTime;
        length = theLength;
        notes = theNotes;
    }

    public int getId() {
        return id;
    }

    public void setIdCount(int theId) {
        count = theId;
    }

    public String getName() {
        return name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getType() {
        return type;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getLength() {
        return length;
    }

    public String getNotes() {
        return notes;
    }

}