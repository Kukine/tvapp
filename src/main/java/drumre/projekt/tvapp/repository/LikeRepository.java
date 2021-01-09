package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.MovieLike;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LikeRepository extends MongoRepository<MovieLike, String> {

    List<MovieLike> getByUserID(Long userID);

    boolean existsByUserIDAndMovieID(Long userId, String movieId);

    void deleteByUserIDAndMovieID(Long userId, String movieId);

    List<MovieLike> getByLikeTimeAfter(LocalDateTime time);

}
