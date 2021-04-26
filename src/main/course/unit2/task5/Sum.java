package unit2.task5;

@FunctionalInterface
public interface Sum {

    double calculate(double a, double b);

    default double calculate(int a, int b) {
        return calculate((double) a, (double) b);
    }

    default double calculate(float a, float b) {
        return calculate((double) a, (double) b);
    }
}
