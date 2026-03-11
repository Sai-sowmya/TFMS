package com.tfms.service;

import com.tfms.model.RiskAssessment;
import com.tfms.repository.RiskAssessmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {

    private final RiskAssessmentRepository repository;

    public RiskAssessmentService(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    /**
     * Simple method to calculate risk score.
     * Higher numbers = more risk.
     */
    public int calculateRiskScore(String riskType, String remarks) {
        int score = 0;

        // Step 1: Add score based on risk type
        if ("SANCTIONS".equalsIgnoreCase(riskType)) {
            score = 40;
        } else if ("HIGH_AMOUNT".equalsIgnoreCase(riskType)) {
            score = 30;
        } else if ("COUNTRY_RISK".equalsIgnoreCase(riskType)) {
            score = 20;
        } else if ("FRAUD_ALERT".equalsIgnoreCase(riskType)) {
            score = 35;
        } else if ("KYC_ISSUE".equalsIgnoreCase(riskType)) {
            score = 25;
        } else if ("PEP_INVOLVEMENT".equalsIgnoreCase(riskType)) {
            score = 30;
        } else if ("DOCUMENT_MISMATCH".equalsIgnoreCase(riskType)) {
            score = 15;
        } else {
            score = 10; // Default for unknown types
        }

        // Step 2: Modify score based on remarks text
        if (remarks != null) {
            String lowerRemarks = remarks.toLowerCase();

            if (lowerRemarks.contains("urgent")) {
                score += 5;
            }
            if (lowerRemarks.contains("late")) {
                score += 5;
            }
            if (lowerRemarks.contains("manual override")) {
                score -= 10;
            }
            if (lowerRemarks.contains("repeat offender")) {
                score += 15;
            }
            if (lowerRemarks.contains("new customer")) {
                score += 5;
            }
        }

        // Step 3: Make sure score stays between 1 and 100
        if (score < 1) {
            score = 1;
        }
        if (score > 100) {
            score = 100;
        }

        return score;
    }

    /**
     * This method will calculate risk score (if not given)
     * and save the record in database.
     */
    public RiskAssessment analyzeAndSave(RiskAssessment ra) {
        if (ra.getRiskScore() == null) { // If score is empty, calculate it
            ra.setRiskScore(calculateRiskScore(ra.getRiskType(), ra.getRemarks()));
        }
        return repository.save(ra);
    }

    // Get all records
    public List<RiskAssessment> findAll() {
        return repository.findAll();
    }

    // Get a record by ID
    public RiskAssessment findById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Delete record by ID
    public void deleteById(int id) {
        repository.deleteById(id);
    }
    public List<RiskAssessment> getAllAssessments() { // ✅ NEW METHOD
        return repository.findAll();
    }

    public RiskAssessment getAssessmentById(int id) {
        return repository.findById(id).orElse(null);
    }
    // 🔹 NEW CODE: Edit existing RiskAssessment
    public RiskAssessment updateRiskAssessment(int id, RiskAssessment updatedAssessment) {
        return repository.findById(id).map(existing -> {
            existing.setTransactionId(updatedAssessment.getTransactionId());
            existing.setRiskType(updatedAssessment.getRiskType());
            existing.setRiskScore(updatedAssessment.getRiskScore());
            existing.setRemarks(updatedAssessment.getRemarks());
            return repository.save(existing);
        }).orElse(null);
    }
}
