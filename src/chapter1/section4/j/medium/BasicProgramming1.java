package chapter1.section4.j.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class BasicProgramming1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[scanner.nextInt()];
        int operation = scanner.nextInt();

        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }

        switch (operation) {
            case 1:
                System.out.println(7);
                break;
            case 2:
                compare(array);
                break;
            case 3:
                printMedian(array);
                break;
            case 4:
                printSum(array, false);
                break;
            case 5:
                printSum(array, true);
                break;
            case 6:
                printLetters(array);
                break;
            case 7:
                doJumps(array);
                break;
        }

    }

    private static void compare(int[] array) {
        if (array[0] > array[1]) {
            System.out.println("Bigger");
        } else if (array[0] == array[1]) {
            System.out.println("Equal");
        } else {
            System.out.println("Smaller");
        }
    }

    private static void printMedian(int[] array) {
        int[] newArray = { array[0], array[1], array[2] };
        Arrays.sort(newArray);
        System.out.println(newArray[1]);
    }

    private static void printSum(int[] array, boolean onlyEven) {
        long sum = 0;
        for (int value : array) {
            if (!onlyEven || value % 2 == 0) {
                sum += value;
            }
        }
        System.out.println(sum);
    }

    private static void printLetters(int[] array) {
        StringBuilder letters = new StringBuilder();

        for (int value : array) {
            char charValue = (char) ((value % 26) + 'a');
            letters.append(charValue);
        }
        System.out.println(letters);
    }

    private static void doJumps(int[] array) {
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        int index = 0;

        while (true) {
            int nextIndex = array[index];
            if (nextIndex < 0 || nextIndex >= array.length) {
                System.out.println("Out");
                break;
            } else if (nextIndex == array.length - 1) {
                System.out.println("Done");
                break;
            } else if (visited.contains(nextIndex)) {
                System.out.println("Cyclic");
                break;
            } else {
                visited.add(nextIndex);
                index = nextIndex;
            }
        }
    }

}
