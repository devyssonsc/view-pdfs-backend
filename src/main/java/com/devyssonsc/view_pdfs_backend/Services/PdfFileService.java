package com.devyssonsc.view_pdfs_backend.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.devyssonsc.view_pdfs_backend.models.PdfFile;
import com.devyssonsc.view_pdfs_backend.repositories.PdfFileRepository;

@Service
public class PdfFileService {
    
    @Autowired
    private PdfFileRepository pdfFileRepository;

    // public PdfFile findById(Long id)
    // {
    //     Optional<PdfFile> pdfFile = this.pdfFileRepository.findById(id);

    //     return pdfFile.orElseThrow(() -> new RuntimeException(
    //         "PDF não encontrado! Id: " + id + ", Tipo: " + PdfFile.class.getName()
    //     ));
    // }
    public PdfFile getPdf(Long id)
    {
        Optional<PdfFile> pdfFile = pdfFileRepository.findById(id);

        return pdfFile.orElseThrow(() -> new RuntimeException(
            "Arquivo não encontrado! Id:" + id + ", Tipo: " + PdfFile.class.getName()
        ));
    }

    @Transactional
    public PdfFile convertAndCreate(String htmlContent) throws IOException
    {
        //Instancia ITextRenderer e ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        //Converte uma string HTML em PDF
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        //Salva um PdfFile no banco
        PdfFile pdfFile = new PdfFile();
        pdfFile.setFileContent(outputStream.toByteArray());
        return pdfFileRepository.save(pdfFile);
    }

    // @Transactional
    // public PdfFile update(PdfFile pdfFile)
    // {
    //     PdfFile newPdfFile = findById(pdfFile.getId());

    //     newPdfFile.setFileName(pdfFile.getFileName());

    //     newPdfFile.setFileContent(pdfFile.getFileContent());

    //     return this.pdfFileRepository.save(newPdfFile);
    // }

}
