package com.tfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfms.model.BankGuarantee;

@Repository
public interface BankGuaranteeRepository extends JpaRepository<BankGuarantee, Integer> {
}
