package esprit.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobRestAPI {

    @Autowired
    private JobService jobService;

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @PostMapping("/send")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobService.saveAndSendJob(job);
        return ResponseEntity.ok(savedJob);
       // return "Job created and sent to RabbitMQ with ID: " + savedJob.getId();
    }

    // ================= HELLO =================
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, I'm the Job MS";
    }

    // ================= GET ALL =================
    @GetMapping("/list")
    public ResponseEntity<List<Job>> getAll() {
        return ResponseEntity.ok(jobService.getAll());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Job> getById(@PathVariable int id) {
        return ResponseEntity.ok(jobService.getById(id));
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        return new ResponseEntity<>(jobService.addJob(job), HttpStatus.CREATED);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable int id,
                                         @RequestBody Job job) {
        return ResponseEntity.ok(jobService.updateJob(id, job));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        return ResponseEntity.ok(jobService.deleteJob(id));
    }

    // ================= SEARCH =================

    // Search by title
    @GetMapping("/search/title")
    public ResponseEntity<List<Job>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(jobService.searchByTitle(title));
    }

    // Search by availability
    @GetMapping("/search/available")
    public ResponseEntity<List<Job>> searchByAvailability(@RequestParam boolean available) {
        return ResponseEntity.ok(jobService.searchByAvailability(available));
    }

    // Search by title AND availability
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchByTitleAndAvailability(
            @RequestParam String title,
            @RequestParam boolean available) {

        return ResponseEntity.ok(
                jobService.searchByTitleAndAvailability(title, available));
    }
}
