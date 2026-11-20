package tn.iset.gestion_formation.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.iset.gestion_formation.security.entity.AppUser;
import tn.iset.gestion_formation.security.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur introuvable : " + username
                ));

        String[] roles = appUser.getRoles()
                .stream()
                .map(role -> role.getName())
                .toArray(String[]::new);

        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .disabled(!appUser.isEnabled())
                .build();
    }
}