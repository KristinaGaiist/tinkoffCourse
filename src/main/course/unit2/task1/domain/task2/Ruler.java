package unit2.task1.domain.task2;

import java.util.Objects;

public class Ruler extends OfficeSupply {

    private final int length;

    public Ruler(double cost, int length) {
        super("линейка", cost);
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ruler ruler = (Ruler) o;
        return length == ruler.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length);
    }

    @Override
    public String toString() {
        return super.toString() + " " + length;
    }
}
