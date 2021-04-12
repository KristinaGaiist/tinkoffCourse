package unit9.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger count = new AtomicInteger();

    public int add() {
        return count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }
}
