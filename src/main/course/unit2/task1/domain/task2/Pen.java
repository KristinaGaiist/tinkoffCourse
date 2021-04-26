package unit2.task1.domain.task2;

import java.util.Objects;

public class Pen extends OfficeSupply {

    private final PenColor color;

    public Pen(double cost, PenColor color) {
        super("ручка", cost);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pen pen = (Pen) o;
        return color == pen.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }

    @Override
    public String toString() {
        return super.toString() + " " + color;
    }
}
