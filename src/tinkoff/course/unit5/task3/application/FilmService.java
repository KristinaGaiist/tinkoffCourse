package unit5.task3.application;

import java.util.Date;
import java.util.stream.StreamSupport;
import unit5.task3.entity.Actor;
import unit5.task3.entity.Film;
import unit5.task3.exception.FilmAlreadyExistException;
import unit5.task3.exception.FilmNotFoundException;
import unit5.task3.persistence.IFilmRepository;

public class FilmService {

    private final IFilmRepository storage;

    public FilmService(IFilmRepository storage) {
        this.storage = storage;
    }

    public Iterable<Film> getFilmCollection() {
        return storage.getAll();
    }

    public Film getFilmInfo(String filmName) {
        return StreamSupport.stream(storage.getAll().spliterator(), false)
            .filter(film -> film.getName().equals(filmName))
            .findFirst()
            .orElseThrow(() -> new FilmNotFoundException(filmName));
    }

    public void editFilmName(String oldName, String newName) {
        Film film = getFilmInfo(oldName);
        film.setName(newName);
    }

    public void addFilm(String name, Date releaseDate) {
        StreamSupport.stream(storage.getAll().spliterator(), false)
            .filter(film -> film.getName().equals(name))
            .findFirst()
            .ifPresent(f -> {
                throw new FilmAlreadyExistException(name);
            });

        storage.add(new Film(name, releaseDate));
    }

    public void addActor(String filmName, String actorFirstName, String actorLastName, Date birthDay) {
        Film film = getFilmInfo(filmName);
        film.addActor(new Actor(actorFirstName, actorLastName, birthDay));
    }
}
