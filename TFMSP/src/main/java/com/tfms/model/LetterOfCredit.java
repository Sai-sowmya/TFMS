package com.tfms.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


@Entity
public class LetterOfCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int icid;

	@NotBlank(message="Applicant Name cannot be blank")
	@Size(min = 2, max = 30, message = "Applicant Name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Applicant Name must contain only letters and spaces")
    private String applicantName;

	@NotBlank(message="Beneficiary Name cannot be blank")
	@Size(min = 2, max = 30, message = "Beneficiary Name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Beneficiary Name must contain only letters and spaces")
    private String beneficiaryName;
	
//	@Pattern(regexp = "^[+-]?\\d*\\.\\d+$")
    @DecimalMin(value = "1.0", message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be a future date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    

    

	public int getIcid() {
		return icid;
	}




	public void setIcid(int icid) {
		this.icid = icid;
	}




	public String getApplicantName() {
		return applicantName;
	}




	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}




	public String getBeneficiaryName() {
		return beneficiaryName;
	}




	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}




	public double getAmount() {
		return amount;
	}




	public void setAmount(double amount) {
		this.amount = amount;
	}




	public String getCurrency() {
		return currency;
	}




	public void setCurrency(String currency) {
		this.currency = currency;
	}




	public LocalDate getExpiryDate() {
		return expiryDate;
	}




	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}




	public Status getStatus() {
		return status;
	}




	public void setStatus(Status status) {
		this.status = status;
	}




	public enum Status {
        Open, Amended, Closed
    }

    // Getters and Setters
    
}
