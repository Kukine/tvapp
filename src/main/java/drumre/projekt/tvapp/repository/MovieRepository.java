package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
}

