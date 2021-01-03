package drumre.projekt.tvapp.repository;

import drumre.projekt.tvapp.data.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}
