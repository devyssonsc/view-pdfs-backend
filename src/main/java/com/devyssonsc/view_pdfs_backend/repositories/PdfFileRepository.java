package com.devyssonsc.view_pdfs_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devyssonsc.view_pdfs_backend.models.PdfFile;

@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, Long> {}
