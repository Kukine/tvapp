package drumre.projekt.tvapp.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.security.RestAuthenticationEntryPoint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class TMDBService {

    private static final Logger logger = LoggerFactory.getLogger(TMDBService.class);


    private static String apiKey = "e4f17e8f3c9aa938e9dbd95fc974798f";
    private static String baseUrl = "https://api.themoviedb.org/3/";
    private static String topRated = "movie/top_rated";

    @Autowired
    private MovieRepository movieRepository;



    public List<Movie> getMoviesFromTMDB() throws JsonProcessingException, InterruptedException {
        movieRepository.deleteAll();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + topRated + "?api_key=" + apiKey, String.class);
        TMDBListResponse list = new ObjectMapper().readValue(response.getBody(), TMDBListResponse.class);
        movieRepository.saveAll(list.results);

        int total = list.total_pages;
        for(int i = 2; i < total; i++){
            Thread.sleep(250);
            ResponseEntity<String> pageresponse=restTemplate.getForEntity(baseUrl + topRated + "?api_key=" + apiKey + "&page=" + i, String.class);
            TMDBListResponse pagelist = new ObjectMapper().readValue(response.getBody(), TMDBListResponse.class);
            movieRepository.saveAll(pagelist.results);
            logger.info("Fetched movies from remote tmdb server: page = %d", i);
        }
        long size = movieRepository.count();
        logger.info("Finished with loading movies from tmdb: total movie collection size = %d", size);
        return null;
    }



}

@NoArgsConstructor
@Data
class TMDBListResponse{
    public int page;
    public int total_pages;
    public int total_results;
    public List<Movie> results;
}
