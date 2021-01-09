package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.GenreFavorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreFavoriteRepository extends MongoRepository<GenreFavorite, String> {

    GenreFavorite getByUserID(Long userID);

    boolean existsByUserID(Long userID);

}
