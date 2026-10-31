
package tn.iset.gestion_formation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.iset.gestion_formation.entity.Formateur;
import tn.iset.gestion_formation.entity.SessionFormation;

import tn.iset.gestion_formation.repository.FormateurRepository;
import tn.iset.gestion_formation.repository.SessionFormationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessionFormationService {

    private final SessionFormationRepository sessionFormationRepository;
    private final FormateurRepository formateurRepository;

    public SessionFormationService(
            SessionFormationRepository sessionFormationRepository,
            FormateurRepository formateurRepository) {

        this.sessionFormationRepository = sessionFormationRepository;
        this.formateurRepository = formateurRepository;
    }

    // Afficher toutes les sessions
    public List<SessionFormation> listerToutesLesSessions() {
        return sessionFormationRepository.findAll();
    }

    // Enregistrer une nouvelle session
    @Transactional
    public SessionFormation enregistrer(
            String description,
            LocalDate dateDebut,
            LocalDate dateFin,
            Long formateurId) {

        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Formateur introuvable"));

        SessionFormation session = new SessionFormation(
                description,
                dateDebut,
                dateFin,
                formateur
        );

        return sessionFormationRepository.save(session);
    }

    // Rechercher les sessions par date
    public List<SessionFormation> rechercherParDate(LocalDate date) {
        return sessionFormationRepository.findByDateDebut(date);
    }

    // Rechercher les sessions par description
    public List<SessionFormation> rechercherParDescription(String description) {
        return sessionFormationRepository
                .findByDescriptionContainingIgnoreCase(description);
    }

    // Calculer le nombre d'apprenants d'une session
    @Transactional(readOnly = true)
    public long nombreApprenantsParSession(Long sessionId) {
        return sessionFormationRepository.findById(sessionId)
                .map(session -> (long) session.getInscriptions().size())
                .orElse(0L);
    }

    // Rechercher les sessions suivies par un apprenant
    public List<SessionFormation> rechercherParApprenant(Long apprenantId) {
        return sessionFormationRepository
                .findDistinctByInscriptionsApprenantId(apprenantId);
    }
}
