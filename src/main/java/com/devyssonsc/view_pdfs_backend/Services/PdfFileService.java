package com.devyssonsc.view_pdfs_backend.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyssonsc.view_pdfs_backend.models.PdfFile;
import com.devyssonsc.view_pdfs_backend.repositories.PdfFileRepository;

@Service
public class PdfFileService {
    
    @Autowired
    private PdfFileRepository pdfFileRepository;

    public PdfFile findById(Long id)
    {
        Optional<PdfFile> pdfFile = this.pdfFileRepository.findById(id);

        return pdfFile.orElseThrow(() -> new RuntimeException(
            "PDF não encontrado! Id: " + id + ", Tipo: " + PdfFile.class.getName()
        ));
    }

    @Transactional
    public PdfFile create(PdfFile pdfFile)
    {
        pdfFile.setId(null);

        pdfFile = pdfFileRepository.save(pdfFile);

        return pdfFile;
    }

    @Transactional
    public PdfFile update(PdfFile pdfFile)
    {
        PdfFile newPdfFile = findById(pdfFile.getId());

        newPdfFile.setFileName(pdfFile.getFileName());

        newPdfFile.setFileContent(pdfFile.getFileContent());

        return this.pdfFileRepository.save(newPdfFile);
    }

}
