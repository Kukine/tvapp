package drumre.projekt.tvapp.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoviesByGenreRequest {
    List<String> genres;
}
