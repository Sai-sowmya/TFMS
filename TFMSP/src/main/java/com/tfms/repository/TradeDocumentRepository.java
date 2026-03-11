package com.tfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfms.model.TradeDocument;

public interface TradeDocumentRepository extends JpaRepository<TradeDocument, Long> {
}

