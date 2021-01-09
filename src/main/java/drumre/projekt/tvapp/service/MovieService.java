package drumre.projekt.tvapp.service;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.LikedMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieService {

    Movie GetByID(String id);

    List<BasicMovieDTO> GetBatch(int size);

    List<BasicMovieDTO> getSearchBatch(int size, String search);

    boolean likeMovie(Long userID, String movieID);

    boolean dislikeMovie(Long userID, String movieID);

    boolean isLiked(Long userID, String movieID);

    List<BasicMovieDTO> findMoviesReccomendedForUser(Long userID);

    List<BasicMovieDTO> findMoviesByGenres(List<String> genres);

    List<LikedMovieDTO> findLikedMovies(Long userID);

    List<BasicMovieDTO> findPopularMovieByTime(LocalDateTime time);

}
