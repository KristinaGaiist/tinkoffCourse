package unit1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task4 {

    public static void main(String... args) {
        try {
            int a = readInt("Введите целочисленное a: ");
            int b = readInt("Введите целочисленное b: ");
            int c = readInt("Введите целочисленное c: ");
            printEquationResult(a, b, c);
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка.");
        }
    }

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

    private static int readInt(String message) throws IOException {
        System.out.print(message);
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (true) {
            try {
                String line = bufferedReader.readLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Попробуйте еще раз. " + message);
            }
        }
    }
}
