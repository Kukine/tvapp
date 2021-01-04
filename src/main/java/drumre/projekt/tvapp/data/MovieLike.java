package drumre.projekt.tvapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("likes")
@AllArgsConstructor
@NoArgsConstructor
public class MovieLike {
    public Long userID;
    public String movieID;
}
