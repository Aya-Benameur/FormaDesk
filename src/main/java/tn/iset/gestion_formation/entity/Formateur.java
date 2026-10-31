
package tn.iset.gestion_formation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Formateur extends Personne {

    private String grade;
    private String fonction;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datePremiereFormation;

    @OneToMany(mappedBy = "formateur")
    @JsonIgnore
    private List<SessionFormation> sessions = new ArrayList<>();

    public Formateur() {
    }

    public Formateur(String cin, String prenom, String nom,
                     LocalDate dateNaissance, String email,
                     String grade, String fonction,
                     LocalDate datePremiereFormation) {
        super(cin, prenom, nom, dateNaissance, email);
        this.grade = grade;
        this.fonction = fonction;
        this.datePremiereFormation = datePremiereFormation;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public LocalDate getDatePremiereFormation() {
        return datePremiereFormation;
    }

    public void setDatePremiereFormation(LocalDate datePremiereFormation) {
        this.datePremiereFormation = datePremiereFormation;
    }

    public List<SessionFormation> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionFormation> sessions) {
        this.sessions = sessions;
    }
}
