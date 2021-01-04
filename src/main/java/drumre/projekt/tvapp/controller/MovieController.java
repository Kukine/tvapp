package drumre.projekt.tvapp.controller;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/top")
    public List<BasicMovieDTO> getAllMovies(@RequestParam(required = false) Integer size){
        if (size == null){
            size = 20;
        }
        return movieService.GetBatch(size);
    }

    @GetMapping("/{movieID}")
    public MovieWithDetailsDTO get(@PathVariable(name = "movieID") String movieID){
        return movieService.GetByID(movieID);
    }

}
