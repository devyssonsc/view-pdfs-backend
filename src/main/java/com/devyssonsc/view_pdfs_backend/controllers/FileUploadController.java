package com.devyssonsc.view_pdfs_backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devyssonsc.view_pdfs_backend.Services.PdfFileService;
import com.devyssonsc.view_pdfs_backend.models.PdfFile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/api")
public class FileUploadController {
    
    @Autowired
    private PdfFileService pdfFileService;

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) {
        // Get the PDF file from the service
        PdfFile pdf = pdfFileService.getPdf(id);

        // Create a ByteArrayResource from the PDF file content
        ByteArrayResource resource = new ByteArrayResource(pdf.getFileContent());

        // Set the response headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename='arquivo.pdf'");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Return the ResponseEntity with the PDF file as the response body
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
    

    @PostMapping("/upload")
    public ResponseEntity<String> ConvertandCreate(@RequestBody String htmlContent)
    {
        try {
            //Chama o método no service para converter para PDF e salvar no banco
            pdfFileService.convertAndCreate(htmlContent);

            //Resposta da requisição
            return ResponseEntity.ok("Arquivo convertido e salvo com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao converter e salvar o arquivo: " + e.getMessage());
        }
    }


}
