package com.fitcoach.fitcoach.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcoach.fitcoach.Requests.AiProgramRequest;
import com.fitcoach.fitcoach.models.ProgramAi;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.layout.Document;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService{

    private String AiUrl= "http://127.0.0.1:8000";

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateProgram(int age, String goal, String level, String equipment, int daysPerWeek) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("age", age);
        requestBody.put("days_per_week", daysPerWeek);
        requestBody.put("level", level);
        requestBody.put("goal", goal);
        requestBody.put("equipment", equipment);

        String response = restTemplate.postForObject(AiUrl + "/recommend_program/", requestBody,String.class);
        System.out.println(response);
        String validResponse = response.replace("\"[", "[").replace("]\"", "]").replace("\\\"", "\"");
        System.out.println(validResponse);

        try {
            System.out.println(getGeneratedProgram(validResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public void generatePdfFromProgram() {
        String outputPath = "src/main/resources/Pdfs/Output.pdf";
        try {
            PdfWriter pdfWriter=new PdfWriter(outputPath);
            PdfDocument pdfDocument=new PdfDocument(pdfWriter);
            Document document= new Document(pdfDocument);
            document.add(new Paragraph("Text content......."));
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public ProgramAi getGeneratedProgram(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProgramAi programAi= new ProgramAi();

        // Deserialize JSON into a list of maps
        List<Map<String, List<String>>> program = objectMapper.readValue(json, new TypeReference<>() {});
        programAi.setProgram(program);
        return programAi;
    }

}
