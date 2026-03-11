package com.tfms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfms.model.BankGuarantee;
import com.tfms.model.BankGuarantee.Status;
import com.tfms.repository.BankGuaranteeRepository;

@Service
public class BankGuaranteeService {

    private final BankGuaranteeRepository repository;

    public BankGuaranteeService(BankGuaranteeRepository repository) {
        this.repository = repository;
    }

    // Validate + request new guarantee
    public BankGuarantee requestGuarantee(BankGuarantee guarantee) {
        validateGuarantee(guarantee);
        guarantee.setStatus(Status.Pending);
        return repository.save(guarantee);
    }

    // Validate basic fields
    private void validateGuarantee(BankGuarantee g) {
        if (g == null) throw new IllegalArgumentException("Guarantee is null.");
        if (g.getApplicantName() == null || g.getApplicantName().trim().isEmpty())
            throw new IllegalArgumentException("Applicant name is required.");
        if (g.getBeneficiaryName() == null || g.getBeneficiaryName().trim().isEmpty())
            throw new IllegalArgumentException("Beneficiary name is required.");
        if (g.getGuaranteeAmount() <= 0)
            throw new IllegalArgumentException("Guarantee amount must be positive.");
        if (g.getCurrency() == null || g.getCurrency().trim().isEmpty())
            throw new IllegalArgumentException("Currency is required.");
        if (g.getValidityPeriod() == null)
            throw new IllegalArgumentException("Validity period is required.");
        if (!g.getValidityPeriod().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Validity period must be a future date.");
    }

    // Return all guarantees — also update expired statuses on-the-fly
    public List<BankGuarantee> getAllGuarantees() {
        List<BankGuarantee> list = repository.findAll();
        LocalDate today = LocalDate.now();
        for (BankGuarantee g : list) {
            if (g.getValidityPeriod() != null
                && g.getStatus() != Status.Expired
                && g.getValidityPeriod().isBefore(today)) {
                g.setStatus(Status.Expired);
                repository.save(g);
            }
        }
        return list;
    }

    // Issue guarantee: returns true if issued; false if not (not found / invalid state)
    public boolean issueGuarantee(int id) {
        Optional<BankGuarantee> optional = repository.findById(id);
        if (!optional.isPresent()) return false;

        BankGuarantee g = optional.get();

        // If already expired, don't issue
        if (g.getValidityPeriod() != null && g.getValidityPeriod().isBefore(LocalDate.now())) {
            g.setStatus(Status.Expired);
            repository.save(g);
            return false;
        }

        // Only pending guarantees can be issued
        if (g.getStatus() == Status.Pending) {
            g.setStatus(Status.Issued);
            repository.save(g);
            return true;
        }
        return false;
    }

    // Track by id
    public BankGuarantee trackGuaranteeStatus(int id) {
        return repository.findById(id).orElse(null);
    }
}
