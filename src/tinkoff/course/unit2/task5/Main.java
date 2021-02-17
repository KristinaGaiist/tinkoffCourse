package unit2.task5;

public class Main {

    public static void main(String... args) {
        DivisionIntoThirteen divisionIntoThirteen = number -> number % 13 == 0;
        System.out.println(divisionIntoThirteen.divisionIntoThirteen(26));

        Discriminant discriminant = (a, b, c) -> (b * b) - (4 * a * c);
        System.out.println(discriminant.calculate(5.8, 7.2, 1.7));

        Sum sum = Double::sum;
        System.out.println(sum.calculate(5.5, 6.6));
        System.out.println(sum.calculate(5.5f, 6.6f));
        System.out.println(sum.calculate(5, 6));
    }
}
