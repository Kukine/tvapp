package drumre.projekt.tvapp.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("movieDetails")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDetails {

    private Long budget;
    private String homepage;
    @JsonAlias("movie_id")
    private String movieId;
    @JsonAlias("imdb_id")
    private String imdbId;
    @JsonAlias("production_companies")
    private List<ProductionCompanies> productionCompanies;
    private Long runtime;
    private String status;
    private String tagline;

}

@Data
@NoArgsConstructor
class ProductionCompanies{
    private Long id;
    @JsonAlias("logo_path")
    private String LogoPath;
    private String name;
    @JsonAlias("origin_country")
    private String originCountry;
}




