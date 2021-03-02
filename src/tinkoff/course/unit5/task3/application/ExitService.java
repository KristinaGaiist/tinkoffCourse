package unit5.task3.application;

import unit5.task3.persistence.IFilmRepository;

public class ExitService {

    private final IFilmRepository storage;

    public ExitService(IFilmRepository storage) {
        this.storage = storage;
    }

    public void exit() {
        storage.save();
    }
}
