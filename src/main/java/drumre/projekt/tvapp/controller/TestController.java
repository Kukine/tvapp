package drumre.projekt.tvapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import drumre.projekt.tvapp.remote.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TMDBService tmdbService;

    @GetMapping("/fix")
    public void test() throws JsonProcessingException {
        tmdbService.fixActors();
    }

}
