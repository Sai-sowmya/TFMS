package com.tfms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfms.model.Compliance;
import com.tfms.repository.ComplianceRepository;

@Service
public class ComplianceService {

    private final ComplianceRepository complianceRepository;

    public ComplianceService(ComplianceRepository complianceRepository) {
        this.complianceRepository = complianceRepository;
    }

  
    public List<Compliance> getAllCompliances() {
        return complianceRepository.findAll();
    }

 
    public Compliance getComplianceById(Long id) {
        return complianceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compliance not found with ID: " + id));
    }

    // Generate compliance report (creates or updates)
//    public Compliance generateComplianceReport(String transactionReference, String remarks) {
//        Compliance compliance = new Compliance();
//        compliance.setTransactionReference(transactionReference);
//        compliance.setRemarks(remarks);
//        compliance.setComplianceStatus("Generated");
//        compliance.setReportDate(LocalDate.now());
//        return complianceRepository.save(compliance);
//    }

    // Save new or updated compliance (no duplicates)
    public Compliance saveCompliance(Compliance compliance) {
        // If ID is present → update instead of creating a duplicate
        if (compliance.getComplianceId() != null) {
            Compliance existing = getComplianceById(compliance.getComplianceId());
            existing.setTransactionReference(compliance.getTransactionReference());
            existing.setComplianceStatus(compliance.getComplianceStatus());
            existing.setRemarks(compliance.getRemarks());
            existing.setReportDate(compliance.getReportDate());
            return complianceRepository.save(existing);
        } else {
            return complianceRepository.save(compliance);
        }
    }



    // Delete compliance
    public void deleteCompliance(Long id) {
        complianceRepository.deleteById(id);
    }
}
