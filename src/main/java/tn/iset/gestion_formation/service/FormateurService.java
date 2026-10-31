
package tn.iset.gestion_formation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.iset.gestion_formation.entity.Formateur;
import tn.iset.gestion_formation.repository.FormateurRepository;

import java.util.List;

@Service
public class FormateurService {

    private final FormateurRepository formateurRepository;

    public FormateurService(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
    }

    public List<Formateur> rechercherParFonction(String fonction) {
        if (fonction == null || fonction.isBlank()) {
            return formateurRepository.findAll();
        }
        return formateurRepository.findByFonctionContainingIgnoreCase(fonction);
    }

    public List<Formateur> rechercherParFormation(String formation) {
        if (formation == null || formation.isBlank()) {
            return formateurRepository.findAll();
        }
        return formateurRepository
                .findDistinctBySessionsDescriptionContainingIgnoreCase(formation);
    }

    public List<Formateur> listerTousLesFormateurs() {
        return formateurRepository.findAll();
    }

    public Formateur trouverParId(Long id) {
        return formateurRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Formateur introuvable : " + id));
    }

    public Formateur ajouter(Formateur formateur) {
        return formateurRepository.save(formateur);
    }

    @Transactional
    public Formateur modifier(Long id, Formateur donnees) {
        Formateur existant = trouverParId(id);

        // Champs hérités de Personne
        existant.setCin(donnees.getCin());
        existant.setPrenom(donnees.getPrenom());
        existant.setNom(donnees.getNom());
        existant.setDateNaissance(donnees.getDateNaissance());
        existant.setEmail(donnees.getEmail());

        // Champs propres à Formateur
        existant.setGrade(donnees.getGrade());
        existant.setFonction(donnees.getFonction());
        existant.setDatePremiereFormation(
                donnees.getDatePremiereFormation()
        );

        return formateurRepository.save(existant);
    }

    @Transactional
    public void supprimer(Long id) {
        Formateur formateur = trouverParId(id);

        // Empêche de supprimer un formateur encore associé à des sessions.
        if (formateur.getSessions() != null
                && !formateur.getSessions().isEmpty()) {
            throw new IllegalStateException(
                    "Ce formateur est associé à une ou plusieurs sessions. "
                    + "Modifiez d'abord ces sessions."
            );
        }

        formateurRepository.delete(formateur);
    }
}
