package unit2.task1.domain.task2;

public class OfficeSupplyCreator {

    public static OfficeSupply create(String name, double cost) {
        switch (name) {
            case "ручка" :
                return new Pen(cost, PenColor.BLUE);
            case "блокнот" :
                return new NoteBook(cost, 20, PageType.PLANE);
            case "линейка" :
                return new Ruler(cost, 30);
            default: throw new IllegalArgumentException(String.format("Канцелярского товара %s не существует", name));
        }
    }
}
