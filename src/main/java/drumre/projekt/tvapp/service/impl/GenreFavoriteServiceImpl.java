package drumre.projekt.tvapp.service.impl;

import drumre.projekt.tvapp.data.GenreFavorite;
import drumre.projekt.tvapp.repository.GenreFavoriteRepository;
import drumre.projekt.tvapp.service.GenreFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreFavoriteServiceImpl implements GenreFavoriteService {

    private final GenreFavoriteRepository genreFavoriteRepository;

    @Autowired
    public GenreFavoriteServiceImpl(GenreFavoriteRepository genreFavoriteRepository) {
        this.genreFavoriteRepository = genreFavoriteRepository;
    }

    @Override
    public GenreFavorite getByUserID(Long userID) {
        return genreFavoriteRepository.getByUserID(userID);
    }

    @Override
    public void updateFavorites(GenreFavorite genreFavorite) {
        genreFavoriteRepository.save(genreFavorite);
    }

}
