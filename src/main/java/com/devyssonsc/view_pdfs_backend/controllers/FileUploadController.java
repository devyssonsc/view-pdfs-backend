package com.devyssonsc.view_pdfs_backend.controllers;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.devyssonsc.view_pdfs_backend.Services.PdfFileService;
import com.devyssonsc.view_pdfs_backend.models.PdfFile;
import com.devyssonsc.view_pdfs_backend.repositories.PdfFileRepository;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    
    @Autowired
    private PdfFileService pdfFileService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file)
    {
        try {
            //Converter o arquivo Multipart para String(HTML)
            String htmlContent = new String(file.getBytes());

            //Converter HTML para PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            //Criar uma instancia de PdfFile e salvar no banco
            PdfFile pdfFile = new PdfFile();
            pdfFile.setFileName(file.getOriginalFilename().replace(".html", ".pdf"));
            pdfFile.setFileContent(outputStream.toByteArray());

            this.pdfFileService.create(pdfFile);

            return ResponseEntity.ok().body("PDF salvo com sucessso!");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
