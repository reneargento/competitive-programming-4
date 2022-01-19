package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/01/22.
 */
public class Robotopia {

    private static class Robots {
        int type1;
        int type2;

        public Robots(int type1, int type2) {
            this.type1 = type1;
            this.type2 = type2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int type1Legs = FastReader.nextInt();
            int type1Arms = FastReader.nextInt();
            int type2Legs = FastReader.nextInt();
            int type2Arms = FastReader.nextInt();
            int totalLegs = FastReader.nextInt();
            int totalArms = FastReader.nextInt();

            Robots robots = computeRobotsQuantity(type1Legs, type1Arms, type2Legs, type2Arms, totalLegs, totalArms);
            if (robots != null) {
                outputWriter.printLine(String.format("%d %d", robots.type1, robots.type2));
            } else {
                outputWriter.printLine("?");
            }
        }
        outputWriter.flush();
    }

    private static Robots computeRobotsQuantity(int type1Legs, int type1Arms, int type2Legs, int type2Arms,
                                                int totalLegs, int totalArms) {
        int type1Robots = 0;
        int type2Robots = 0;

        int minMembers = Math.min(totalLegs, totalArms);
        boolean found = false;

        for (int type1 = 1; type1 <= minMembers; type1++) {
            int type1RobotLegs = type1Legs * type1;
            int type1RobotArms = type1Arms * type1;

            int type2RobotLegs = totalLegs - type1RobotLegs;
            int type2RobotArms = totalArms - type1RobotArms;

            if (type2RobotLegs % type2Legs == 0
                    && type2RobotArms % type2Arms == 0) {
                int possibleType2Robots = type2RobotLegs / type2Legs;
                if (possibleType2Robots <= 0
                        || type1RobotLegs + possibleType2Robots * type2Legs != totalLegs
                        || type1RobotArms + possibleType2Robots * type2Arms != totalArms) {
                    continue;
                }

                if (found) {
                    return null;
                }
                type1Robots = type1;
                type2Robots = possibleType2Robots;
                found = true;
            }
        }

        if (!found) {
            return null;
        }
        return new Robots(type1Robots, type2Robots);
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
