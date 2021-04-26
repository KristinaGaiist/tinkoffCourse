package unit2.task1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import unit2.task1.domain.task2.NoteBook;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.PageType;
import unit2.task1.domain.task2.Pen;
import unit2.task1.domain.task2.PenColor;
import unit2.task1.domain.task2.Ruler;

public class WorkPlace {

	private Employee employee;
	private List<OfficeSupply> officeSupplies;

	public WorkPlace(Employee employee) {
		this.employee = employee;
		officeSupplies = new ArrayList<>();
		officeSupplies.add(new Pen(33.12, PenColor.BLUE));
		officeSupplies.add(new Ruler(53.12, 30));
		officeSupplies.add(new NoteBook(144.1, 50, PageType.PLANE));
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
		return Objects.equals(officeSupplies, workPlace.officeSupplies);
	}

	@Override
	public int hashCode() {
		return Objects.hash(officeSupplies);
	}

	@Override
	public String toString() {
		return employee.toString() + " " + officeSupplies.toString();
	}
}
