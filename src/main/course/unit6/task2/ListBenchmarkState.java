package unit6.task2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ListBenchmarkState {

    private List<String> list;

    @Param({"50000", "500000", "1000000"})
    private int addItemsCount;


    @Param({"linked", "array"})
    private String typeOfList;

    public ListBenchmarkState() {
        list = new ArrayList<>();
    }

    @Setup(Level.Invocation)
    public void setUp() {
        if(typeOfList.equals("linked")) {
            list = new LinkedList<>();
        } else {
            list = new ArrayList<>();
        }
        for (var i = 0; i < addItemsCount; i++) {
            list.add("string");
        }
    }

    public int getAddItemsCount() {
        return addItemsCount;
    }

    public void setAddItemsCount(int addItemsCount) {
        this.addItemsCount = addItemsCount;
    }

    public List<String> getList() {
        return list;
    }

    public String getTypeOfList() {
        return typeOfList;
    }

    public void setTypeOfList(String typeOfList) {
        this.typeOfList = typeOfList;
    }
}
