package com.tfms.service;
 
import org.springframework.web.multipart.MultipartFile;


import com.tfms.model.TradeDocument;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
 
public interface TradeDocumentService {
    void saveDocument(TradeDocument document, MultipartFile file) throws IOException;
    List<TradeDocument> getAllDocuments();
    Optional<TradeDocument> getDocumentById(Long id);
    void updateDocument(TradeDocument document, MultipartFile file) throws IOException;
}