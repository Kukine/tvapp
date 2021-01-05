package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.Movie;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query(value = "{ 'actors' : { $in : [?0] }}")
    List<Movie> findAnyByActors(String[] values);
}

