package com.fitcoach.fitcoach.web;


import com.fitcoach.fitcoach.Requests.RecommendationRequest;
import com.fitcoach.fitcoach.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiGeneratorApiTest {

    private final AiService aiService;


    @PostMapping("/generate/{username}")
    public ResponseEntity<?> GenerateWorkout(@PathVariable("username")String username ,@RequestBody RecommendationRequest request){
        return new ResponseEntity(aiService.generateProgram(username, request), HttpStatus.OK);
    }

//    @GetMapping("/generatePdf")
//    public ResponseEntity<?> GeneratePdf(){
//        aiService.generatePdfFromProgram();
//        return new ResponseEntity("Pdf Created", HttpStatus.OK);
//    }



}
