
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

import tn.iset.gestion_formation.service.SessionFormationService;
import tn.iset.gestion_formation.service.FormateurService;

@Controller
@RequestMapping("/sessions")
public class SessionFormationController {

    private final SessionFormationService sessionFormationService;
    private final FormateurService formateurService;

    public SessionFormationController(
            SessionFormationService sessionFormationService,
            FormateurService formateurService) {

        this.sessionFormationService = sessionFormationService;
        this.formateurService = formateurService;
    }

    // Afficher toutes les sessions
    @GetMapping
    public String listerToutes(Model model) {
        model.addAttribute(
                "sessions",
                sessionFormationService.listerToutesLesSessions()
        );

        return "sessions";
    }

    // Afficher le formulaire de création
    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {

        model.addAttribute(
                "formateurs",
                formateurService.listerTousLesFormateurs()
        );

        model.addAttribute("dateDuJour", LocalDate.now());

        return "session-form";
    }

    // Enregistrer une nouvelle session
    @PostMapping("/enregistrer")
    public String enregistrer(
            @RequestParam String description,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateDebut,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFin,
            @RequestParam Long formateurId,
            RedirectAttributes redirectAttributes) {

        sessionFormationService.enregistrer(
                description,
                dateDebut,
                dateFin,
                formateurId
        );

        redirectAttributes.addFlashAttribute(
                "message",
                "Session ajoutée avec succès."
        );

        return "redirect:/sessions";
    }

    // Rechercher les sessions par date de début
    @GetMapping("/rechercher/date")
    public String rechercherParDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Model model) {

        model.addAttribute(
                "sessions",
                sessionFormationService.rechercherParDate(date)
        );

        model.addAttribute("dateRecherchee", date);

        return "sessions";
    }

    // Rechercher les sessions par description
    @GetMapping("/rechercher/description")
    public String rechercherParDescription(
            @RequestParam String description,
            Model model) {

        model.addAttribute(
                "sessions",
                sessionFormationService.rechercherParDescription(description)
        );

        model.addAttribute("descriptionRecherchee", description);

        return "sessions";
    }

    // Afficher le nombre d'apprenants d'une session
    @GetMapping("/nombre-apprenants")
    public String nombreApprenants(
            @RequestParam Long sessionId,
            Model model) {

        model.addAttribute(
                "nombreApprenants",
                sessionFormationService.nombreApprenantsParSession(sessionId)
        );

        model.addAttribute("sessionId", sessionId);

        return "nombre-apprenants";
    }

    // Rechercher les sessions suivies par un apprenant
    @GetMapping("/par-apprenant")
    public String rechercherParApprenant(
            @RequestParam Long apprenantId,
            Model model) {

        model.addAttribute(
                "sessions",
                sessionFormationService.rechercherParApprenant(apprenantId)
        );

        model.addAttribute("apprenantId", apprenantId);

        return "sessions";
    }
}
