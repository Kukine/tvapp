package drumre.projekt.tvapp.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ratings")
public class OMDBRating {

    private String movieID;
    private String imdbID;
    private String rated;
    private String director;
    private List<Rating> ratings;

}
@Data
@NoArgsConstructor
class Rating{
    private String source;
    private String value;
}