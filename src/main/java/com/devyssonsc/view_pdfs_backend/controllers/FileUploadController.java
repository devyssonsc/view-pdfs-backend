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


@RestController
@RequestMapping("/api")
public class FileUploadController {
    
    @Autowired
    private PdfFileService pdfFileService;

    @GetMapping("/{id}")
    public ResponseEntity<PdfFile> getPdf(@PathVariable Long id) {
        PdfFile pdf = pdfFileService.getPdf(id);

        return ResponseEntity.ok().body(pdf);
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
