package drumre.projekt.tvapp.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nimbusds.oauth2.sdk.id.Actor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBRating {

    @JsonAlias("imdbID")
    private String ImdbID;
    @JsonAlias("Rated")
    private String Rated;
    @JsonAlias("Director")
    private String Director;
    @JsonAlias("Ratings")
    private List<Rating> Ratings;
    @JsonAlias("Actors")
    private String actors;
}
@Data
@NoArgsConstructor
class Rating{
    @JsonAlias("Source")
    private String Source;
    @JsonAlias("Value")
    private String Value;
}