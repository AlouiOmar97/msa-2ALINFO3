package esprit.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobProducer jobProducer;

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    /** Sauvegarde d'un Job dans la base de données et envoi d’un DTO à RabbitMQ **/
//si save réussit mais l’envoi échoue, on aura un enregistrement sans message
    @Transactional
    public Job saveAndSendJob(Job jobEntity) {
        Job savedJob = jobRepository.save(jobEntity);
        log.info("Job sauvegardé en base : {}", savedJob.getTitle());
// Construire un DTO à envoyer
        JobDTO jobDTO = new JobDTO(savedJob.getId(), savedJob.getTitle(), savedJob.isAvailable());
// Envoi asynchrone via RabbitMQ
        jobProducer.sendJob(jobDTO);
        return savedJob;

    }

    // ================= CREATE =================
    public Job addJob(Job job) {
        return jobRepository.save(job);
    }

    // ================= READ =================
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    public Job getById(int id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }

    // ================= UPDATE =================
    public Job updateJob(int id, Job newJob) {

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        existingJob.setTitle(newJob.getTitle());
        existingJob.setDescription(newJob.getDescription());
        existingJob.setAvailable(newJob.isAvailable());

        return jobRepository.save(existingJob);
    }

    // ================= DELETE =================
    public String deleteJob(int id) {

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with id: " + id);
        }

        jobRepository.deleteById(id);
        return "Job deleted successfully";
    }

    // ================= SEARCH =================

    // Search by title
    public List<Job> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    // Search by availability
    public List<Job> searchByAvailability(boolean available) {
        return jobRepository.findByAvailable(available);
    }

    // Search by title AND availability
    public List<Job> searchByTitleAndAvailability(String title, boolean available) {
        return jobRepository.findByTitleContainingIgnoreCaseAndAvailable(title, available);
    }
}
