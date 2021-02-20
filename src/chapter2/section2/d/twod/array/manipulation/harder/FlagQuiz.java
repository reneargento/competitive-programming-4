package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 08/02/21.
 */
public class FlagQuiz {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        FastReader.getLine();
        int alternativesNumber = FastReader.nextInt();

        String[] firstAlternative = FastReader.getLine().split(", ");
        String[][] alternatives = new String[alternativesNumber][firstAlternative.length];
        alternatives[0] = firstAlternative;

        for (int i = 1; i < alternativesNumber; i++) {
            alternatives[i] = FastReader.getLine().split(", ");
        }

        List<Integer> amountOfChanges = computeAmountOfChanges(alternatives);
        int smallestAmountOfChanges = getSmallestAmountOfChanges(amountOfChanges);
        List<String> bestAnswers = getBestAnswers(alternatives, amountOfChanges, smallestAmountOfChanges);

        for (String answer : bestAnswers) {
            System.out.println(answer);
        }
    }

    private static List<Integer> computeAmountOfChanges(String[][] alternatives) {
        List<Integer> amountOfChanges = new ArrayList<>();
        int items = alternatives[0].length;

        for (int i = 0; i < alternatives.length; i++) {
            int maxChanges = 0;
            for (int j = 0; j < alternatives.length; j++) {
                if (i == j) {
                    continue;
                }
                int changes = 0;
                for (int k = 0; k < items; k++) {
                    if (!alternatives[i][k].equals(alternatives[j][k])) {
                        changes++;
                    }
                }
                maxChanges = Math.max(maxChanges, changes);
            }
            amountOfChanges.add(maxChanges);
        }
        return amountOfChanges;
    }

    private static int getSmallestAmountOfChanges(List<Integer> amountOfChanges) {
        int smallestAmountOfChanges = Integer.MAX_VALUE;
        for (int amount : amountOfChanges) {
            smallestAmountOfChanges = Math.min(smallestAmountOfChanges, amount);
        }
        return smallestAmountOfChanges;
    }

    private static List<String> getBestAnswers(String[][] alternatives, List<Integer> amountOfChanges,
                                               int smallestAmountOfChanges) {
        List<String> bestAnswers = new ArrayList<>();
        for (int i = 0; i < amountOfChanges.size(); i++) {
            if (amountOfChanges.get(i) == smallestAmountOfChanges) {
                String answer = composeAnswer(alternatives[i]);
                bestAnswers.add(answer);
            }
        }
        return bestAnswers;
    }

    private static String composeAnswer(String[] alternative) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String component : alternative) {
            joiner.add(component);
        }
        return joiner.toString();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
