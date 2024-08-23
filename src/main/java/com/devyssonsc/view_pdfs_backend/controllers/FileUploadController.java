package com.devyssonsc.view_pdfs_backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devyssonsc.view_pdfs_backend.Services.PdfFileService;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    
    @Autowired
    private PdfFileService pdfFileService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file)
    {
        try {
            String fileName = file.getOriginalFilename();
            String htmlContent = new String(file.getBytes());

            pdfFileService.convertAndCreate(fileName, htmlContent);

            return ResponseEntity.ok("Arquivo convertido e salvo com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao converter e salvar o arquivo: " + e.getMessage());
        }
    }


}
