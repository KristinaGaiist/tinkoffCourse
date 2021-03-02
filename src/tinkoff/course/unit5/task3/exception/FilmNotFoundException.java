package unit5.task3.exception;

import unit5.task3.ExceptionMessage;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String filmName) {
        super(String.format(ExceptionMessage.FILM_NOT_FOUND, filmName));
    }
}
