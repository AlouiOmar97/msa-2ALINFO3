package com.esprit.tn.candidat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/candidats")
class CandidatRestAPIController {
    @Autowired
    private CandidatService candidatService;
    private final DataSource dataSource;
    private String title = "Hello, I'm the candidate MicroService";

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        System.out.println(welcomeMessage);
        return welcomeMessage;
    }

    CandidatRestAPIController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/hello")
    public String hello() {
        System.out.println(title);
        return title;
    }

    @GetMapping("/db-info")
    public String getDbInfo() throws Exception {
        return dataSource.getConnection().getMetaData().getDatabaseProductName();
    }

    @GetMapping("/favorites")
    public List<JobDTO> getFavoriteJobs() {
        return candidatService.getFavoriteJobs();
    }

    @RequestMapping("/jobs")
    public List<Job> getAllJobs() {
        return candidatService.getJobs();
    }

    @RequestMapping("jobs/{id}")
    public Job getJobById(@PathVariable int id) {
        return candidatService.getJobById(id);
    }

    @GetMapping("/{id}/favorite-jobs")
    public List<Job> getFavoriteJobs(@PathVariable int id) {
        return candidatService.getFavoriteJobs(id);
    }

    @PostMapping("/{id}/favorite-jobs/{jobId}")
    public ResponseEntity<String> saveFavoriteJob(@PathVariable int id, @PathVariable int jobId) {
        Job job = candidatService.getJobById(jobId);
        if (job != null) {
            candidatService.saveFavoriteJob(id, jobId);
            return ResponseEntity.status(HttpStatus.OK).body("Job saved as favorite successfully.");

        } else {
            // Gérer le cas où le job n'existe pas
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job not found with ID: " + jobId);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Candidat>> getListCandid() {
        List<Candidat> candidats = candidatService.getAll();
        if (candidats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(candidats);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Candidat> getCandidatById(@PathVariable int id) {
        Candidat candidat = candidatService.getById(id);
        if (candidat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(candidat);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Candidat> createCandidat(@RequestBody Candidat candidat) {
        return new ResponseEntity<>(candidatService.addCandidat(candidat), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable("id") int id, @RequestBody Candidat candidat){
        return ResponseEntity.ok(candidatService.updateCandidat(id, candidat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidat(@PathVariable("id") int id){
        return new ResponseEntity<>(candidatService.deleteCandidat(id), HttpStatus.OK);
    }

}
