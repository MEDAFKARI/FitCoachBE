package com.fitcoach.fitcoach.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcoach.fitcoach.Requests.AiProgramRequest;
import com.fitcoach.fitcoach.Requests.RecommendationRequest;
import com.fitcoach.fitcoach.Responses.DayPlanDTO;
import com.fitcoach.fitcoach.Responses.ProgramDTO;
import com.fitcoach.fitcoach.models.ProgramAi;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.layout.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class AiServiceImpl implements AiService{

    private String AiUrl= "http://127.0.0.1:8000";

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public ProgramDTO generateProgram(String username ,RecommendationRequest request) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        request.setEquipment(request.getEquipment().replace("_"," "));
        HttpEntity<RecommendationRequest> entity = new HttpEntity<>(request, headers);


        try {
            ResponseEntity<String> rawResponse = restTemplate.exchange(
                    AiUrl+"/recommend_program/",
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            System.out.println("Raw JSON Response: " + rawResponse.getBody());

            JSONObject jsonObject = getJsonObject(rawResponse);

            System.out.println("Updated and Valid JSON:");
            System.out.println(jsonObject.toString(4));

            ObjectMapper mapper = new ObjectMapper();
            ProgramDTO program = mapper.readValue(jsonObject.toString(4), ProgramDTO.class);

            System.out.println("Deserialized Program: " + program);

            String url = generatePdfFromProgram(username ,program);

            program.setProgramPdfUrl(url);

            return program;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public String generatePdfFromProgram(String username , ProgramDTO programDTO) {
        String directoryPath = "pdfs/";
        String outputPath = directoryPath + username +"_" + programDTO.getName() + "_" + UUID.randomUUID() + ".pdf";

        try {
            Path path = Path.of(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }


            PdfWriter pdfWriter = new PdfWriter(outputPath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Workout Program for "+ username +" Program : "+programDTO.getName()).setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(18));
            document.add(new Paragraph("\n"));

            float[] columnWidths = {100, 400};
            Table table = new Table(columnWidths);

            table.addHeaderCell(new Cell().add(new Paragraph("Day").setBold().setTextAlignment(TextAlignment.CENTER)));
            table.addHeaderCell(new Cell().add(new Paragraph("Exercises").setBold().setTextAlignment(TextAlignment.CENTER)));

            if (programDTO.getRecommendedProgram() != null) {
                for (DayPlanDTO dayWorkout : programDTO.getRecommendedProgram()) {
                    table.addCell(new Cell().add(new Paragraph(dayWorkout.getDay())));

                    String exercises = String.join("\n", dayWorkout.getExercises());
                    table.addCell(new Cell().add(new Paragraph(exercises)));
                }
            }

            document.add(table);
            document.close();

            System.out.println("PDF generated successfully: " + outputPath);

            return "http://localhost:9090/files/"+outputPath;

        } catch (IOException e) {
            throw new RuntimeException("Error while creating the PDF file", e);
        }
    }





    private static JSONObject getJsonObject(ResponseEntity<String> rawResponse) {
        try {
            JSONObject originalJson = new JSONObject(rawResponse.getBody());

            // Fix the "Recommended Program" field
            String recommendedProgramRaw = originalJson.getString("Recommended Program");

            // Replace single quotes in the nested structure to make it valid JSON
            String fixedRecommendedProgram = recommendedProgramRaw
                    .replace("'", "\"")  // Replace single quotes with double quotes
                    .replace(",}", "}")  // Fix trailing commas before closing braces
                    .replace(",]", "]"); // Fix trailing commas before closing brackets

            // Parse the fixed string into a JSONArray
            JSONArray recommendedProgramJsonArray = new JSONArray(fixedRecommendedProgram);

            // Update the original JSON object
            originalJson.put("Recommended Program", recommendedProgramJsonArray);


            return originalJson;
        } catch (Exception e) {
            // Log and handle errors
            System.err.println("Error parsing or fixing JSON: " + e.getMessage());
            throw new RuntimeException("Invalid JSON format", e);
        }
    }

}
