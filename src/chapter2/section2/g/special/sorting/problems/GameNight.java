package chapter2.section2.g.special.sorting.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/03/21.
 */
public class GameNight {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int peopleNumber = FastReader.nextInt();
        String people = FastReader.next();
        String duplicatePeople = people + people;

        int[] frequencies = countFrequencies(people);
        int[][] prefixSums = computePrefixSums(duplicatePeople);

        int minimumMoves = getMinimumMoves(frequencies, prefixSums, peopleNumber);
        System.out.println(minimumMoves);
    }

    private static int[] countFrequencies(String people) {
        int[] frequencies = new int[3];

        for (int i = 0; i < people.length(); i++) {
            switch (people.charAt(i)) {
                case 'A': frequencies[0]++; break;
                case 'B': frequencies[1]++; break;
                case 'C': frequencies[2]++;
            }
        }
        return frequencies;
    }

    private static int[][] computePrefixSums(String people) {
        int[][] prefixSums = new int[3][people.length() + 1];

        for (int i = 1; i <= people.length(); i++) {
            int groupIndex = people.charAt(i - 1) - 'A';

            for (int group = 0; group < 3; group++) {
                int addFrequency = groupIndex == group ? 1 : 0;
                prefixSums[group][i] = prefixSums[group][i - 1] + addFrequency;
            }
        }
        return prefixSums;
    }

    private static int getMinimumMoves(int[] frequencies, int[][] prefixSums, int peopleNumber) {
        int minimumMovesABC = getMinimumMoves(frequencies, prefixSums, peopleNumber, true);
        int minimumMovesACB = getMinimumMoves(frequencies, prefixSums, peopleNumber, false);
        return Math.min(minimumMovesABC, minimumMovesACB);
    }

    private static int getMinimumMoves(int[] frequencies, int[][] prefixSums, int peopleNumber, boolean checkBSecond) {
        int minimumMoves = Integer.MAX_VALUE;
        int lastIndexToCheck = prefixSums[0].length - peopleNumber;
        int secondGroup = checkBSecond ? 1 : 2;
        int thirdGroup = checkBSecond ? 2 : 1;

        for (int i = 1; i <= lastIndexToCheck; i++) {
            int aGroupEndIndex = i + frequencies[0] - 1;
            int quantityA = prefixSums[0][aGroupEndIndex] - prefixSums[0][i - 1];

            int secondGroupStartIndex = i + frequencies[0];
            int secondGroupEndIndex = secondGroupStartIndex + frequencies[secondGroup] - 1;
            int quantitySecondGroup = prefixSums[secondGroup][secondGroupEndIndex]
                    - prefixSums[secondGroup][secondGroupStartIndex - 1];

            int thirdGroupStartIndex = secondGroupStartIndex + frequencies[secondGroup];
            int thirdGroupEndIndex = thirdGroupStartIndex + frequencies[thirdGroup] - 1;
            int quantityThirdGroup = prefixSums[thirdGroup][thirdGroupEndIndex]
                    - prefixSums[thirdGroup][thirdGroupStartIndex - 1];

            int movesA = frequencies[0] - quantityA;
            int movesSecondGroup = frequencies[secondGroup] - quantitySecondGroup;
            int movesThirdGroup = frequencies[thirdGroup] - quantityThirdGroup;
            int totalMoves = movesA + movesSecondGroup + movesThirdGroup;

            minimumMoves = Math.min(minimumMoves, totalMoves);
        }
        return minimumMoves;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
