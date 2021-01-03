package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.OMDBRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<OMDBRating, String> {
}
