package unit2.task4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import unit2.task1.domain.task2.NoteBook;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.PageType;
import unit2.task1.domain.task2.Pen;
import unit2.task1.domain.task2.PenColor;
import unit2.task1.domain.task2.Ruler;

public class SortOfficeSupplier {

    public static void main(String... args) {
        List<OfficeSupply> supplies = new ArrayList<>();
        supplies.add(new Pen(12.6, PenColor.BLACK));
        supplies.add(new Ruler(12.6, 50));
        supplies.add(new NoteBook(12.6, 30, PageType.SQUARE));

        sortBeginnersKitByCost(supplies);
        System.out.println(supplies);

        sortBeginnersKitByName(supplies);
        System.out.println(supplies);

        sortBeginnersKitByCostAndName(supplies);
        System.out.println(supplies);
    }

    public static void sortBeginnersKitByName(List<OfficeSupply> supplies) {
        supplies.sort(Comparator.comparing(OfficeSupply::getName));
    }

    public static void sortBeginnersKitByCost(List<OfficeSupply> supplies) {
        supplies.sort(Comparator.comparing(OfficeSupply::getCost));
    }

    public static void sortBeginnersKitByCostAndName(List<OfficeSupply> supplies) {
        supplies.sort(Comparator.comparing(OfficeSupply::getName).thenComparing(OfficeSupply::getCost));
    }
}
