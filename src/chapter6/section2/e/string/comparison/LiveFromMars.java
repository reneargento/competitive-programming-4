package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 29/06/2026.
 */
public class LiveFromMars {

    private static class Mutation implements Comparable<Mutation> {
        char mutantElement;
        char normalElement;

        public Mutation(char mutantElement, char normalElement) {
            this.mutantElement = mutantElement;
            this.normalElement = normalElement;
        }

        @Override
        public int compareTo(Mutation other) {
            return Character.compare(this.mutantElement, other.mutantElement);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int caseId = 1;
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            int length = Integer.parseInt(line);
            String dna1 = readDna(length);
            String dna2 = readDna(length);

            if (caseId > 1) {
                outputWriter.printLine();
            }
            List<Mutation> shortestMutation = computeShortestMutation(dna1.toCharArray(), dna2.toCharArray());
            if (shortestMutation == null) {
                outputWriter.printLine("NO");
            } else {
                outputWriter.printLine("YES");
                for (Mutation mutation : shortestMutation) {
                    outputWriter.printLine(mutation.mutantElement + " " + mutation.normalElement);
                }
            }
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String readDna(int length) throws IOException {
        StringBuilder dna = new StringBuilder();
        for (int i = 0; i < length; i++) {
            dna.append(FastReader.getLine());
        }
        return dna.toString();
    }

    private static List<Mutation> computeShortestMutation(char[] dna1, char[] dna2) {
        List<Mutation> shortestMutation = new ArrayList<>();

        while (true) {
            boolean mutationHappened = false;
            for (int i = 0; i < dna1.length; i++) {
                char element1 = dna1[i];
                char element2 = dna2[i];

                if (isMutantElement(element1) && isMutantElement(element2)) {
                    continue;
                }
                if (!isMutantElement(element1) && !isMutantElement(element2)) {
                    if (element1 != element2) {
                        return null;
                    } else {
                        continue;
                    }
                }

                if (isMutantElement(element1)) {
                    shortestMutation.add(new Mutation(element1, element2));
                    mutate(dna1, element1, element2);
                    mutate(dna2, element1, element2);
                    mutationHappened = true;
                } else if (isMutantElement(element2)) {
                    shortestMutation.add(new Mutation(element2, element1));
                    mutate(dna1, element2, element1);
                    mutate(dna2, element2, element1);
                    mutationHappened = true;
                }
            }

            if (!mutationHappened) {
                break;
            }
        }

        if (areEquivalent(dna1, dna2)) {
            Collections.sort(shortestMutation);
            return shortestMutation;
        } else {
            return null;
        }
    }

    private static boolean areEquivalent(char[] dna1, char[] dna2) {
        for (int i = 0; i < dna1.length; i++) {
            char element1 = dna1[i];
            char element2 = dna2[i];

            if (isMutantElement(element1) && isMutantElement(element2)) {
                continue;
            }
            if (element1 != element2) {
                return false;
            }
        }
        return true;
    }

    private static void mutate(char[] dna, char mutantElement, char normalElement) {
        for (int i = 0; i < dna.length; i++) {
            if (dna[i] == mutantElement) {
                dna[i] = normalElement;
            }
        }
    }

    private static boolean isMutantElement(char element) {
        return Character.isDigit(element);
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