package com.fitcoach.fitcoach.Responses;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.List;

public class DayPlanDTO {
    private String day;
    private List<String> exercises;

    public String getDay() {
        return day;
    }

    @JsonAnySetter
    public void setDayPlan(String key, List<String> value) {
        this.day = key;
        this.exercises = value;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "DayPlanDTO{" +
                "day='" + day + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
