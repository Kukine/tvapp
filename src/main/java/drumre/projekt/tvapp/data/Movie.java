package drumre.projekt.tvapp.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    public String id;
    public Boolean adult;
    @JsonAlias("backdrop_path")
    public String backdropPath;
    @JsonAlias("genre_ids")
    public List<String> genreIds;
    public List<String> genres;
    @JsonAlias("original_language")
    public String originalLanguage;
    @JsonAlias("original_title")
    public String originalTitle;
    public Double popularity;
    public String overview;
    @JsonAlias("release_date")
    public Date releaseDate;
    public String title;
    @JsonAlias("vote_average")
    public Double voteAverage;
    @JsonAlias("vote_count")
    public Long voteCount;
    @JsonAlias("poster_path")
    public String posterPath;
    public Boolean video;
    private Long budget;
    private String homepage;
    @JsonAlias("imdb_id")
    private String imdbId;
    @JsonAlias("production_companies")
    private List<ProductionCompanies> productionCompanies;
    private Long runtime;
    private String status;
    private String tagline;

    // Data we fetch from OMDB API
    private List<String> actors;
    private String director;
    private List<Rating> ratings;
    private String rated;

    public void MapFromMovieDetails(MovieDetails details){
        this.budget = details.getBudget();
        this.homepage = details.getHomepage();
        this.imdbId = details.getImdbId();
        this.productionCompanies = details.getProductionCompanies();
        this.runtime = details.getRuntime();
        this.status = details.getStatus();
        this.tagline = details.getTagline();
    }
}
