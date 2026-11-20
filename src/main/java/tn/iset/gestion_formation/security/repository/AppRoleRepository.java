package tn.iset.gestion_formation.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iset.gestion_formation.security.entity.AppRole;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findByName(String name);
}