package drumre.projekt.tvapp.controller;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.security.CurrentUser;
import drumre.projekt.tvapp.security.UserPrincipal;
import drumre.projekt.tvapp.service.MovieService;
import org.apache.catalina.security.SecurityUtil;
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
        return this.movieService.GetBatch(size);
    }

    @GetMapping("/{movieID}")
    public Movie get(@PathVariable(name = "movieID") String movieID){
        return this.movieService.GetByID(movieID);
    }


    @PostMapping("/{movieID}/like")
    public void likeMovie(@CurrentUser UserPrincipal userPrincipal, @PathVariable String movieID){
        this.movieService.likeMovie(userPrincipal.getId(), movieID);
    }

    @GetMapping("/recommendation/{movieID}")
    public List<Movie> getRecommendationsForMovie(@PathVariable String movieID){
        return this.movieService.findSimiliarMovies(movieID);
    }

}
