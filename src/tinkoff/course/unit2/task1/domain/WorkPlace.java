package unit2.task1.domain;

import unit2.task1.domain.task2.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class WorkPlace {

	private Employee employee;
	private List<OfficeSupply> officeSupplies;
	//task2
	private final List<OfficeSupply> beginnersKit;

	public WorkPlace(Employee employee) {
		this.employee = employee;
		this.officeSupplies = new ArrayList<>();
		beginnersKit = new ArrayList<>();
		beginnersKit.add(new Pen(33.12, PenColor.BLUE));
		beginnersKit.add(new Ruler(53.12, 30));
		beginnersKit.add(new NoteBook(144.1, 50, PageType.PLANE));
	}

	public Iterable<OfficeSupply> getOfficeSupplies() {
		return officeSupplies;
	}

	public void add(OfficeSupply officeSupply) {
		this.officeSupplies.add(officeSupply);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WorkPlace workPlace = (WorkPlace) o;
		return Objects.equals(employee, workPlace.employee) &&
				Objects.equals(officeSupplies, workPlace.officeSupplies) &&
				Objects.equals(beginnersKit, workPlace.beginnersKit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employee, officeSupplies, beginnersKit);
	}

	@Override
	public String toString() {
		return employee.toString() + " " + officeSupplies.toString();
	}
}
