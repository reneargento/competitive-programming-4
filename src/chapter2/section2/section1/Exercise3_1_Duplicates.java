package chapter2.section2.section1;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 08/01/21.
 */
public class Exercise3_1_Duplicates {

    public static void main(String[] args) {
        int[] array1 = { 1, 2, 3, 4, 5 };
        System.out.print("Has duplicates 1: ");
        System.out.println(hasDuplicates(array1) ? "yes" : "no");
        System.out.println("Expected: no");

        int[] array2 = { 1, 2, 3, 4, 5, 2 };
        System.out.print("\nHas duplicates 2: ");
        System.out.println(hasDuplicates(array2) ? "yes" : "no");
        System.out.println("Expected: yes");
    }

    private static boolean hasDuplicates(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int value : array) {
            if (set.contains(value)) {
                return true;
            }
            set.add(value);
        }
        return false;
    }
}
