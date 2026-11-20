package tn.iset.gestion_formation.security.config;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.iset.gestion_formation.security.entity.AppRole;
import tn.iset.gestion_formation.security.entity.AppUser;
import tn.iset.gestion_formation.security.repository.AppRoleRepository;
import tn.iset.gestion_formation.security.repository.AppUserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppRoleRepository roleRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            AppRoleRepository roleRepository,
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Création des rôles
        AppRole userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new AppRole("USER")));

        AppRole adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new AppRole("ADMIN")));

        // Lecture des variables d'environnement
        String adminUsername = System.getenv("FORMA_ADMIN_USERNAME");
        String adminPassword = System.getenv("FORMA_ADMIN_PASSWORD");

        String userUsername = System.getenv("FORMA_USER_USERNAME");
        String userPassword = System.getenv("FORMA_USER_PASSWORD");

        // Vérification des variables
        if (adminUsername == null || adminPassword == null
                || userUsername == null || userPassword == null) {
            throw new IllegalStateException(
                    "Les variables d'environnement des comptes sont manquantes."
            );
        }

        // Création du compte ADMIN
        createUserIfMissing(adminUsername, adminPassword, adminRole);

        // Création du compte USER
        createUserIfMissing(userUsername, userPassword, userRole);

        System.out.println("Initialisation des comptes terminée.");
    }

    private void createUserIfMissing(
            String username,
            String password,
            AppRole role) {

        if (userRepository.existsByUsername(username)) {
            System.out.println("Compte déjà existant : " + username);
            return;
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        HashSet<AppRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        System.out.println("Compte créé : " + username);
    }
}