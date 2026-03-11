package com.tfms.controller;
 
import com.tfms.model.TradeDocument;

import com.tfms.service.TradeDocumentService;

import jakarta.validation.Valid;

import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
 
@Controller
@RequestMapping("/documents")
public class TradeDocumentController {
 

    private TradeDocumentService tradeDocumentService;
    
 
    public TradeDocumentController(TradeDocumentService tradeDocumentService) {
		this.tradeDocumentService = tradeDocumentService;
	}

	@GetMapping("/upload")
    public String uploadDocument(Model model) {
        model.addAttribute("document", new TradeDocument());
        return "trade_documents/upload";
    }
 
    @PostMapping("/upload")
    public String handleUpload(@Valid @ModelAttribute("document") TradeDocument document,
                               BindingResult result,
                               @RequestParam("file") MultipartFile file,
                               Model model, RedirectAttributes redirectAttributes ){
        if (result.hasErrors()) {
            return "trade_documents/upload";
        }
        try {
            tradeDocumentService.saveDocument(document, file);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Trade Document Uploaded successfully!");
            return "redirect:/documents/list";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file.");
            redirectAttributes.addFlashAttribute("successMessage", "❌ Failed to Upload the Document ");
            return "trade_documents/upload";
        }
    }
 
    @GetMapping("/list")
    public String viewDocuments(Model model) {
        model.addAttribute("documents", tradeDocumentService.getAllDocuments());
        return "trade_documents/list";
    }
 
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        Optional<TradeDocument> opt = tradeDocumentService.getDocumentById(id);
        if (opt.isPresent()) {
            TradeDocument doc = opt.get();
            Path path = Paths.get("uploads", doc.getFileName());
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
 
    @GetMapping("/edit/{id}")
    public String editDocumentForm(@PathVariable Long id, Model model) {
        Optional<TradeDocument> opt = tradeDocumentService.getDocumentById(id);
        if (opt.isPresent()) {
            model.addAttribute("document", opt.get());
            return "trade_documents/update";
        }
        return "redirect:/documents/list";
    }
 
    @PostMapping("/update")
    public String updateDocument(@Valid @ModelAttribute("document") TradeDocument document,
                                 BindingResult result,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "trade_documents/update";
        }
        try {
            tradeDocumentService.updateDocument(document, file);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Trade Document Updated successfully!");
            return "redirect:/documents/list";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update document.");
            redirectAttributes.addFlashAttribute("successMessage", "❌ Failed to Upload the Document ");
            return "trade_documents/update";
        }
    }
}