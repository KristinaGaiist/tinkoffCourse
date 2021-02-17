package unit2.task1.domain.task2;

public class Ruler extends OfficeSupply {

    private final int length;

    public Ruler(double cost, int length) {
        super("линейка", cost);
        this.length = length;
    }
}
