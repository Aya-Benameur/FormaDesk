
package tn.iset.gestion_formation.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tn.iset.gestion_formation.service.ApprenantService;
import tn.iset.gestion_formation.service.InscriptionService;
import tn.iset.gestion_formation.service.SessionFormationService;

@Controller
@RequestMapping("/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final ApprenantService apprenantService;
    private final SessionFormationService sessionFormationService;

    public InscriptionController(
            InscriptionService inscriptionService,
            ApprenantService apprenantService,
            SessionFormationService sessionFormationService) {

        this.inscriptionService = inscriptionService;
        this.apprenantService = apprenantService;
        this.sessionFormationService = sessionFormationService;
    }

    // Afficher toutes les inscriptions
    @GetMapping
    public String listerToutes(Model model) {

        model.addAttribute(
                "inscriptions",
                inscriptionService.listerToutesLesInscriptions()
        );

        return "inscriptions";
    }

    // Afficher le formulaire d'inscription
    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {

        model.addAttribute(
                "apprenants",
                apprenantService.listerTousLesApprenants()
        );

        model.addAttribute(
                "sessions",
                sessionFormationService.listerToutesLesSessions()
        );

        model.addAttribute("dateDuJour", LocalDate.now());

        return "inscription-form";
    }

    // Enregistrer une inscription
    @PostMapping("/enregistrer")
    public String enregistrer(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateInscription,

            @RequestParam Long apprenantId,
            @RequestParam Long sessionId,
            RedirectAttributes redirectAttributes) {

        inscriptionService.enregistrer(
                dateInscription,
                apprenantId,
                sessionId
        );

        redirectAttributes.addFlashAttribute(
                "message",
                "Inscription enregistrée avec succès."
        );

        return "redirect:/inscriptions";
    }

    // Rechercher les inscriptions par date
    @GetMapping("/rechercher")
    public String rechercherParDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Model model) {

        model.addAttribute(
                "inscriptions",
                inscriptionService.rechercherParDate(date)
        );

        model.addAttribute("dateRecherchee", date);

        return "inscriptions";
    }
}
