package tn.iset.gestion_formation.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iset.gestion_formation.security.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}