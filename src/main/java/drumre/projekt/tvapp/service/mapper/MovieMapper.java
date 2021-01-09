package drumre.projekt.tvapp.service.mapper;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.LikedMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.OMDBRating;
import drumre.projekt.tvapp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    @Autowired
    RatingRepository ratingRepository;

    public BasicMovieDTO movieToDTO(Movie movie){
        BasicMovieDTO dto = new BasicMovieDTO();
        dto.setId(movie.getId());
        dto.setAdult(movie.getAdult());
        dto.setBackdropPath(movie.getBackdropPath());
        dto.setGenres(movie.getGenres());
        dto.setOriginalLanguage(movie.getOriginalLanguage());
        dto.setOriginalTitle(movie.getOriginalTitle());
        dto.setTitle(movie.getTitle());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPopularity(movie.getPopularity());
        dto.setVoteAverage(movie.getVoteAverage());
        dto.setPosterPath(movie.getPosterPath());
        return dto;
    }

    public List<BasicMovieDTO> batchMovieToDTO(List<Movie> movies){
        List<BasicMovieDTO> dtos = new ArrayList<>();
        movies.forEach(x -> dtos.add(movieToDTO(x)));
        return dtos;
    }

    public LikedMovieDTO movieToLikedMovieDTO(Movie movie){
        LikedMovieDTO dto = new LikedMovieDTO();
        dto.setId(movie.getId());
        dto.setAdult(movie.getAdult());
        dto.setBackdropPath(movie.getBackdropPath());
        dto.setGenres(movie.getGenres());
        dto.setOriginalLanguage(movie.getOriginalLanguage());
        dto.setOriginalTitle(movie.getOriginalTitle());
        dto.setTitle(movie.getTitle());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPopularity(movie.getPopularity());
        dto.setVoteAverage(movie.getVoteAverage());
        dto.setPosterPath(movie.getPosterPath());
        return dto;
    }

    public List<LikedMovieDTO> batchMovieToLikedMovieDTO(List<Movie> movies){
        return movies.stream().map(this::movieToLikedMovieDTO).collect(Collectors.toList());
    }

    public MovieWithDetailsDTO movieWithDetailsDTO(Movie movie){
        return new MovieWithDetailsDTO(movie);
    }

}
