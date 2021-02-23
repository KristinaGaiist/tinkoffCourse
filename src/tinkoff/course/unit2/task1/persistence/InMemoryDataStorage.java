package unit2.task1.persistence;

import unit2.task1.domain.Office;

public final class InMemoryDataStorage implements IDataStorage {

    private final Office office;

    public InMemoryDataStorage() {
        this.office = new Office();
    }

    @Override
    public Office getOffice() {
        return this.office;
    }
}
