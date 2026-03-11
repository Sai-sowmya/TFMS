package com.tfms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotBlank(message = "Risk Type is required")
    private String riskType;

    @NotNull(message = "Risk Score is required")
    @Min(value = 1, message = "Minimum score is 1")
    @Max(value = 100, message = "Maximum score is 100")
    private Integer riskScore;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
