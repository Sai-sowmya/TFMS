package com.tfms.model;
 
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
 
@Entity
public class TradeDocument {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
 
    @NotBlank(message = "Document Type is required")
    private String documentType;
 
    @NotBlank(message = "Reference Number is required")
    @Pattern(regexp = "^[A-Z0-9-]{5,20}$", message = "Reference Number must be 5–20 characters, uppercase letters, numbers, or hyphens")
    private String referenceNumber;
 
    @NotBlank(message = "Uploaded By is required")
    private String uploadedBy;
 
    private String fileName;
 
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
 
    @NotBlank(message = "Status is required")
    private String status;
 
	public Long getDocumentId() {
		return documentId;
	}
 
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
 
	public String getDocumentType() {
		return documentType;
	}
 
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
 
	public String getReferenceNumber() {
		return referenceNumber;
	}
 
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
 
	public String getUploadedBy() {
		return uploadedBy;
	}
 
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
 
	public String getFileName() {
		return fileName;
	}
 
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
 
	public Date getUploadDate() {
		return uploadDate;
	}
 
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
 
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
 
    // Getters and setters...
}