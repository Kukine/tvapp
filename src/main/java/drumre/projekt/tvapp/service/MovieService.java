package drumre.projekt.tvapp.service;

import drumre.projekt.tvapp.data.Movie;

import java.util.List;

public interface MovieService {

    Movie GetByID(String id);

    List<Movie> GetAll();

}
