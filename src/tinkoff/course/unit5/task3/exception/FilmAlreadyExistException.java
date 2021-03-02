package unit5.task3.exception;

import unit5.task3.ExceptionMessage;

public class FilmAlreadyExistException extends RuntimeException {

    public FilmAlreadyExistException(String name) {
        super(String.format(ExceptionMessage.FILM_ALREADY_EXIST, name));
    }
}
