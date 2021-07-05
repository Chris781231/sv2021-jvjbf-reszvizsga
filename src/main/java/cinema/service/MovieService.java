package cinema.service;

import cinema.command.UpdateDateCommand;
import cinema.command.CreateMovieCommand;
import cinema.entity.Movie;
import cinema.entity.MovieDTO;
import cinema.command.CreateReservationCommand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovieService {

    private AtomicLong idGenerator = new AtomicLong();

    private final List<Movie> movies = new ArrayList<>();

    private final ModelMapper modelMapper;

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDTO> getMovies(Optional<String> title) {
        return movies.stream()
                .filter(movie -> title.isEmpty() || movie.getTitle().toLowerCase().contains(title.get()))
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .toList();
    }

    public MovieDTO findMovieById(Long id) {
        Movie result = findById(id);
        return modelMapper.map(result, MovieDTO.class);
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getDate(), command.getMaxSpaces());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO reserveSpaceByMovieId(Long id, CreateReservationCommand command) {
        Movie movie = findById(id);
        movie.reserveSpace(command.getReservation());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO updateDateOfMovie(Long id, UpdateDateCommand command) {
        Movie movie = findById(id);
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteAll() {
        idGenerator = new AtomicLong();
        movies.clear();
    }


    // Private methods
    private Movie findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find movie with this id: " + id));
    }
}
