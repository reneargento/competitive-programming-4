package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 14/11/20.
 */
public class EbAltoSaxophonePlayer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String song = scanner.nextLine();
            int[] fingerPresses = new int[10];
            Set<Integer> previousFingers = null;

            for (int i = 0; i < song.length(); i++) {
                Set<Integer> fingerUsed = getFingersUsed(song.charAt(i));
                Set<Integer> currentFingers = fingerUsed;
                if (i != 0) {
                    currentFingers = setMinusSet(fingerUsed, previousFingers);
                }

                for (int finger : currentFingers) {
                    fingerPresses[finger - 1]++;
                }
                previousFingers = fingerUsed;
            }

            StringJoiner fingersDescription = new StringJoiner(" ");
            for (int finger : fingerPresses) {
                fingersDescription.add(String.valueOf(finger));
            }
            System.out.println(fingersDescription);
        }
    }

    private static Set<Integer> getFingersUsed(char note) {
        Set<Integer> fingers = new HashSet<>();

        switch (note) {
            case 'c':
                addRangeValuesToSet(fingers, 2, 4); addRangeValuesToSet(fingers, 7, 10); break;
            case 'd':
                addRangeValuesToSet(fingers, 2, 4); addRangeValuesToSet(fingers, 7, 9); break;
            case 'e':
                addRangeValuesToSet(fingers, 2, 4); addRangeValuesToSet(fingers, 7, 8); break;
            case 'f':
                addRangeValuesToSet(fingers, 2, 4); fingers.add(7); break;
            case 'g':
                addRangeValuesToSet(fingers, 2, 4); break;
            case 'a':
                addRangeValuesToSet(fingers, 2, 3); break;
            case 'b':
                fingers.add(2); break;
            case 'C':
                fingers.add(3); break;
            case 'D':
                addRangeValuesToSet(fingers, 1, 4); addRangeValuesToSet(fingers, 7, 9); break;
            case 'E':
                addRangeValuesToSet(fingers, 1, 4); addRangeValuesToSet(fingers, 7, 8); break;
            case 'F':
                addRangeValuesToSet(fingers, 1, 4); fingers.add(7); break;
            case 'G':
                addRangeValuesToSet(fingers, 1, 4); break;
            case 'A':
                addRangeValuesToSet(fingers, 1, 3); break;
            case 'B':
                addRangeValuesToSet(fingers, 1, 2); break;
        }
        return fingers;
    }

    private static void addRangeValuesToSet(Set<Integer> set, int start, int end) {
        for (int i = start; i <= end; i++) {
            set.add(i);
        }
    }

    private static Set<Integer> setMinusSet(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> resultSet = new HashSet<>(setA);
        for (int value : setB) {
            resultSet.remove(value);
        }
        return resultSet;
    }
}
