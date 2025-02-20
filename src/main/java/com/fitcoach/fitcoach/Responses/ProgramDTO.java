package com.fitcoach.fitcoach.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProgramDTO {



    @JsonProperty("Program Name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String programName) {
        name = programName;
    }

    @JsonProperty("Recommended Program")
    private List<DayPlanDTO> recommendedProgram;


    private String programPdfUrl;

    // Getters and setters
    public List<DayPlanDTO> getRecommendedProgram() {
        return recommendedProgram;
    }

    public void setRecommendedProgram(List<DayPlanDTO> recommendedProgram) {
        this.recommendedProgram = recommendedProgram;
    }

    @Override
    public String toString() {
        return "ProgramDTO{" +
                "recommendedProgram=" + recommendedProgram +
                '}';
    }
}
