
package com.tfms.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tfms.model.TradeDocument;
import com.tfms.repository.TradeDocumentRepository;

@Service

public class TradeDocumentServiceImpl implements TradeDocumentService {

	private static final String UPLOAD_DIR = "uploads";
	private TradeDocumentRepository tradeDocumentRepository;
	@Autowired
	public TradeDocumentServiceImpl(TradeDocumentRepository tradeDocumentRepository) {
		this.tradeDocumentRepository = tradeDocumentRepository;
	}

	public TradeDocumentServiceImpl() {

		File dir = new File(UPLOAD_DIR);

		if (!dir.exists())
			dir.mkdirs();

	}

	@Override

	public void saveDocument(TradeDocument document, MultipartFile file) throws IOException {

		String filename = file.getOriginalFilename();

		Path filepath = Paths.get(UPLOAD_DIR, filename);

		Files.write(filepath, file.getBytes());

		document.setFileName(filename);

		document.setUploadDate(new Date());

		tradeDocumentRepository.save(document);

	}

	@Override

	public List<TradeDocument> getAllDocuments() {

		return tradeDocumentRepository.findAll();

	}

	@Override

	public Optional<TradeDocument> getDocumentById(Long id) {

		return tradeDocumentRepository.findById(id);

	}

	@Override
	public void updateDocument(TradeDocument updatedDoc, MultipartFile file) throws IOException {
		Optional<TradeDocument> existingOpt = tradeDocumentRepository.findById(updatedDoc.getDocumentId());

		if (existingOpt.isPresent()) {
			TradeDocument existingDoc = existingOpt.get();

			// Update basic fields
			existingDoc.setDocumentType(updatedDoc.getDocumentType());
			existingDoc.setReferenceNumber(updatedDoc.getReferenceNumber());
			existingDoc.setUploadedBy(updatedDoc.getUploadedBy());
			existingDoc.setStatus(updatedDoc.getStatus());

			// Only update file if a new one is uploaded
			if (file != null && !file.isEmpty()) {
				String filename = file.getOriginalFilename();
				Path filepath = Paths.get(UPLOAD_DIR, filename);
				Files.write(filepath, file.getBytes());

				existingDoc.setFileName(filename);
				existingDoc.setUploadDate(new Date()); // update time only if file is replaced
			}

			tradeDocumentRepository.save(existingDoc);
		}
	}

}
