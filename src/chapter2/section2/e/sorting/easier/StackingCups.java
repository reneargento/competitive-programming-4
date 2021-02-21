package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class StackingCups {

    private static class Cup implements Comparable<Cup> {
        int radius;
        String color;

        public Cup(int radius, String color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public int compareTo(Cup other) {
            return radius - other.radius;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int cupsNumber = FastReader.nextInt();
        Cup[] cups = new Cup[cupsNumber];

        for (int i = 0; i < cups.length; i++) {
            String token1 = FastReader.next();
            String token2 = FastReader.next();
            int radius;
            String color;

            if (Character.isDigit(token1.charAt(0))) {
                radius = Integer.parseInt(token1) / 2;
                color = token2;
            } else {
                color = token1;
                radius = Integer.parseInt(token2);
            }
            cups[i] = new Cup(radius, color);
        }

        Arrays.sort(cups);
        for (Cup cup : cups) {
            System.out.println(cup.color);
        }
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
