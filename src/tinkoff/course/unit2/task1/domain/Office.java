package unit2.task1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Office {

	private final List<Employee> employees;

	public Office() {
		this.employees = new ArrayList<>();
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public Optional<Employee> getEmployeeById(int id) {
		for (Employee employee : employees) {
			if (employee.getId() == id) {
				return Optional.of(employee);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Office office = (Office) o;
		return Objects.equals(employees, office.employees);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employees);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		employees.forEach(employee ->
				stringBuilder.append(employee.getId()).append(" ").append(employee.getWorkPlace().toString()));
		return stringBuilder.toString();
	}
}
