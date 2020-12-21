package unit1;

public class Task1 {

    public int[] arrayOfEvenNumbersCase1() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];

        for(int i = 0; i < size; i++) {
            evenNumber += 2;
            array[i] = evenNumber;
        }

        return array;
    }

    public int[] arrayOfEvenNumbersCase2() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];
        int i = 0;

        while (i < size) {
            evenNumber += 2;
            array[i] = evenNumber;
            i++;
        }

        return array;
    }

    public int[] arrayOfEvenNumbersCase3() {
        int size = 100;
        int evenNumber = 0;
        int[] array = new int[size];
        int i = 0;

        do{
            evenNumber += 2;
            array[i] = evenNumber;
            i++;
        }
        while (i < size);

        return array;
    }

    public void printArray(int[] array) {
        for(int number : array) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
