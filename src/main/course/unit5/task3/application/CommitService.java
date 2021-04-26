package unit5.task3.application;

import unit5.task3.persistence.IUnitOfWork;

public class CommitService {

    private final IUnitOfWork storage;

    public CommitService(IUnitOfWork storage) {
        this.storage = storage;
    }

    public void saveChanges() {
        storage.save();
    }
}
