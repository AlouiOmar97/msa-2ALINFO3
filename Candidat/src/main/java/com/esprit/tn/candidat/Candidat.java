package com.esprit.tn.candidat;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom, prenom, email;

    @ElementCollection
    private Set<Integer> favoriteJobs = new HashSet<>();

    public Set<Integer> getFavoriteJobs() {
        return favoriteJobs;
    }

    public void setFavoriteJobs(Set<Integer> favoriteJobs) {
        this.favoriteJobs = favoriteJobs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public Candidat(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Candidat() {
        // REQUIRED by JPA
    }
}
