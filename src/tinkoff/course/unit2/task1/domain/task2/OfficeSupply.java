package unit2.task1.domain.task2;

import java.util.Objects;

public abstract class OfficeSupply {

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

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OfficeSupply that = (OfficeSupply) o;
		return id == that.id &&
				Double.compare(that.cost, cost) == 0 &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, cost);
	}

	@Override
	public String toString() {
		return id + " " + name + " " + cost;
	}
}
