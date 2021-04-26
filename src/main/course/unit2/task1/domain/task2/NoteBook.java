package unit2.task1.domain.task2;

import java.util.Objects;

public class NoteBook extends OfficeSupply {

    private final int pageCount;
    private final PageType pageType;

    public NoteBook(double cost, int pageCount, PageType pageType) {
        super("блокнот", cost);
        this.pageCount = pageCount;
        this.pageType = pageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NoteBook noteBook = (NoteBook) o;
        return pageCount == noteBook.pageCount &&
                pageType == noteBook.pageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pageCount, pageType);
    }

    @Override
    public String toString() {
        return super.toString() + " " + pageCount + " " +pageType;
    }
}
