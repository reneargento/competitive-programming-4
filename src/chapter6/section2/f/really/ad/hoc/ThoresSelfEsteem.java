package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/2026.
 */
public class ThoresSelfEsteem {

    private static final String THORE = "ThoreHusfeldt";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int namesNumber = FastReader.nextInt();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < namesNumber; i++) {
            String name = FastReader.next();
            names.add(name);

            if (name.equals(THORE)) {
                break;
            }
        }

        String result = checkScoreboard(names);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String checkScoreboard(List<String> names) {
        if (names.get(0).equals(THORE)) {
            return "Thore is awesome";
        }

        int smallestDifferentIndex = -1;
        for (int i = 0; i < names.size() - 1; i++) {
            String name = names.get(i);
            if (name.startsWith("ThoreHusfeld")) {
                return "Thore sucks";
            }

            if (THORE.startsWith(name)) {
                if (name.length() > smallestDifferentIndex) {
                    smallestDifferentIndex = name.length();
                }
                continue;
            }

            int endIndex = Math.min(name.length(), THORE.length());
            for (int j = 0; j < endIndex; j++) {
                if (name.charAt(j) != THORE.charAt(j)) {
                    if (j > smallestDifferentIndex) {
                        smallestDifferentIndex = j;
                    }
                    break;
                }
            }
        }
        return THORE.substring(0, smallestDifferentIndex + 1);
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

        public void flush() {
            writer.flush();
        }
    }
}