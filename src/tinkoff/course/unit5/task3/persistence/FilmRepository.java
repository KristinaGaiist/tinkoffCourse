package unit5.task3.persistence;

import java.io.IOException;
import java.util.List;
import unit5.task3.JsonStorage;
import unit5.task3.entity.Film;

public class FilmRepository implements IFilmRepository {

    private final JsonStorage filmJsonStorage;
    private List<Film> films;

    public FilmRepository() {
        films = null;
        filmJsonStorage = new JsonStorage("src\\tinkoff\\resources\\film-collection.json");
    }

    @Override
    public Iterable<Film> getAll() {
        if (films != null) {
            return films;
        }

        try {
            films = filmJsonStorage.read();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file. See inner exception.", e);
        }

        return films;
    }

    @Override
    public void add(Film film) {
        films.add(film);
    }

    @Override
    public void save() {
        try {
            filmJsonStorage.save(films);
        } catch (IOException e) {
            throw new RuntimeException("Can't save data to file. See inner exception", e);
        }
    }
}