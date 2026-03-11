package com.tfms.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class BankGuarantee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guaranteeId;

	@Size(min = 2, max = 30, message = "Applicant Name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Applicant Name must contain only letters and spaces")
	@NotBlank(message = "Applicant name is required")
	private String applicantName;

	@NotBlank(message="Beneficiary Name cannot be blank")
	@Size(min = 2, max = 30, message = "Beneficiary Name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Beneficiary Name must contain only letters and spaces")
	private String beneficiaryName;

	@DecimalMin(value = "1.0", message = "Guarantee amount must be positive")
	private double guaranteeAmount;

	@NotBlank(message = "Currency is required")
	private String currency;

	@NotNull(message = "Validity period is required")
	@Future(message = "Expiry date must be a future date")
	private LocalDate validityPeriod;


	public int getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(int guaranteeId) {
		this.guaranteeId = guaranteeId;
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

	public double getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(double guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(LocalDate validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Enumerated(EnumType.STRING)
	private Status status;

	public enum Status {
		Pending, Issued, Expired
	}


}