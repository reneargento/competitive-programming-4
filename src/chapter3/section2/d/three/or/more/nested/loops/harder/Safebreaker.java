package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class Safebreaker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int guesses = FastReader.nextInt();
            List<String> codes = generateAllCodes();

            for (int i = 0; i < guesses; i++) {
                String guess = FastReader.next();
                String result = FastReader.next();
                int correctLocation = Character.getNumericValue(result.charAt(0));
                int correctValues = Character.getNumericValue(result.charAt(2));

                codes = filter(codes, guess, correctLocation, correctValues);
            }

            if (codes.size() == 1) {
                outputWriter.printLine(codes.get(0));
            } else if (codes.isEmpty()) {
                outputWriter.printLine("impossible");
            } else {
                outputWriter.printLine("indeterminate");
            }
        }
        outputWriter.flush();
    }

    private static List<String> generateAllCodes() {
        List<String> codes = new ArrayList<>();

        for (int i = 0; i <= 9999; i++) {
            StringBuilder code = new StringBuilder(String.valueOf(i));
            while (code.length() < 4) {
                code.insert(0, "0");
            }
            codes.add(code.toString());
        }
        return codes;
    }

    private static List<String> filter(List<String> codes, String guess, int correctLocation, int correctValues) {
        List<String> filteredSet = new ArrayList<>();

        for (String code : codes) {
            int okLocation = 0;
            int okValues = 0;
            boolean[] checked = new boolean[4];

            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == guess.charAt(i)) {
                    okLocation++;
                    checked[i] = true;
                } else {
                    for (int j = 0; j < code.length(); j++) {
                        if (code.charAt(j) == guess.charAt(i)
                                && code.charAt(j) != guess.charAt(j)
                                && !checked[j]) {
                            checked[j] = true;
                            okValues++;
                            break;
                        }
                    }
                }
            }

            if (okLocation == correctLocation && okValues == correctValues) {
                filteredSet.add(code);
            }
        }
        return filteredSet;
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
