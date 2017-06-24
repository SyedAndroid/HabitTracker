package com.example.syed.habittrackerapp;

/**
 * Created by syed on 2017-06-12.
 */

public class Habit {
    private String title;
    private String desc;
    private String repetition;
    private String status;
    private int id;

    public int getId() {
        return id;
    }

    public Habit(int id, String title, String desc, String repetition, String status) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.repetition = repetition;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getRepetition() {
        return repetition;
    }

    public String getStatus() {
        return status;
    }
}
