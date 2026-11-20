
package tn.iset.gestion_formation.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // Pages publiques et ressources statiques
                .requestMatchers(
                    "/",
                    "/login",
                    "/403",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/h2-console/**"
                ).permitAll()

                // Pages de création et modification : ADMIN uniquement
                .requestMatchers(
                    "/sessions/nouveau",
                    "/formateurs/nouveau",
                    "/formateurs/modifier/**",
                    "/apprenants/nouveau",
                    "/inscriptions/nouveau"
                ).hasRole("ADMIN")

                // Enregistrement : ADMIN uniquement
                .requestMatchers(
                    HttpMethod.POST,
                    "/sessions/enregistrer",
                    "/formateurs/enregistrer",
                    "/apprenants/enregistrer",
                    "/inscriptions/enregistrer",
                    "/formateurs/supprimer/**"
                ).hasRole("ADMIN")

                // Toutes les autres pages nécessitent une connexion
                .anyRequest().authenticated()
            )

            // Formulaire de connexion
            .formLogin(Customizer.withDefaults())

            // Authentification HTTP Basic
            .httpBasic(Customizer.withDefaults())

            // Déconnexion
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            // Gestion personnalisée des erreurs 403
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403")
            )

            // Protection de la console H2
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )

            // Autoriser les frames de la console H2
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}
