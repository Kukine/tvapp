package drumre.projekt.tvapp.controller;

import drumre.projekt.tvapp.controller.dto.BasicMovieDTO;
import drumre.projekt.tvapp.controller.dto.GenreRequest;
import drumre.projekt.tvapp.controller.dto.MoviesByGenreRequest;
import drumre.projekt.tvapp.data.Genre;
import drumre.projekt.tvapp.data.GenreFavorite;
import drumre.projekt.tvapp.security.CurrentUser;
import drumre.projekt.tvapp.security.UserPrincipal;
import drumre.projekt.tvapp.service.GenreFavoriteService;
import drumre.projekt.tvapp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreFavoriteService genreFavoriteService;

    @Autowired
    public GenreController(final GenreService genreService, final GenreFavoriteService genreFavoriteService) {
        this.genreService = genreService;
        this.genreFavoriteService = genreFavoriteService;
    }

    @GetMapping
    public List<Genre> getAllGenres(){
        return this.genreService.getAllGenres();
    }

    @GetMapping("/favorites")
    public List<String> getFavoriteGenres(@CurrentUser UserPrincipal userPrincipal){
        GenreFavorite genreFavorite = genreFavoriteService.getByUserID(userPrincipal.getId());

        if (genreFavorite == null) {
            return Collections.emptyList();
        }

        return genreFavorite.getGenres();
    }


    @PostMapping("/favorites")
    public boolean updateFavoriteGenres(@CurrentUser UserPrincipal userPrincipal, @RequestBody GenreRequest req){
        GenreFavorite genreFavorite = genreFavoriteService.getByUserID(userPrincipal.getId());

        if (genreFavorite == null) {
            genreFavorite = new GenreFavorite();
            genreFavorite.setUserID(userPrincipal.getId());
        }

        genreFavorite.setGenres(req.getGenres());

        genreFavoriteService.updateFavorites(genreFavorite);
        return true;
    }

}
