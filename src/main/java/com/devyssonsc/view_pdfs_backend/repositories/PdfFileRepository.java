package com.devyssonsc.view_pdfs_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devyssonsc.view_pdfs_backend.models.PdfFile;

public interface PdfFileRepository extends JpaRepository<PdfFile, Long> {}
