package com.esprit.tn.candidat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/candidats")
class CandidatRestAPIController {
    @Autowired
    private CandidatService candidatService;
    private final DataSource dataSource;
    private String title = "Hello, I'm the candidate MicroService";

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
