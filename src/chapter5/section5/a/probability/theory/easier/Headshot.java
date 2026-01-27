package chapter5.section5.a.probability.theory.easier;

import java.io.*;

/**
 * Created by Rene Argento on 17/01/26.
 */
public class Headshot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String digits = FastReader.getLine();
        while (digits != null) {
            String move = decideMove(digits);
            outputWriter.printLine(move);
            digits = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String decideMove(String digits) {
        int empty = 0;
        int bullet = 0;
        int emptyAfterShot = 0;
        int bulletAfterShot = 0;

        for (int i = 0; i < digits.length(); i++) {
            boolean canBeAfterShot = false;
            int previousIndex;
            if (i == 0) {
                previousIndex = digits.length() - 1;
            } else {
                previousIndex = i - 1;
            }

            if (digits.charAt(previousIndex) == '0') {
                canBeAfterShot = true;
            }

            char digit = digits.charAt(i);
            if (digit == '0') {
                empty++;
                if (canBeAfterShot) {
                    emptyAfterShot++;
                }
            } else {
                bullet++;
                if (canBeAfterShot) {
                    bulletAfterShot++;
                }
            }
        }

        double probabilityShoot = emptyAfterShot / (double) bulletAfterShot;
        double probabilityRotate = empty / (double) bullet;
        if (probabilityShoot == probabilityRotate) {
            return "EQUAL";
        }
        if (probabilityRotate > probabilityShoot) {
            return "ROTATE";
        }
        return "SHOOT";
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