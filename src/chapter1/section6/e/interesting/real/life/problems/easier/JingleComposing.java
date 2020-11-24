package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class JingleComposing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Double> durations = getDurations();
        String line = scanner.next();

        while (!line.equals("*")){
            String[] notes = line.substring(1, line.length() - 1).split("/");
            int rightDurations = countRightDurations(notes, durations);
            System.out.println(rightDurations);

            line = scanner.next();
        }
    }

    private static Map<Character, Double> getDurations() {
        Map<Character, Double> durations = new HashMap<>();
        durations.put('W', 1.0);
        durations.put('H', 0.5);
        durations.put('Q', 0.25);
        durations.put('E', 0.125);
        durations.put('S', 0.0625);
        durations.put('T', 0.03125);
        durations.put('X', 0.015625);
        return durations;
    }

    private static int countRightDurations(String[] notes, Map<Character, Double> durations) {
        int rightDurations = 0;

        for (String note : notes) {
            double sum = 0;

            for (int i = 0; i < note.length(); i++) {
                sum += durations.get(note.charAt(i));
            }

            if (sum == 1) {
                rightDurations++;
            }
        }
        return rightDurations;
    }

}
