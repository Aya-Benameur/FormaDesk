
package tn.iset.gestion_formation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.iset.gestion_formation.entity.Apprenant;
import tn.iset.gestion_formation.entity.Inscription;
import tn.iset.gestion_formation.entity.SessionFormation;

import tn.iset.gestion_formation.repository.ApprenantRepository;
import tn.iset.gestion_formation.repository.InscriptionRepository;
import tn.iset.gestion_formation.repository.SessionFormationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final ApprenantRepository apprenantRepository;
    private final SessionFormationRepository sessionFormationRepository;

    public InscriptionService(
            InscriptionRepository inscriptionRepository,
            ApprenantRepository apprenantRepository,
            SessionFormationRepository sessionFormationRepository) {

        this.inscriptionRepository = inscriptionRepository;
        this.apprenantRepository = apprenantRepository;
        this.sessionFormationRepository = sessionFormationRepository;
    }

    // Afficher toutes les inscriptions
    public List<Inscription> listerToutesLesInscriptions() {
        return inscriptionRepository.findAll();
    }

    // Enregistrer une inscription
    @Transactional
    public Inscription enregistrer(
            LocalDate dateInscription,
            Long apprenantId,
            Long sessionId) {

        Apprenant apprenant = apprenantRepository.findById(apprenantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Apprenant introuvable"));

        SessionFormation session = sessionFormationRepository.findById(sessionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Session introuvable"));

        Inscription inscription = new Inscription(
                dateInscription,
                apprenant,
                session
        );

        return inscriptionRepository.save(inscription);
    }

    // Compter les inscriptions d'une session
    public long nombreInscriptionsParSession(Long sessionId) {
        return inscriptionRepository.countBySessionFormationId(sessionId);
    }

    // Rechercher les inscriptions par date
    public List<Inscription> rechercherParDate(LocalDate date) {
        return inscriptionRepository.findByDateInscription(date);
    }
}
