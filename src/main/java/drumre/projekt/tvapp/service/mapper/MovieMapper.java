package drumre.projekt.tvapp.service.mapper;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.OMDBRating;
import drumre.projekt.tvapp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        OMDBRating exampleRating = new OMDBRating();
        exampleRating.setMovieID(movie.getId());
        Example<OMDBRating> example = Example.of(exampleRating);
        List<OMDBRating> rating = ratingRepository.findAll(example);
        if (rating.size() == 1){
            dto.setRating(rating.get(0));
        }
        return dto;
    }

    public List<BasicMovieDTO> batchMovieToDTO(List<Movie> movies){
        List<BasicMovieDTO> dtos = new ArrayList<>();
        movies.forEach(x -> dtos.add(movieToDTO(x)));
        return dtos;
    }

    public MovieWithDetailsDTO movieWithDetailsDTO(Movie movie){
        MovieWithDetailsDTO movieWithDetailsDTO = new MovieWithDetailsDTO(movie);
        OMDBRating exampleRating = new OMDBRating();
        exampleRating.setMovieID(movie.getId());
        Example<OMDBRating> example = Example.of(exampleRating);
        List<OMDBRating> rating = ratingRepository.findAll(example);
        if (rating.size() == 1){
            movieWithDetailsDTO.setRating(rating.get(0));
        }
        return movieWithDetailsDTO;
    }

}
