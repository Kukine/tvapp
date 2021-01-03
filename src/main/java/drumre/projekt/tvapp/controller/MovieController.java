package drumre.projekt.tvapp.controller;

import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/all")
    public List<Movie> getAllMovies(@RequestParam(required = false) int size){
        return movieService.GetAll().subList(0,size);
    }
}
