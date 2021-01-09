package drumre.projekt.tvapp.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

@Document(collection = "genre-favorites")
@Data
public class GenreFavorite {
    @Id
    public String id;
    public Long userID;
    public List<String> genres = Collections.emptyList();
}
