package unit1;

public class Task2 {

    public void printFunctionResult(double a, double b, double h) {
        for (double i = a; i <= b; i += h) {
            double result = Math.tan(2 * i) - 3;
            System.out.println(i  + " | " + result);
        }
        System.out.println();
    }
}
