package com.tfms.controller;

import com.tfms.model.RiskAssessment;

import com.tfms.service.RiskAssessmentService;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/risk")
public class RiskAssessmentController {

    private final RiskAssessmentService service;

    public RiskAssessmentController(RiskAssessmentService service) {
        this.service = service;
    }

    // Show all risk assessments
    @GetMapping("/list")
    public String listAll(Model model) {
        model.addAttribute("risks", service.findAll());
        return "risk/risk-list"; // Thymeleaf page
    }

    // Show form to add
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("riskAssessment", new RiskAssessment());
        return "risk/risk-form";
    }

    // Handle form submit
    @PostMapping("/save")
    public String save(@ModelAttribute RiskAssessment riskAssessment, RedirectAttributes redirectAttributes) {
        service.analyzeAndSave(riskAssessment);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Risk Assessment Saved successfully!");
        return "redirect:/risk/list";
    }

    // Delete record
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/risk/list";
    }

    // 🔹 NEW CODE: Edit functionality
    @GetMapping("/edit/{id}")
    public String editRiskAssessment(@PathVariable int id, Model model) {
        Optional<RiskAssessment> existing = service.getAllAssessments().stream()
                .filter(r -> r.getId() == id)
                .findFirst();
        if (existing.isPresent()) {
            model.addAttribute("riskAssessment", existing.get());
            model.addAttribute("assessments", service.getAllAssessments());
            return "risk/risk-form";
        }
        return "redirect:/risk/new";
    }

    // 🔹 NEW CODE: Save after edit
    @PostMapping("/update/{id}")
    public String updateRiskAssessment(@PathVariable int id, @ModelAttribute RiskAssessment riskAssessment,RedirectAttributes redirectAttributes) {
        service.updateRiskAssessment(id, riskAssessment);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Risk Assessment Updated successfully!");

        return "redirect:/risk/list";
    }


}
