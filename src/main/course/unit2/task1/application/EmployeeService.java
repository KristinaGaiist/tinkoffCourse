package unit2.task1.application;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import unit2.task1.application.contracts.EmployeeDto;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.persistence.IDataStorage;

public class EmployeeService {

    private final IDataStorage storage;

    public EmployeeService(IDataStorage storage) {
        this.storage = storage;
    }

    public void add(String firstName, String lastName) {
        Office office = this.storage.getOffice();
        office.addEmployee(new Employee(firstName, lastName));
    }

    public Iterable<EmployeeDto> getAll() {
        Office office = this.storage.getOffice();

        return StreamSupport.stream(office.getEmployees().spliterator(), false)
                .map(e -> new EmployeeDto(e.getFirstName(), e.getLastName()))
                .collect(Collectors.toList());
    }

    public void fireEmployee(int id) {
        Office office = this.storage.getOffice();
        office.getEmployeeById(id).fire();
    }
}
