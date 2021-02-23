package unit2.task1.application.contracts;

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
}
