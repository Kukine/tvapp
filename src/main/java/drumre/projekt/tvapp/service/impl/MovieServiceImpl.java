package drumre.projekt.tvapp.service.impl;

import com.mongodb.client.MongoClient;
import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.MovieWithDetailsDTO;
import drumre.projekt.tvapp.data.Movie;
import drumre.projekt.tvapp.data.MovieLike;
import drumre.projekt.tvapp.remote.TMDBService;
import drumre.projekt.tvapp.repository.LikeRepository;
import drumre.projekt.tvapp.repository.MovieRepository;
import drumre.projekt.tvapp.service.MovieService;
import drumre.projekt.tvapp.service.mapper.MovieMapper;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    MovieRepository movieRepository;

    MovieMapper movieMapper;

    LikeRepository likeRepository;

    MongoTemplate mongoTemplate;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, LikeRepository likeRepository,  MongoTemplate mongoTemplate){
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.likeRepository = likeRepository;
        this.mongoTemplate  = mongoTemplate;
    }

    @Override
    public Movie GetByID(String id) {
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("movie/repo: Movie not found"));
    }

    @Override
    public List<BasicMovieDTO> GetBatch(int size) {
        List<Movie> movies = movieRepository.findAll(Sort.by("voteAverage").descending()).stream().limit(size).collect(Collectors.toList());
        return movieMapper.batchMovieToDTO(movies);
    }

    @Override
    public void likeMovie(Long userID, String movieID) {
        MovieLike like = new MovieLike(userID, movieID, new Timestamp(System.currentTimeMillis()));
        List<MovieLike> all = this.likeRepository.findAll(Example.of(like));
        if(all.size() == 0){
            this.likeRepository.save(like);
        }
    }

    @Override
    public List<Movie> findMoviesReccomendedForUser(Long userID) {
        Query likeQuery = new Query();
        likeQuery.addCriteria(Criteria.where("userID").is(userID));
        List<MovieLike> userLikes = mongoTemplate.find(likeQuery, MovieLike.class);
        List<String> likedMovieIDS = userLikes.stream().map(x -> x.movieID).collect(Collectors.toList());
        if(likedMovieIDS.size() == 0){
            return new ArrayList<>();
        }

        Query movieQuery = new Query();
        movieQuery.addCriteria(Criteria.where("_id").in(likedMovieIDS));
        List<Movie> likedMovies = mongoTemplate.find(movieQuery, Movie.class);

        List<String> actorPool = new ArrayList<>();
        List<String> genrePool = new ArrayList<>();
        List<String> directorPool = new ArrayList<>();

        likedMovies.forEach(x -> {
            if(x.getActors() != null){
                actorPool.addAll(x.getActors());
            }
            if(x.getGenres() != null){
                genrePool.addAll(x.getGenres());
            }
            if(x.getDirector() != null){
                directorPool.add(x.getDirector());
            }
        });

        Map<String, Long> actorCounts = actorPool.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> genreCounts = genrePool.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> directorCounts = directorPool.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Set<String> actors = new HashSet<>(actorPool);
        Set<String> genres = new HashSet<>(genrePool);
        Set<String> directors = new HashSet<>(directorPool);

        logger.info(String.format("Actor pool: %s",actors.toString()));
        logger.info(String.format("Genre pool: %s",genres.toString()));
        logger.info(String.format("Director pool: %s",directors.toString()));

        logger.info(String.format("Actor counts: %s",actorCounts.toString()));
        logger.info(String.format("Genre counts: %s",genreCounts.toString()));
        logger.info(String.format("Director counts: %s",directorCounts.toString()));

        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("actors").in(actors),
                Criteria.where("genre").in(genres),
                Criteria.where("director").in(directors));
        Query query = new Query(criteria);

        List<Movie> similar = mongoTemplate.find(query, Movie.class);

        List<SimilarMovie> similarMovies = new ArrayList<>();
        int cumulative = 0;
        for(Movie s: similar){
            int score = 0;
            Set<String> overlapActors = s.getActors().stream().filter(actors::contains).collect(Collectors.toSet());
            Set<String> overlapGenres = s.getGenres().stream().filter(genres::contains).collect(Collectors.toSet());
            boolean sameDirector = directors.contains(s.getDirector());
            for(String a : overlapActors){
                score += 20 * actorCounts.get(a);
            }
            for(String g: overlapGenres){
                score += 10 * genreCounts.get(g);
            }
            if(sameDirector){
                logger.info(String.format("Director found for movie: %s - %s", s.title, s.getDirector()));
                score += 100 * directorCounts.get(s.getDirector());
            }
            score += s.getPopularity()/5;
            similarMovies.add(new SimilarMovie(score, s));
            cumulative += score;
        }
        int finalCumulative = cumulative;
        logger.info(String.format("Size of similar movies: %d", similar.size()));

        List<SimilarMovie> output = similarMovies.stream().sorted(Comparator.comparing(SimilarMovie::getScore).reversed()).filter(x -> x.score > finalCumulative / similar.size() && !likedMovieIDS.contains(x.movie.getId())).collect(Collectors.toList());
        logger.info(String.format("Size of final similar movies: %d", output.size()));
        for(SimilarMovie o: output){
            logger.info(String.format("Score for movie %s: %d", o.movie.title, o.score));
        }
        return output.stream().map(x -> x.movie).collect(Collectors.toList());
    }
}

@Data
@AllArgsConstructor
class SimilarMovie{
    int score;
    Movie movie;

}
