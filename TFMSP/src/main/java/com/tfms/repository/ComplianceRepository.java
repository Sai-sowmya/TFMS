package com.tfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.tfms.model.Compliance;

public interface ComplianceRepository extends JpaRepository<Compliance, Long> {
}
