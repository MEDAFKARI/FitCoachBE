package com.fitcoach.fitcoach.services;

import com.fitcoach.fitcoach.Requests.RecommendationRequest;
import com.fitcoach.fitcoach.Responses.ProgramDTO;

import java.util.Map;

public interface AiService {
    public ProgramDTO generateProgram(String username ,RecommendationRequest request);

    public String generatePdfFromProgram(String username ,ProgramDTO programDTO);
}
