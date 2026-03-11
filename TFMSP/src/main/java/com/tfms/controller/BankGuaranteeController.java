package com.tfms.controller;

import com.tfms.model.BankGuarantee;
import com.tfms.service.BankGuaranteeService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guarantees")
public class BankGuaranteeController {

    private final BankGuaranteeService service;

    public BankGuaranteeController(BankGuaranteeService service) {
        this.service = service;
    }

    // Show request form
    @GetMapping("/request")
    public String requestForm(Model model) {
        model.addAttribute("guarantee", new BankGuarantee());
        return "bg/guarantee-request";
    }

    // Handle request submission
    @PostMapping("/request")
    public String requestGuarantee(@Valid @ModelAttribute("guarantee") BankGuarantee guarantee,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "bg/guarantee-request";
        }
        try {
            BankGuarantee saved = service.requestGuarantee(guarantee);
            redirectAttributes.addFlashAttribute("message",
                    "Guarantee requested successfully (ID: " + saved.getGuaranteeId() + ")");
            return "redirect:/guarantees/list";
        } catch (IllegalArgumentException e) {
            result.rejectValue(null, null, e.getMessage());
            return "bg/guarantee-request";
        }
    }

    // List all guarantees
    @GetMapping("/list")
    public String listGuarantees(Model model) {
        model.addAttribute("guarantees", service.getAllGuarantees());
        return "bg/guarantee-list";
    }

    // Issue guarantee (POST)
    @PostMapping("/issue/{id}")
    public String issueGuarantee(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean ok = service.issueGuarantee(id);
        if (ok) {
            redirectAttributes.addFlashAttribute("message", "Guarantee #" + id + " issued successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Unable to issue Guarantee #" + id + ". It may be expired or not in Pending state.");
        }
        return "redirect:/guarantees/list";
    }

    // Track guarantee status by id
    @GetMapping("/track/{id}")
    public String trackGuarantee(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        BankGuarantee g = service.trackGuaranteeStatus(id);
        if (g == null) {
            redirectAttributes.addFlashAttribute("message", "Guarantee not found: ID " + id);
            return "redirect:/guarantees/list";
        }
        model.addAttribute("guarantee", g);
        return "bg/guarantee-status";
    }
}
