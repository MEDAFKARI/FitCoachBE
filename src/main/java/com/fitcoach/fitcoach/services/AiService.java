package com.fitcoach.fitcoach.services;

import java.util.Map;

public interface AiService {
    public String generateProgram(int age, String goal, String level, String equipement, int daysPerWeek);

    public void GeneratePdfFromProgram();
}
