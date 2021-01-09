package drumre.projekt.tvapp.controller;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.LikedMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.controller.dto.MoviesByGenreRequest;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.security.CurrentUser;
import drumre.projekt.tvapp.security.UserPrincipal;
import drumre.projekt.tvapp.service.MovieService;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/top")
    public List<BasicMovieDTO> getAllMovies(@RequestParam(required = false) Integer size,
                                            @RequestParam(required = false) String search){
        if (search != null && !search.isBlank()) {
            return movieService.getSearchBatch(size, search);
        }

        return this.movieService.GetBatch(size);
    }

    @GetMapping("/{movieID}")
    public Movie get(@PathVariable(name = "movieID") String movieID){
        return this.movieService.GetByID(movieID);
    }

    @GetMapping("/liked")
    public List<LikedMovieDTO> getLikedMovies(@CurrentUser UserPrincipal userPrincipal){
        return this.movieService.findLikedMovies(userPrincipal.getId()).stream().sorted(
            Comparator.comparing(LikedMovieDTO::getLikeTime).reversed()).collect(Collectors.toList());
    }

    @PostMapping("/{movieID}/like")
    public boolean likeMovie(@CurrentUser UserPrincipal userPrincipal, @PathVariable String movieID){
        return this.movieService.likeMovie(userPrincipal.getId(), movieID);
    }

    @PostMapping("/{movieID}/dislike")
    public boolean dislikeMovie(@CurrentUser UserPrincipal userPrincipal, @PathVariable String movieID){
        return this.movieService.dislikeMovie(userPrincipal.getId(), movieID);
    }

    @GetMapping("/{movieID}/liked")
    public boolean checkLikedMovie(@CurrentUser UserPrincipal userPrincipal, @PathVariable String movieID){
        return this.movieService.isLiked(userPrincipal.getId(), movieID);
    }

    @GetMapping("/recommendation/me")
    public List<BasicMovieDTO> getRecommendationsForMovie(@CurrentUser UserPrincipal userPrincipal){
        return this.movieService.findMoviesReccomendedForUser(userPrincipal.getId());
    }

    @PostMapping("/genres")
    public List<BasicMovieDTO> getMoviesByGenres(@RequestBody MoviesByGenreRequest req){
        return this.movieService.findMoviesByGenres(req.getGenres()).stream().limit(30).collect(Collectors.toList());
    }

    @GetMapping("/popular/day")
    public List<BasicMovieDTO> getDailyPopular(){
        return  this.movieService.findPopularMovieByTime(LocalDateTime.now().minusDays(1));
    }

    @GetMapping("/popular/weekly")
    public List<BasicMovieDTO> getWeeklyPopular(){
        return  this.movieService.findPopularMovieByTime(LocalDateTime.now().minusDays(7));
    }

    @GetMapping("/popular/monthly")
    public List<BasicMovieDTO> getMonthlyPopular(){
        return  this.movieService.findPopularMovieByTime(LocalDateTime.now().minusDays(30));
    }

    @GetMapping("/popular/alltime")
    public List<BasicMovieDTO> getAllTimePopular(){
        return  this.movieService.findPopularMovieByTime(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
    }
}
