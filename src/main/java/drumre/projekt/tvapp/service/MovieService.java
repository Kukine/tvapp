package drumre.projekt.tvapp.service;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;

import java.util.List;

public interface MovieService {

    Movie GetByID(String id);

    List<BasicMovieDTO> GetBatch(int size);

    void likeMovie(Long userID, String movieID);

    List<BasicMovieDTO> findMoviesReccomendedForUser(Long userID);

    List<BasicMovieDTO> findMoviesByGenres(List<String> genres);
}
