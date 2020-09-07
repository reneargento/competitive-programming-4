package chapter1.section3.section4.exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task7 {

    public static void main(String[] args) {
        String letters = "ABCDEFGHIJ";
        List<String> permutations = new ArrayList<>();
        getPermutations("", letters, permutations);

        for (String permutation : permutations) {
            StringJoiner permutationDescription = new StringJoiner(" ");
            for (char character : permutation.toCharArray()) {
                permutationDescription.add(String.valueOf(character));
            }
            System.out.println(permutationDescription);
        }
    }

    // O(N!)
    private static void getPermutations(String prefix, String remaining, List<String> permutations) {
        if (remaining.length() == 0) {
            permutations.add(prefix);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            char removedChar = remaining.charAt(i);
            String start = remaining.substring(0, i);
            String end = remaining.substring(i + 1);

            getPermutations(prefix + removedChar, start + end, permutations);
        }

    }

}
