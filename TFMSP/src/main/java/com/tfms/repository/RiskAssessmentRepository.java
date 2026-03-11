package com.tfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfms.model.RiskAssessment;

@Repository
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Integer> {
}