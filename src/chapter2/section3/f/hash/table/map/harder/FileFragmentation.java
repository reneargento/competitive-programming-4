package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/21.
 */
public class FileFragmentation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            List<String> fragments = new ArrayList<>();
            int totalLength = 0;

            String fragment = FastReader.getLine();
            while (fragment != null && !fragment.equals("")) {
                fragments.add(fragment);
                totalLength += fragment.length();
                fragment = FastReader.getLine();
            }

            int patternLength = totalLength / (fragments.size() / 2);
            String pattern = getPattern(fragments, patternLength);
            outputWriter.printLine(pattern);
        }
        outputWriter.flush();
    }

    private static String getPattern(List<String> fragments, int patternSize) {
        boolean[] matched = new boolean[fragments.size()];
        return getPattern(fragments, patternSize, matched, 0, null, 0);
    }

    private static String getPattern(List<String> fragments, int patternLength, boolean[] matched,
                                     int index, String targetPattern, int totalMatches) {
        if (totalMatches == fragments.size() / 2) {
            return targetPattern;
        } else if (index == fragments.size()) {
            return null;
        }

        if (matched[index]) {
            return getPattern(fragments, patternLength, matched, index + 1, targetPattern, totalMatches);
        }
        String currentFragment = fragments.get(index);
        for (int i = index + 1; i < fragments.size(); i++) {
            if (matched[i]) {
                continue;
            }
            if (currentFragment.length() + fragments.get(i).length() == patternLength) {
                String combinedFragments1 = currentFragment + fragments.get(i);
                String combinedFragments2 = fragments.get(i) + currentFragment;
                matched[index] = true;
                matched[i] = true;

                if (targetPattern == null) {
                    String patternResult = getPattern(fragments, patternLength, matched, index + 1, combinedFragments1, totalMatches + 1);
                    if (patternResult != null) {
                        return patternResult;
                    }

                    patternResult = getPattern(fragments, patternLength, matched, index + 1, combinedFragments2, totalMatches + 1);
                    if (patternResult != null) {
                        return patternResult;
                    }
                } else {
                    if (combinedFragments1.equals(targetPattern) || combinedFragments2.equals(targetPattern)) {
                        String patternResult = getPattern(fragments, patternLength, matched, index + 1, targetPattern, totalMatches + 1);
                        if (patternResult != null) {
                            return patternResult;
                        }
                    }
                }
                matched[index] = false;
                matched[i] = false;
            }
        }
        return null;
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
