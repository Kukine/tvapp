package drumre.projekt.tvapp.service;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;

import java.util.List;

public interface MovieService {

    MovieWithDetailsDTO GetByID(String id);

    List<BasicMovieDTO> GetBatch(int size);

}
