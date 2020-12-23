package drumre.projekt.tvapp.service.impl;

import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie GetByID(String id) {
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("movie/repo: Movie not found"));
    }

    @Override
    public List<Movie> GetAll() {
        return movieRepository.findAll();
    }
}
