
package tn.iset.gestion_formation.repository;

import tn.iset.gestion_formation.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InscriptionRepository
        extends JpaRepository<Inscription, Long> {

    // Compter les inscriptions d'une session
    long countBySessionFormationId(Long sessionId);

    // Rechercher les inscriptions par date
    List<Inscription> findByDateInscription(LocalDate date);
}
