
package tn.iset.gestion_formation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tn.iset.gestion_formation.entity.Formateur;
import tn.iset.gestion_formation.service.FormateurService;

@Controller
@RequestMapping("/formateurs")
public class FormateurController {

    private final FormateurService formateurService;

    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    @GetMapping
    public String listerTous(
            @RequestParam(required = false) String erreur,
            Model model) {

        model.addAttribute(
                "formateurs",
                formateurService.listerTousLesFormateurs()
        );

        if (erreur != null) {
            model.addAttribute("erreur", erreur);
        }

        return "formateurs";
    }

    @GetMapping("/rechercher")
    public String rechercherParFonction(
            @RequestParam(required = false, defaultValue = "") String fonction,
            Model model) {

        model.addAttribute(
                "formateurs",
                formateurService.rechercherParFonction(fonction)
        );
        model.addAttribute("fonctionRecherchee", fonction);

        return "formateurs";
    }

    @GetMapping("/par-formation")
    public String rechercherParFormation(
            @RequestParam(required = false, defaultValue = "") String formation,
            Model model) {

        model.addAttribute(
                "formateurs",
                formateurService.rechercherParFormation(formation)
        );
        model.addAttribute("formationRecherchee", formation);

        return "formateurs";
    }

    @GetMapping("/nouveau")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("formateur", new Formateur());
        model.addAttribute("titreFormulaire", "Ajouter un formateur");
        return "formateur-form";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "formateur",
                formateurService.trouverParId(id)
        );
        model.addAttribute("titreFormulaire", "Modifier le formateur");
        return "formateur-form";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(
            @ModelAttribute("formateur") Formateur formateur,
            RedirectAttributes redirectAttributes) {

        if (formateur.getId() == null) {
            formateurService.ajouter(formateur);
            redirectAttributes.addFlashAttribute(
                    "message", "Formateur ajouté avec succès."
            );
        } else {
            formateurService.modifier(formateur.getId(), formateur);
            redirectAttributes.addFlashAttribute(
                    "message", "Formateur modifié avec succès."
            );
        }

        return "redirect:/formateurs";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimer(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            formateurService.supprimer(id);
            redirectAttributes.addFlashAttribute(
                    "message", "Formateur supprimé avec succès."
            );
        } catch (IllegalStateException e) {
            redirectAttributes.addAttribute("erreur", e.getMessage());
        }

        return "redirect:/formateurs";
    }
}
