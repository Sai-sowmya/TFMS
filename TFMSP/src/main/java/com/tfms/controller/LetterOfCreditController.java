package com.tfms.controller;

import com.tfms.model.LetterOfCredit;
import com.tfms.service.LetterOfCreditService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lc")
public class LetterOfCreditController {

    private final LetterOfCreditService lcService;

    @Autowired
    public LetterOfCreditController(LetterOfCreditService lcService) {
        this.lcService = lcService;
    }

    @GetMapping("/list")
    public String listLCs(Model model) {
        model.addAttribute("lcs", lcService.getAll());
        return "lc/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("lc", new LetterOfCredit());
        return "lc/create";
    }

    @PostMapping("/save")
    public String saveLC(@ModelAttribute("lc") @Valid LetterOfCredit lc,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "lc/create";
        }

        lc.setStatus(LetterOfCredit.Status.Open);
        lcService.save(lc);
        redirectAttributes.addFlashAttribute("successMessage", "✅ New Letter of Credit added successfully!");
        return "redirect:/lc/list";
    }

    @GetMapping("/amend/{id}")
    public String amendForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        LetterOfCredit lc = lcService.getById(id);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Letter of Credit not found.");
            return "redirect:/lc/list";
        }
        model.addAttribute("lc", lc);
        return "lc/amend";
    }

    @PostMapping("/update")
    public String updateLC(@ModelAttribute("lc") @Valid LetterOfCredit lc,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "lc/amend";
        }

        lc.setStatus(LetterOfCredit.Status.Amended);
        lcService.save(lc);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Letter of Credit updated successfully!");
        return "redirect:/lc/list";
    }

    @GetMapping("/close/{id}")
    public String closeLC(@PathVariable int id, RedirectAttributes redirectAttributes) {
        LetterOfCredit lc = lcService.getById(id);
        if (lc != null) {
            lc.setStatus(LetterOfCredit.Status.Closed);
            lcService.save(lc);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Letter of Credit closed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Letter of Credit not found.");
        }
        return "redirect:/lc/list";
    }
}
