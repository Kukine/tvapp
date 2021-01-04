package drumre.projekt.tvapp.controller.dto;

import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.OMDBRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieWithDetailsDTO{
    public Movie movie;

    public MovieWithDetailsDTO(Movie movie){
        this.movie = movie;
    }
}
