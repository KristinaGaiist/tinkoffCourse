package unit2.task1.domain;

import java.util.stream.StreamSupport;

public class OfficeSuppliesCostCalculator {

	public double calculate(Employee employee) {
		Iterable<OfficeSupply> officeSupplies = employee.getWorkPlace().getOfficeSupplies();
		return StreamSupport.stream(officeSupplies.spliterator(), false)
			.map(OfficeSupply::getCost)
			.reduce(0d, Double::sum);
	}


}
