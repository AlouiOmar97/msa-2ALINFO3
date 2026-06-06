package com.esprit.tn.candidat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class CandidatService {
    @Autowired
    private CandidatRepository candidateRepository;
    @Autowired
    private JobClient jobServiceClient;
    private static final Logger log = LoggerFactory.getLogger(JobConsumer.class);
    // Simulons un cache en mémoire pour les jobs reçus
    private List<JobDTO> favoriteJobDTOS = new ArrayList<>();

    public List<Job> getJobs() {
        return jobServiceClient.getAllJobs();
    }

    public void receiveJobService(JobDTO jobDTO) {
        log.info("Traitement du jobDTO received : {}", jobDTO.getService());
        addJobToFavorites(jobDTO);
        sendNotificationToUser(jobDTO);
    }

    private void addJobToFavorites(JobDTO jobDTO) {
// Ajoutons le JobDTO à la liste en mémoire (cachée)
        favoriteJobDTOS.add(jobDTO);
        log.info("JobDTO ajouté aux favoris : {}", jobDTO.getTitle());
    }
    private void sendNotificationToUser(JobDTO jobDTO) {
// Simulation de l'envoi d'une notification
        log.info("Notification envoyée au candidat: Nouveau jobDTO - {}",
                jobDTO.getTitle());
    }

    // Simulez l'affichage des jobs favoris depuis le cache
    public List<JobDTO> getFavoriteJobs() {
        return favoriteJobDTOS;
    }
    public List<Candidat> getAllCandidats() {
        return candidateRepository.findAll();
    }

    public Job getJobById(int id) {
        return jobServiceClient.getJobById(id);
    }
    public List<Job> getFavoriteJobs(int candidateId) {
        Candidat candidate = candidateRepository.findById(candidateId).get();
        return candidate.getFavoriteJobs().stream()
                .map(jobServiceClient::getJobById)
                .collect(Collectors.toList());
    }

    public void saveFavoriteJob(int candidateId, int jobId) {
        Candidat candidate = candidateRepository.findById(candidateId).get();
        candidate.getFavoriteJobs().add(jobId);
        candidateRepository.save(candidate);
    }

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
