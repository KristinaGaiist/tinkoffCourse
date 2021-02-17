package unit2.task1.domain.task2;

public class Pen extends OfficeSupply {

    private final PenColor color;

    public Pen(double cost, PenColor color) {
        super("ручка", cost);
        this.color = color;
    }
}
