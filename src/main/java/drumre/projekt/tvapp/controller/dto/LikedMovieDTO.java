package drumre.projekt.tvapp.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

public class LikedMovieDTO {

    public String id;
    public Boolean adult;
    public String backdropPath;
    public List<String> genres;
    public String originalLanguage;
    public String originalTitle;
    public String title;
    public Date releaseDate;
    public Long voteCount;
    public String posterPath;
    public Double popularity;
    public Double voteAverage;
    public LocalDateTime likeTime;

}
