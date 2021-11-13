package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/11/21.
 */
public class Ecosystem {

    private static class Cycle implements Comparable<Cycle> {
        int species1;
        int species2;
        int species3;

        public Cycle(int species1, int species2, int species3) {
            this.species1 = species1;
            this.species2 = species2;
            this.species3 = species3;
        }

        @Override
        public int compareTo(Cycle other) {
            if (species1 != other.species1) {
                return Integer.compare(species1, other.species1);
            }
            if (species2 != other.species2) {
                return Integer.compare(species2, other.species2);
            }
            return Integer.compare(species3, other.species3);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int species = Integer.parseInt(line);

            int[][] chain = new int[species][species];
            for (int row = 0; row < chain.length; row++) {
                for (int column = 0; column < chain[0].length; column++) {
                    chain[row][column] = FastReader.nextInt();
                }
            }

            List<Cycle> cycles = compute3Cycles(chain);
            for (Cycle cycle : cycles) {
                outputWriter.printLine(String.format("%d %d %d", cycle.species1, cycle.species2,
                        cycle.species3));
            }
            outputWriter.printLine(String.format("total:%d", cycles.size()));
            outputWriter.printLine();

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Cycle> compute3Cycles(int[][] chain) {
        List<Cycle> cycles = new ArrayList<>();

        for (int i = 0; i < chain.length; i++) {
            for (int j = 0; j < chain.length; j++) {
                for (int k = 0; k < chain.length; k++) {
                    if (chain[i][j] == 1 && chain[j][k] == 1 && chain[k][i] == 1) {
                        Cycle cycle = new Cycle(i + 1, j + 1, k + 1);
                        if (isValidCycle(cycle)) {
                            cycles.add(cycle);
                        }
                    }
                }
            }
        }
        Collections.sort(cycles);
        return cycles;
    }

    private static boolean isValidCycle(Cycle cycle) {
        return (cycle.species1 < cycle.species2 && cycle.species2 < cycle.species3) ||
                (cycle.species1 > cycle.species2 && cycle.species2 > cycle.species3);
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
