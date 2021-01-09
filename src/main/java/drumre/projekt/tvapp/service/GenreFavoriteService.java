package drumre.projekt.tvapp.service;

import drumre.projekt.tvapp.data.GenreFavorite;

public interface GenreFavoriteService {

    GenreFavorite getByUserID(Long userID);

    void updateFavorites(GenreFavorite genreFavorite);

}
