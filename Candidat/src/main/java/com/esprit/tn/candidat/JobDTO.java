package com.esprit.tn.candidat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private  int id;
    private String service;
    //private boolean etat;
    private String title;
    private String description;
    private Boolean available;
}
