package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 30/04/25.
 */
public class SimplySubsets {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            Set<Integer> set1 = readSet(line);
            line = FastReader.getLine();
            Set<Integer> set2 = readSet(line);

            String result = checkSets(set1, set2);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Set<Integer> readSet(String line) {
        Set<Integer> set = new HashSet<>();

        String[] tokens = line.split(" ");
        for (String token : tokens) {
            set.add(Integer.parseInt(token));
        }
        return set;
    }

    private static String checkSets(Set<Integer> set1, Set<Integer> set2) {
        int elementsContainedAInB = countElementsContained(set1, set2);
        if (elementsContainedAInB == set1.size() && elementsContainedAInB == set2.size()) {
            return "A equals B";
        }
        if (elementsContainedAInB == 0) {
            return "A and B are disjoint";
        }
        if (elementsContainedAInB == set1.size()) {
            return "A is a proper subset of B";
        }
        if (elementsContainedAInB == set2.size()) {
            return "B is a proper subset of A";
        }
        return "I'm confused!";
    }

    private static int countElementsContained(Set<Integer> set1, Set<Integer> set2) {
        int elementsContained = 0;
        for (int element : set1) {
            if (set2.contains(element)) {
                elementsContained++;
            }
        }
        return elementsContained;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
