package io.pivotal.pal.tracker;

import java.sql.Time;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Map;

public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;




    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;

    }

    public TimeEntry() {

    }

    public TimeEntry(TimeEntry timeEntry) {
        this.id = timeEntry.getId();
        this.projectId = timeEntry.getProjectId();
        this.userId = timeEntry.getUserId();
        this.date = timeEntry.getDate();
        this.hours = timeEntry.getHours();
    }

    public TimeEntry(Map<String, Object> timeEntryData) {
        this.id = (Long)timeEntryData.get("id");
        this.projectId = (Long)timeEntryData.get("project_id");
        this.userId = (Long)timeEntryData.get("user_id");
        this.date = ((Date)timeEntryData.get("date")).toLocalDate();
        this.hours = (Integer)timeEntryData.get("hours");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }



    @Override
    public boolean equals(Object timeEntry) {
        return ((TimeEntry)timeEntry).getId() == this.id;
    }
}
