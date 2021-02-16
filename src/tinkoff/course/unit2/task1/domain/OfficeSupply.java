package unit2.task1.domain;

import java.util.Objects;

public class OfficeSupply {

	private static int counter = 0;

	private final int id;
	private final String name;
	private double cost;

	public OfficeSupply(String name, double cost) {
		this.name = name;
		this.cost = cost;
		this.id = counter++;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}
}
