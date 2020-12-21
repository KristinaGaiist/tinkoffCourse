package unit1;

import java.util.Random;

public class Task3 {

    public int[][] fullArrayByRandomNumbers() {
        int arrayLineSize = 5;
        int arrayColumnSize = 8;
        Random random = new Random();
        int[][] array = new int[arrayLineSize][arrayColumnSize];

        for (int i = 0; i < arrayLineSize; i++) {
            for (int j = 0; j < arrayColumnSize; j++) {
                array[i][j] = random.nextInt(1000);
            }
        }

        return array;
    }

    public int[][] fullArrayByMaxAndMinNumbers(int[][] array) {
        int arrayLineSize = array.length;
        int[][] resultArray = new int[arrayLineSize][2];

        for (int i = 0; i < arrayLineSize; i++) {
            int maxNumber = array[i][0];
            int minNumber = array[i][0];

            for (int j = 0; j < array[i].length; j++) {
                int arrayNumber = array[i][j];
                if (arrayNumber > maxNumber) {
                    maxNumber = arrayNumber;
                }
                if (arrayNumber < minNumber) {
                    minNumber = arrayNumber;
                }
            }

            resultArray[i][0] = minNumber;
            resultArray[i][1] = maxNumber;
        }

        return resultArray;
    }


    public void printArray(int[][] array) {
        for (int[] line : array) {
            for (int number : line) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
