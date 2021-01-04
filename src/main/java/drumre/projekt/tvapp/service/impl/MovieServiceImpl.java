package drumre.projekt.tvapp.service.impl;

import com.mongodb.client.MongoClient;
import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.MovieLike;
import drumre.projekt.tvapp.repository.LikeRepository;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.service.MovieService;
import drumre.projekt.tvapp.service.mapper.MovieMapper;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    MovieMapper movieMapper;

    LikeRepository likeRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, LikeRepository likeRepository){
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.likeRepository = likeRepository;
    }

    @Override
    public Movie GetByID(String id) {
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("movie/repo: Movie not found"));
    }

    @Override
    public List<BasicMovieDTO> GetBatch(int size) {
        List<Movie> movies = movieRepository.findAll(Sort.by("voteAverage").descending()).stream().limit(size).collect(Collectors.toList());
        return movieMapper.batchMovieToDTO(movies);
    }

    @Override
    public void likeMovie(Long userID, String movieID) {
        MovieLike like = new MovieLike(userID, movieID);
        List<MovieLike> all = this.likeRepository.findAll(Example.of(like));
        if(all.size() == 0){
            this.likeRepository.save(like);
        }
    }

    @Override
    public List<Movie> findSimiliarMovies(String movieID) {
        Movie movie = this.movieRepository.findById(movieID).orElseThrow(()-> new RuntimeException("Movie whose similiar movies you are tryig to fetch doesn't exist!"));
        Movie exampleMovie = new Movie();
        exampleMovie.setGenres(movie.getGenres());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("genres", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Movie> example = Example.of(exampleMovie, exampleMatcher);

        List<Movie> similar = this.movieRepository.findAll(example);
        return similar;
    }
}
