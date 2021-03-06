package drumre.projekt.tvapp.remote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import drumre.projekt.tvapp.data.Genre;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.MovieDetails;
import drumre.projekt.tvapp.repository.GenreRepository;
import drumre.projekt.tvapp.repository.MovieRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TMDBService {

    private static final Logger logger = LoggerFactory.getLogger(TMDBService.class);


    private static String apiKey = "e4f17e8f3c9aa938e9dbd95fc974798f";
    private static String baseUrl = "https://api.themoviedb.org/3/";
    private static String topRated = "movie/top_rated";
    private static String genres = "/genre/movie/list";
    private static String details = "movie";
    private static String videos = "/videos";
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OMDBService omdbService;

    @Autowired
    private GenreRepository genreRepository;


    public String getYoutubeKey(String movieID) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "movie/" + movieID + videos + "?api_key=" + apiKey;
        logger.info(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        VideosResponse videos = new ObjectMapper().readValue(response.getBody(), VideosResponse.class);
        if(videos != null && videos.results != null && videos.results.size() != 0 ){
            for(VideoResult v : videos.results){
                if(v.site.equals("YouTube") && v.type.equals("Trailer")){
                    return v.key;
                }
            }
        }
        return null;
    }


    public List<Movie> getMoviesFromTMDB() throws JsonProcessingException, InterruptedException {
        movieRepository.deleteAll();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + topRated + "?api_key=" + apiKey, String.class);
        TMDBListResponse list = new ObjectMapper().readValue(response.getBody(), TMDBListResponse.class);

        List<Genre> genres = genreRepository.findAll();
        HashMap<String,String> genreIDs = new HashMap<>();
        genres.forEach(x -> genreIDs.put(x.getId(),x.getName()));
        logger.info(genreIDs.keySet().toString());
        for(Movie m: list.results){
            ArrayList<String> g = new ArrayList<>();
            for(String id: m.genreIds){
                g.add(genreIDs.get(id));
            }
            m.setGenres(g);
            m.MapFromMovieDetails(getDetails(m.id));
        }

        movieRepository.saveAll(list.results);

        int total = list.total_pages;
        for(int i = 2; i < total; i++){
            Thread.sleep(50);
            String url = baseUrl + topRated + "?api_key=" + apiKey + "&page=" + i;
            logger.info(String.format("Fetching url: %s", url));
            ResponseEntity<String> pageresponse = restTemplate.getForEntity(baseUrl + topRated + "?api_key=" + apiKey + "&page=" + i, String.class);
            TMDBListResponse pagelist = new ObjectMapper().readValue(pageresponse.getBody(), TMDBListResponse.class);
            for(Movie m: pagelist.results){
                ArrayList<String> g = new ArrayList<>();
                for(String id: m.genreIds){
                    g.add(genreIDs.get(id));
                }
                m.setGenres(g);
                m.MapFromMovieDetails(getDetails(m.id));

            }
            movieRepository.saveAll(pagelist.results);
            pagelist.results.forEach(x -> logger.info(String.format("Saving movie with Title: %s, with genres: %s", x.title,x.genres.toString())));
            logger.info(String.format("Fetched movies from remote tmdb server: page = %d", i));
        }
        long size = movieRepository.count();
        logger.info(String.format("Finished with loading movies from tmdb: total movie collection size = %d",size));
        return null;
    }

   public List<Genre> getGenres() throws JsonProcessingException, InterruptedException {
       RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + genres + "?api_key=" + apiKey, String.class);
       GenreResponse pagelist = new ObjectMapper().readValue(response.getBody(), GenreResponse.class);
       genreRepository.saveAll(pagelist.genres);
       logger.info("Saved all movie genres!");
       return pagelist.genres;
   }


   public MovieDetails getDetails(String movieID) throws JsonProcessingException {
       RestTemplate detailTemplate = new RestTemplate();
       logger.info("Fetching movie details - URL: " + baseUrl + details + "/" + movieID + "?api_key=" + apiKey);
       ResponseEntity<String> detailResponse = detailTemplate.getForEntity(baseUrl + details + "/" + movieID + "?api_key=" + apiKey, String.class);
       MovieDetails movieDetails = new ObjectMapper().readValue(detailResponse.getBody(), MovieDetails.class);
       return movieDetails;
   }

   public void fixActors() throws JsonProcessingException{
        List<Movie> movies = this.movieRepository.findAll();
        logger.info("Started fixing actors");
        for(Movie m: movies){
            if(m.getActors() != null && m.getActors().size()>1){
               List<String> actors = new ArrayList<>();
               for(String a: m.getActors()){
                   String in = a.trim();
                   actors.add(in);
               }
               m.setActors(actors);
               this.movieRepository.save(m);
            }
        }
        logger.info("Done fixing actors!");
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

@NoArgsConstructor
@Data
class GenreResponse{
    public List<Genre> genres;
}

@NoArgsConstructor
@Data
class VideosResponse{
    Long id;
    List<VideoResult> results;
}

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
class VideoResult{
    String id;
    String key;
    String site;
    String type;
}