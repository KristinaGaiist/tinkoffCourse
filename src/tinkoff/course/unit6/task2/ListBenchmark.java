package unit6.task2;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

public class ListBenchmark {

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addElements(ListBenchmarkState state) {
        state.getList().add("string");
    }

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void addElementsByIndex(ListBenchmarkState state) {
        state.getList().add(state.getAddItemsCount() / 2, "string");
    }

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getElementsByIndex(ListBenchmarkState state) {
        state.getList().get(state.getAddItemsCount() / 2);
    }

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void containsElements(ListBenchmarkState state) {
        state.getList().contains("abcd");
    }

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void removeElements(ListBenchmarkState state) {
        state.getList().remove("abcd");
    }

    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void removeElementsByIndex(ListBenchmarkState state) {
        state.getList().remove(state.getAddItemsCount() / 2);
    }
}
