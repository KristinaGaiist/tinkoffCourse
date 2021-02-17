package unit2.task1.domain.task2;

public class NoteBook extends OfficeSupply {

    private final int pageCount;
    private final PageType pageType;

    public NoteBook(double cost, int pageCount, PageType pageType) {
        super("блокнот", cost);
        this.pageCount = pageCount;
        this.pageType = pageType;
    }
}
