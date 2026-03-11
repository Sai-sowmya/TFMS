package com.tfms.controller;

import com.tfms.model.Compliance;
import com.tfms.service.ComplianceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;

    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    // List all compliance reports
    @GetMapping
    public String listCompliance(Model model) {
        model.addAttribute("compliances", complianceService.getAllCompliances());
        return "compliance/compliance_list";
    }

    // Form to create a new compliance report
    @GetMapping("/add")
    public String addComplianceForm(Model model) {
        model.addAttribute("compliance", new Compliance());
        return "compliance/compliance_form";
    }

    // Save a compliance report (Generate report logic included)
    @PostMapping("/save")
    public String saveCompliance(@ModelAttribute("compliance") Compliance compliance,
                                 RedirectAttributes redirectAttributes) {
    	// Check if complianceId is present to determine if it's an update or new record
        // This check must happen BEFORE the data is saved.
        boolean isUpdate = (compliance.getComplianceId() != null);
        try {
        	 String transactionRef = compliance.getTransactionReference();
        	  // Prepend "TFMS" to the transaction reference before saving it to the database
            if (transactionRef != null && !transactionRef.startsWith("TFMS")) {
                compliance.setTransactionReference("TFMS_" + transactionRef);
            }
            // The save method from your repository
            complianceService.saveCompliance(compliance);

            // Set the success flash message based on the result of the initial check
            if (isUpdate) {
                redirectAttributes.addFlashAttribute("successMessage", "✅ Compliance record updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "✅ New compliance record added successfully!");
            }
        } catch (Exception e) {
            // If an error occurs, set an error flash message
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Failed to save the compliance record. Please try again.");
            // Log the exception for debugging
            e.printStackTrace();
        }

        // Redirect to the list page
        return "redirect:/compliance";
    }

    // Edit an existing compliance report
    @GetMapping("/edit/{id}")
    public String editCompliance(@PathVariable Long id, Model model) {
        model.addAttribute("compliance", complianceService.getComplianceById(id));
        return "compliance/compliance_form";
    }

    // Delete a compliance report
    @GetMapping("/delete/{id}")
    public String deleteCompliance(@PathVariable Long id) {
        complianceService.deleteCompliance(id);
        return "redirect:/compliance";
    }

    @GetMapping("/view/{id}")
    public String viewCompliance(@PathVariable Long id, Model model) {
        model.addAttribute("compliance", complianceService.getComplianceById(id));
        return "compliance/compliance_view";
    }


}
