
package tn.iset.gestion_formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iset.gestion_formation.entity.Apprenant;

public interface ApprenantRepository
        extends JpaRepository<Apprenant, Long> {
}
