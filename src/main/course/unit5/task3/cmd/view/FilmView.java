package unit5.task3.cmd.view;

import java.text.ParseException;
import java.util.Date;
import unit5.task3.ParseHelper;
import unit5.task3.application.FilmService;
import unit5.task3.exception.DateFormatException;
import unit5.task3.exception.IllegalArgumentsSizeException;
import unit5.task3.exception.ValidationException;

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

    public void showFilmInfo(String[] args) throws ValidationException {
        if (args.length != 1) {
            throw new IllegalArgumentsSizeException();
        }
        System.out.println(filmService.getFilmInfo(args[0]));
    }

    public void showFilmActors(String[] args) throws ValidationException {
        if (args.length != 1) {
            throw new IllegalArgumentsSizeException();
        }
        System.out.println(filmService.getFilmInfo(args[0]).getActors());
    }

    public void editFilmName(String[] args) throws ValidationException {
        if (args.length != 2) {
            throw new IllegalArgumentsSizeException();
        }
        filmService.editFilmName(args[0], args[1]);
    }

    public void addFilm(String[] args) throws ValidationException {
        if (args.length != 2) {
            throw new IllegalArgumentsSizeException();
        }
        Date date;
        try {
            date = ParseHelper.parseStringToDate(args[1]);
        } catch (ParseException e) {
            throw new DateFormatException(args[1]);
        }

        filmService.addFilm(args[0], date);
    }

    public void addActor(String[] args) throws ValidationException {
        if (args.length != 4) {
            throw new IllegalArgumentsSizeException();
        }
        Date date;
        try {
            date = ParseHelper.parseStringToDate(args[3]);
        } catch (ParseException e) {
            throw new DateFormatException(args[3]);
        }
        filmService.addActor(args[0], args[1], args[2], date);
    }
}
