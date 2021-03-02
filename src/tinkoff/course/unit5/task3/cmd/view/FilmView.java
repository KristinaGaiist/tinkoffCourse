package unit5.task3.cmd.view;

import java.util.Date;
import unit5.task3.ParseHelper;
import unit5.task3.application.FilmService;
import unit5.task3.exception.IllegalArgumentsSizeException;

public class FilmView {

    private final FilmService filmService;

    public FilmView(FilmService filmService) {
        this.filmService = filmService;
    }

    public void showFilmCollection() {
        filmService
            .getFilmCollection()
            .forEach(System.out::println);
    }

    public void showFilmInfo(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentsSizeException();
        }
        System.out.println(filmService.getFilmInfo(args[0]));
    }

    public void showFilmActors(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentsSizeException();
        }
        System.out.println(filmService.getFilmInfo(args[0]).getActors());
    }

    public void editFilmName(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentsSizeException();
        }
        filmService.editFilmName(args[0], args[1]);
    }

    public void addFilm(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentsSizeException();
        }
        Date date = ParseHelper.parseStringToDate(args[1]);
        filmService.addFilm(args[0], date);
    }

    public void addActor(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentsSizeException();
        }
        Date date = ParseHelper.parseStringToDate(args[3]);
        filmService.addActor(args[0], args[1], args[2], date);
    }
}
