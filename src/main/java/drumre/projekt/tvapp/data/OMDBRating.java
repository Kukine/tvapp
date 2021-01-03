package drumre.projekt.tvapp.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ratings")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBRating {

    @JsonAlias("movie_id")
    private String movieID;
    @JsonAlias("imdbID")
    private String ImdbID;
    @JsonAlias("Rated")
    private String Rated;
    @JsonAlias("Director")
    private String Director;
    @JsonAlias("Ratings")
    private List<Rating> Ratings;
}
@Data
@NoArgsConstructor
class Rating{
    @JsonAlias("Source")
    private String Source;
    @JsonAlias("Value")
    private String Value;
}