package unit2.task1.domain.task2;

public class OfficeSupplyCreator {

    public static OfficeSupply create(String name, double cost) {
        return switch (name) {
            case "ручка" -> new Pen(cost, PenColor.BLUE);
            case "блокнот" -> new NoteBook(cost, 20, PageType.PLANE);
            case "линейка" -> new Ruler(cost, 30);
            default -> throw new IllegalArgumentException(String.format("Канцелярского товара %s не существует", name));
        };
    }
}
