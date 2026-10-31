
package tn.iset.gestion_formation.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tn.iset.gestion_formation.service.ApprenantService;

@Controller
@RequestMapping("/apprenants")
public class ApprenantController {

    private final ApprenantService apprenantService;

    public ApprenantController(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }

    // Afficher tous les apprenants
    @GetMapping
    public String listerTous(Model model) {

        model.addAttribute(
                "apprenants",
                apprenantService.listerTousLesApprenants()
        );

        return "apprenants";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {

        model.addAttribute("dateDuJour", LocalDate.now());

        return "apprenant-form";
    }

    // Enregistrer un nouvel apprenant
    @PostMapping("/enregistrer")
    public String enregistrer(
            @RequestParam String cin,
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateNaissance,
            @RequestParam String email,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate datePremiereInscription,
            @RequestParam String numero,
            RedirectAttributes redirectAttributes) {

        apprenantService.enregistrer(
                cin,
                prenom,
                nom,
                dateNaissance,
                email,
                datePremiereInscription,
                numero
        );

        redirectAttributes.addFlashAttribute(
                "message",
                "Apprenant ajouté avec succès."
        );

        return "redirect:/apprenants";
    }
}
