package drumre.projekt.tvapp.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.OMDBRating;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class OMDBService {
    private static final Logger logger = LoggerFactory.getLogger(OMDBService.class);

    public static String base = "http://www.omdbapi.com/?apikey=";
    public static String apiKey = "5fc24239";

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RatingRepository ratingRepository;

    public void getRatings() throws JsonProcessingException {
        List<Movie> all = this.movieRepository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        for (Movie m : all){
            if(m.getImdbId() == null || m.getImdbId().equals("")){
                continue;
            }
            if(m.getRated() != null){
                continue;
            }
            String url = base + apiKey + "&i=" + m.getImdbId();
            logger.info("Fetching ratings for movie from URL: " + url);
            ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
            OMDBRating rating = new ObjectMapper().readValue(response.getBody(), OMDBRating.class);
            m.setActors(Arrays.asList(rating.getActors().split(",").clone()));
            m.setDirector(rating.getDirector());
            m.setRatings(rating.getRatings());
            m.setRated(rating.getRated());
            this.movieRepository.save(m);
        }
        logger.info("Finished fetching reviews for all movies");
    }

    public OMDBRating getRating(String imdbID) throws  JsonProcessingException {
        String url = base + apiKey + "&i=" + imdbID;
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Fetching ratings for movie from URL: " + url);
        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
        OMDBRating rating = new ObjectMapper().readValue(response.getBody(), OMDBRating.class);
        return rating;
    }

}
