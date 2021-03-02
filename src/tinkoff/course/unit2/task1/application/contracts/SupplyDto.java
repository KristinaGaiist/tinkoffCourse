package unit2.task1.application.contracts;

import java.util.Objects;

public class SupplyDto {

    private final String name;
    private final double cost;

    public SupplyDto(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupplyDto supplyDto = (SupplyDto) o;
        return Double.compare(supplyDto.cost, cost) == 0 &&
            Objects.equals(name, supplyDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }
}
