package cinema.controller;

import cinema.command.UpdateDateCommand;
import cinema.command.CreateMovieCommand;
import cinema.entity.MovieDTO;
import cinema.command.CreateReservationCommand;
import cinema.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDTO> getMovies(@RequestParam Optional<String> title) {
        return service.getMovies(title);
    }

    @GetMapping("/{id}")
    public MovieDTO findMovieById(@PathVariable Long id) {
        return service.findMovieById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createMovie(@RequestBody @Valid CreateMovieCommand command) {
        return service.createMovie(command);
    }

    @PostMapping("/{id}/reserve")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO reserveSpaceByMovieId(@PathVariable Long id, @RequestBody CreateReservationCommand command) {
        return service.reserveSpaceByMovieId(id, command);
    }

    @PutMapping("/{id}")
    public MovieDTO updateDateOfMovie(@PathVariable Long id, @RequestBody UpdateDateCommand command) {
        return service.updateDateOfMovie(id, command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        service.deleteAll();
    }
}
