package unit1;

public class Task1 {

    private static int[] arrayOfEvenNumbersCase1() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            evenNumber += 2;
            array[i] = evenNumber;
        }

        return multiplyArrayElements(array);
    }

    private static int[] arrayOfEvenNumbersCase2() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];
        int i = 0;

        while (i < size) {
            evenNumber += 2;
            array[i] = evenNumber;
            i++;
        }

        return multiplyArrayElements(array);
    }

    private static int[] arrayOfEvenNumbersCase3() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];
        int i = 0;

        do {
            evenNumber += 2;
            array[i] = evenNumber;
            i++;
        }
        while (i < size);

        return multiplyArrayElements(array);
    }

    private static void printArray(int[] array) {
        for (int number : array) {
            System.out.print(number + " ");
        }
        System.out.println();
    }

    private static int[] multiplyArrayElements(int[] array) {
        int[] result = new int[array.length - 1];
        for (int i = 1; i < array.length; i++) {
            result[i - 1] = array[i] * array[i - 1];
        }
        return result;
    }

    public static void main(String... args) {
        printArray(arrayOfEvenNumbersCase1());
        printArray(arrayOfEvenNumbersCase2());
        printArray(arrayOfEvenNumbersCase3());
    }
}
