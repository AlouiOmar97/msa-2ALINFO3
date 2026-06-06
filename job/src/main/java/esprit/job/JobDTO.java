package esprit.job;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobDTO {
    private  int id;
    private String service;
    //private boolean etat;
    private String title;
    private String description;
    private Boolean available;

    public JobDTO(int id, String title, boolean available) {
        this.id = id;
        this.title = title;
        this.available = available;
    }
}
