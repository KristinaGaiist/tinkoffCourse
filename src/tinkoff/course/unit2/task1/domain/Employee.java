package unit2.task1.domain;

import java.util.Objects;

public class Employee {

	private static int counter = 0;

	private final int id;
	private final String firstName;
	private final String lastName;
	private final WorkPlace workPlace;
	private boolean fired = false;

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = counter++;
		this.workPlace = new WorkPlace(this);
	}

	public int getId() {
		return id;
	}

	public WorkPlace getWorkPlace() {
		return workPlace;
	}

	public void fire() {
		this.fired = true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Employee employee = (Employee) o;
		return id == employee.id && Objects.equals(firstName, employee.firstName) && Objects.equals(
			lastName, employee.lastName) && Objects.equals(workPlace, employee.workPlace);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, workPlace);
	}

	@Override
	public String toString() {
		return id + " " + firstName + " " + lastName + " " + fired;
	}
}
