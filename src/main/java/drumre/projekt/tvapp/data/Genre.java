package drumre.projekt.tvapp.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@ToString
public class Genre {

    @Id
    private String id;
    private String name;
}
