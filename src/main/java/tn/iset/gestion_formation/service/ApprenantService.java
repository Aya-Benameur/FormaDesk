
package tn.iset.gestion_formation.service;

import org.springframework.stereotype.Service;

import tn.iset.gestion_formation.entity.Apprenant;
import tn.iset.gestion_formation.repository.ApprenantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApprenantService {

    private final ApprenantRepository apprenantRepository;

    public ApprenantService(ApprenantRepository apprenantRepository) {
        this.apprenantRepository = apprenantRepository;
    }

    // Afficher tous les apprenants
    public List<Apprenant> listerTousLesApprenants() {
        return apprenantRepository.findAll();
    }

    // Enregistrer un nouvel apprenant
    public Apprenant enregistrer(
            String cin,
            String prenom,
            String nom,
            LocalDate dateNaissance,
            String email,
            LocalDate datePremiereInscription,
            String numero) {

        Apprenant apprenant = new Apprenant(
                cin,
                prenom,
                nom,
                dateNaissance,
                email,
                datePremiereInscription,
                numero
        );

        return apprenantRepository.save(apprenant);
    }
}
