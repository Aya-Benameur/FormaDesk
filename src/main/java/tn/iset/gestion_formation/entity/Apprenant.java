
package tn.iset.gestion_formation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Apprenant extends Personne {

    private LocalDate datePremiereInscription;
    private String numero;

    @OneToMany(mappedBy = "apprenant")
    private List<Inscription> inscriptions = new ArrayList<>();

    public Apprenant() {
    }

    public Apprenant(String cin, String prenom, String nom,
                     LocalDate dateNaissance, String email,
                     LocalDate datePremiereInscription, String numero) {
        super(cin, prenom, nom, dateNaissance, email);
        this.datePremiereInscription = datePremiereInscription;
        this.numero = numero;
    }

    public LocalDate getDatePremiereInscription() {
        return datePremiereInscription;
    }

    public void setDatePremiereInscription(LocalDate datePremiereInscription) {
        this.datePremiereInscription = datePremiereInscription;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
