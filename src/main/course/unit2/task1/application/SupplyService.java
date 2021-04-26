package unit2.task1.application;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import unit2.task1.application.contracts.SupplyDto;
import unit2.task1.domain.Office;
import unit2.task1.domain.task2.OfficeSupplyCreator;
import unit2.task1.persistence.IDataStorage;

public class SupplyService {

    private final IDataStorage storage;

    public SupplyService(IDataStorage storage) {
        this.storage = storage;
    }

    public void add(int id, String name, double cost) {
        Office office = this.storage.getOffice();
        office
                .getEmployeeById(id)
                .getWorkPlace()
                .add(OfficeSupplyCreator.create(name, cost));
    }

    public Iterable<SupplyDto> getAll(int id) {
        Office office = this.storage.getOffice();
        return StreamSupport.stream(office.getEmployeeById(id).getWorkPlace().getOfficeSupplies().spliterator(), false)
                .map(e -> new SupplyDto(e.getName(), e.getCost()))
                .collect(Collectors.toList());
    }
}
