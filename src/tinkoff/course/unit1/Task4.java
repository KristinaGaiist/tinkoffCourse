package unit1;

public class Task4 {

    public void printEquationResult(int a, int b, int c) {
        int sumAandB = Math.addExact(Math.multiplyExact(2, a), b);
        System.out.println(Math.floorDiv(Math.negateExact(c), sumAandB));
    }
}
