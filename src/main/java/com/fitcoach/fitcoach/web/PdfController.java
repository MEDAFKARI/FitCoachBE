package com.fitcoach.fitcoach.web;

import com.fitcoach.fitcoach.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfUtils pdfUtils;

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Resource file = pdfUtils.load(filename);
            if (file == null || !file.exists()) {
                throw new RuntimeException("File not found: " + filename);
            }
            String contentType = "application/pdf";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(file);
        } catch (Exception e) {
            throw new RuntimeException("Error while serving the file: " + filename, e);
        }
    }

//    http://localhost:9090/pdf/files/output.pdf for testing the pdf hahowa link a sat 

}
