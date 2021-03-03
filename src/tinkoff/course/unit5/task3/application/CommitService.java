package unit5.task3.application;

import unit5.task3.persistence.IFilmRepository;

public class CommitService {

    private final IFilmRepository storage;

    public CommitService(IFilmRepository storage) {
        this.storage = storage;
    }

    public void saveChanges() {
        storage.save();
    }
}
