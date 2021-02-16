package unit2.task1.domain;

import java.util.ArrayList;
import java.util.List;

public class WorkPlace {

	private Employee employee;
	private List<OfficeSupply> officeSupplies;

	public WorkPlace(Employee employee) {
		this.employee = employee;
		this.officeSupplies = new ArrayList<>();
	}

	public Iterable<OfficeSupply> getOfficeSupplies() {
		return officeSupplies;
	}

	public void add(OfficeSupply officeSupply) {
		this.officeSupplies.add(officeSupply);
	}
}
