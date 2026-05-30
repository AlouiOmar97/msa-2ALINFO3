package com.esprit.tn.candidat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
class CandidatService {
    @Autowired
    private CandidatRepository candidateRepository;
    public List<Candidat> getAll(){
        return candidateRepository.findAll();
    }

    public Candidat getById(int id){
        return candidateRepository.findById(id).get();
    }
    public Candidat addCandidat(Candidat candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidat updateCandidat(int id, Candidat newCandidat) {
        if (candidateRepository.findById(id).isPresent()) {
            Candidat existingCandidat = candidateRepository.findById(id).get();
            existingCandidat.setNom(newCandidat.getNom());
            existingCandidat.setPrenom(newCandidat.getPrenom());
            existingCandidat.setEmail(newCandidat.getEmail());
            return candidateRepository.save(existingCandidat);
        } else
            return null;
    }
    public String deleteCandidat(int id) {
        if (candidateRepository.findById(id).isPresent()) {
            candidateRepository.deleteById(id);
            return "candidat supprimé";
        } else
            return "candidat non supprimé";
    }
}
