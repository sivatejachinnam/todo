package com.dcrewit.todo;

public class Task {
    private String message;
    private String description;
    private String datetime;
    public Task() {
    }

    public Task(String message, String description,String datetime) {
        this.message = message;
        this.description = description;
        this.datetime=datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
