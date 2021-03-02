package unit5.task3.persistence;

import unit5.task3.entity.Film;

public interface IFilmRepository {

    Iterable<Film> getAll();

    void add(Film film);

    void save();
}
