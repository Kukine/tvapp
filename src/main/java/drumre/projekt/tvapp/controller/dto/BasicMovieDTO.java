package drumre.projekt.tvapp.controller.dto;

import drumre.projekt.tvapp.data.OMDBRating;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BasicMovieDTO {

    public String id;
    public Boolean adult;
    public String backdropPath;
    public List<String> genres;
    public String originalLanguage;
    public String originalTitle;
    public String title;
    public Date releaseDate;
    public Long voteCount;
    public OMDBRating rating;
    public Double popularity;
    public Double voteAverage;

}
