package com.esprit.tn.candidat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private  int id;
    private String service;
    private boolean etat;
}
