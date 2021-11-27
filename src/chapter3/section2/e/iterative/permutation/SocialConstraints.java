package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/11/21.
 */
public class SocialConstraints {

    private static class Constraint {
        int person1;
        int person2;
        int distance;
        boolean farAway;

        public Constraint(int person1, int person2, int distance) {
            this.person1 = person1;
            this.person2 = person2;
            this.distance = Math.abs(distance);
            farAway = distance > 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int group = FastReader.nextInt();
        int constraintsNumber = FastReader.nextInt();

        while (group != 0 || constraintsNumber != 0) {
            Constraint[] constraints = new Constraint[constraintsNumber];
            for (int i = 0; i < constraints.length; i++) {
                constraints[i] = new Constraint(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            int validArrangements = countValidArrangements(group, constraints);
            outputWriter.printLine(validArrangements);

            group = FastReader.nextInt();
            constraintsNumber = FastReader.nextInt();
        }

        outputWriter.flush();
    }

    private static int countValidArrangements(int group, Constraint[] constraints) {
        int[] permutation = new int[group];
        return countValidArrangements(group, constraints, 0, 0, permutation);
    }

    private static int countValidArrangements(int group, Constraint[] constraints, int index,
                                              int mask, int[] permutation) {
        if (mask == (1 << group) - 1) {
            if (isValidArrangement(permutation, constraints)) {
                return 1;
            } else {
                return 0;
            }
        }
        int validArrangements = 0;

        for (int i = 0; i < group; i++) {
            if ((mask & (1 << i)) == 0) {
                permutation[index] = i;
                int nextMask = mask | (1 << i);
                validArrangements += countValidArrangements(group, constraints, index + 1, nextMask, permutation);
            }
        }
        return validArrangements;
    }

    private static boolean isValidArrangement(int[] permutation, Constraint[] constraints) {
        for (Constraint constraint : constraints) {
            int person1Index = -1;
            int person2Index = -1;

            for (int i = 0; i < permutation.length; i++) {
                if (permutation[i] == constraint.person1) {
                    person1Index = i;
                } else if (permutation[i] == constraint.person2) {
                    person2Index = i;
                }
            }
            int distance = Math.abs(person1Index - person2Index);

            if (constraint.farAway) {
                if (distance > constraint.distance) {
                    return false;
                }
            } else {
                if (distance < constraint.distance) {
                    return false;
                }
            }

        }
        return true;
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
