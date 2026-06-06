package esprit.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    // 🔎 Search by title (contains, ignore case)
    List<Job> findByTitleContainingIgnoreCase(String title);

    // 🔎 Search by availability
    List<Job> findByAvailable(boolean available);

    // 🔎 Search by title AND availability
    List<Job> findByTitleContainingIgnoreCaseAndAvailable(String title, boolean available);
}
