
package tn.iset.gestion_formation.repository;

import tn.iset.gestion_formation.entity.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionFormationRepository
        extends JpaRepository<SessionFormation, Long> {

    List<SessionFormation> findByDateDebut(LocalDate dateDebut);

    List<SessionFormation> findByDescriptionContainingIgnoreCase(String description);

    List<SessionFormation> findDistinctByInscriptionsApprenantId(Long apprenantId);
}
