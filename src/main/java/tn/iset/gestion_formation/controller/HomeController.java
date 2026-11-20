
package tn.iset.gestion_formation.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String accueil() {
        return "redirect:/sessions";
    }

    @GetMapping("/profil")
    public String profil(
            @AuthenticationPrincipal UserDetails utilisateur,
            Model model) {

        model.addAttribute("username", utilisateur.getUsername());
        model.addAttribute("authorities", utilisateur.getAuthorities());

        return "profil";
    }
}
