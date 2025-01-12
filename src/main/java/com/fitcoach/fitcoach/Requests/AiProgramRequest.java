package com.fitcoach.fitcoach.Requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AiProgramRequest {
    private int age;
    private String goal;
    private String level;
    private String equipement;
    private int daysPerWeek;
}
