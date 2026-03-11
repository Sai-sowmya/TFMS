package com.tfms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfms.model.LetterOfCredit;

@Repository
public interface LetterOfCreditRepository extends JpaRepository<LetterOfCredit, Integer> {
}