package chapter3.section2.section1;

import java.util.*;

/**
 * Created by Rene Argento on 14/08/21.
 */
// Based on https://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
public class Exercise1 {

    private static List<List<Integer>> generateAllPermutations(int numbers) {
        Integer[] numbersArray = new Integer[numbers];
        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = i + 1;
        }

        List<List<Integer>> permutations = new ArrayList<>();
        generateAllPermutations(permutations, numbersArray, numbers);
        return permutations;
    }

    private static void generateAllPermutations(List<List<Integer>> permutations, Integer[] numbersArray,
                                                int elementsRemaining) {
        if (elementsRemaining == 1) {
            List<Integer> permutation = new ArrayList<>(Arrays.asList(numbersArray));
            permutations.add(permutation);
            return;
        }

        for (int i = 0; i < elementsRemaining; i++) {
            swap(numbersArray, i, elementsRemaining - 1);
            generateAllPermutations(permutations, numbersArray, elementsRemaining - 1);
            swap(numbersArray, i, elementsRemaining - 1);
        }
    }

    private static void swap(Integer[] numbersArray, int index1, int index2) {
        int aux = numbersArray[index1];
        numbersArray[index1] = numbersArray[index2];
        numbersArray[index2] = aux;
    }

    public static void main(String[] args) {
        List<List<Integer>> permutations = generateAllPermutations(4);
        System.out.println("Number of permutations: " + permutations.size() + " Expected: 24\n");

        System.out.println("Permutations");
        for (List<Integer> permutation : permutations) {
            for (int value : permutation) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
