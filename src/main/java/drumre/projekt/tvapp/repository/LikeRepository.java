package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.MovieLike;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<MovieLike, String> {
}
