package tn.iset.gestion_formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iset.gestion_formation.entity.Formateur;

import java.util.List;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {

    List<Formateur> findByFonctionContainingIgnoreCase(String fonction);

    List<Formateur> findDistinctBySessionsDescriptionContainingIgnoreCase(
            String description);
}