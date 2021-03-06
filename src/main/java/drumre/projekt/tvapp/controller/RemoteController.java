package drumre.projekt.tvapp.controller;


import drumre.projekt.tvapp.remote.OMDBService;
import drumre.projekt.tvapp.remote.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remote")
public class RemoteController {

    @Autowired
    private TMDBService TMDBService;

    @Autowired
    private OMDBService omdbService;

    @GetMapping("/top_rated")
    public void getTopRatedMovies() {
        try {
            TMDBService.getMoviesFromTMDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/ratings")
    public void getRatings(){
        try{
            omdbService.getRatings();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
