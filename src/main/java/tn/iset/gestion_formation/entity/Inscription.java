
package tn.iset.gestion_formation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.time.LocalDate;

@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateInscription;

    @ManyToOne
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;

    @ManyToOne
    @JoinColumn(name = "session_formation_id")
    private SessionFormation sessionFormation;

    public Inscription() {
    }

    public Inscription(LocalDate dateInscription, Apprenant apprenant,
                       SessionFormation sessionFormation) {
        this.dateInscription = dateInscription;
        this.apprenant = apprenant;
        this.sessionFormation = sessionFormation;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Apprenant getApprenant() {
        return apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public SessionFormation getSessionFormation() {
        return sessionFormation;
    }

    public void setSessionFormation(SessionFormation sessionFormation) {
        this.sessionFormation = sessionFormation;
    }
}
