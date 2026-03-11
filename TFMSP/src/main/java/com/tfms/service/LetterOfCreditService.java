package com.tfms.service;


import org.springframework.stereotype.Service;

import com.tfms.model.LetterOfCredit;
import com.tfms.repository.LetterOfCreditRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class LetterOfCreditService {

    private LetterOfCreditRepository lcRepository;
    

    public LetterOfCreditService(LetterOfCreditRepository lcRepository) {
		this.lcRepository = lcRepository;
	}
	public List<LetterOfCredit> getAll() {
        return lcRepository.findAll();
    }
    @Transactional
    public void save(LetterOfCredit lc) {
        lcRepository.save(lc);
    }

    public LetterOfCredit getById(int id) {
        return lcRepository.findById(id).orElse(null);
    }

    public void close(int id) {
        LetterOfCredit lc = getById(id);
        if (lc != null) {
            lc.setStatus(LetterOfCredit.Status.Closed);
            lcRepository.save(lc);
        }
    }
}