package drumre.projekt.tvapp.service.impl;

import com.mongodb.client.MongoClient;
import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.service.MovieService;
import drumre.projekt.tvapp.service.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MongoClient mongoClient;

    @Override
    public MovieWithDetailsDTO GetByID(String id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("movie/repo: Movie not found"));
        return movieMapper.movieWithDetailsDTO(movie);
    }

    @Override
    public List<BasicMovieDTO> GetBatch(int size) {
        List<Movie> movies = movieRepository.findAll(Sort.by("voteAverage").descending()).stream().limit(size).collect(Collectors.toList());
        return movieMapper.batchMovieToDTO(movies);
    }
}
