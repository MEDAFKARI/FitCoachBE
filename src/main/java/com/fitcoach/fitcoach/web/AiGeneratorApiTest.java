package com.fitcoach.fitcoach.web;


import com.fitcoach.fitcoach.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLEngineResult;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiGeneratorApiTest {

    private final AiService aiService;


    @GetMapping("/generate")
    public ResponseEntity<?> GenerateWorkout(){
        return new ResponseEntity(aiService.generateProgram(24,"Bodybuilding","beginner","Full Gym",3), HttpStatus.OK);
    }



}
