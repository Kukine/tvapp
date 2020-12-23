package drumre.projekt.tvapp.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "movies")
@Data
public class Movie {

    @Id
    public String id;
    public Boolean adult;
    @JsonAlias("backdrop_path")
    public String backdropPath;
    @JsonAlias("genre_ids")
    public List<Long> genreIds;
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
}
