package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class ClosingTheLoop {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int segments = FastReader.nextInt();
            List<Integer> blueSegments = new ArrayList<>();
            List<Integer> redSegments = new ArrayList<>();

            for (int i = 0; i < segments; i++) {
                String segment = FastReader.next();
                int colorIndex = segment.length() - 1;
                int length = Integer.parseInt(segment.substring(0, colorIndex));

                if (segment.charAt(colorIndex) == 'B') {
                    blueSegments.add(length);
                } else {
                    redSegments.add(length);
                }
            }

            int maximumLength = getMaximumLoopLength(blueSegments, redSegments);
            System.out.printf("Case #%d: %d\n", t, maximumLength);
        }
    }

    private static int getMaximumLoopLength(List<Integer> blueSegments, List<Integer> redSegments) {
        int maximumLength = 0;

        blueSegments.sort(Collections.reverseOrder());
        redSegments.sort(Collections.reverseOrder());

        int segmentsOfEachColor = Math.min(blueSegments.size(), redSegments.size());
        for (int i = 0; i < segmentsOfEachColor; i++) {
            maximumLength += blueSegments.get(i);
            maximumLength += redSegments.get(i);
        }
        maximumLength -= segmentsOfEachColor * 2;

        return maximumLength;
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
