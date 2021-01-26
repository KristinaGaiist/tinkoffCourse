package unit1;

public class Task4 {

    private static void printEquationResult(int a, int b, int c) {
        int aAndC = Math.multiplyExact(a, c);
        double discriminant = Math.pow(b, 2) - Math.multiplyExact(4, aAndC);

        if (discriminant == 0) {
            int x = -b / Math.multiplyExact(a, 2);
            System.out.println("Уравнение имеет единственный корень: x = " + x);
        } else if (discriminant > 0) {
            double discriminantRoot = Math.sqrt(discriminant);
            int doubleA = Math.multiplyExact(a, 2);
            double x1 = (-b - discriminantRoot) / doubleA;
            double x2 = (-b + discriminantRoot) / doubleA;

            System.out.println("Корни уравнения: x1 = " + x1 + ", x2 = " + x2);
        } else {
            System.out.println("Уравнение не имеет действительных корней");
        }
    }

    public static void main(String... args) {
        printEquationResult(1, 6, 9);
        printEquationResult(4, 6, 1);
        printEquationResult(45, 6, 1);

    }
}
