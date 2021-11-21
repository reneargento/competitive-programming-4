package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/11/21.
 */
public class Dice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String dice1String = FastReader.next();
            String dice2String = FastReader.next();

            boolean areTheSame = areTheSame(dice1String, dice2String);
            if (areTheSame) {
                outputWriter.printLine("Equal");
            } else {
                outputWriter.printLine("Not Equal");
            }
        }
        outputWriter.flush();
    }

    private static boolean areTheSame(String dice1, String dice2) {
        char[] dice1Char = dice1.toCharArray();
        char[] dice2Char = dice2.toCharArray();

        for (int i = 0; i < 4; i++) {
            dice1Char = rotateHorizontally(dice1Char);

            if (areTheSame(dice1Char, dice2Char)) {
                return true;
            }

            for (int j = 0; j < 4; j++) {
                dice1Char = rotateVertically(dice1Char);

                if (areTheSame(dice1Char, dice2Char)) {
                    return true;
                }

                for (int k = 0; k < 4; k++) {
                    dice1Char = rotateHorizontally(dice1Char);

                    if (areTheSame(dice1Char, dice2Char)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static char[] rotateHorizontally(char[] dice) {
        char[] rotatedDice = new char[6];
        rotatedDice[0] = dice[0];
        rotatedDice[1] = dice[1];
        rotatedDice[2] = dice[3];
        rotatedDice[3] = dice[4];
        rotatedDice[4] = dice[5];
        rotatedDice[5] = dice[2];
        return rotatedDice;
    }

    private static char[] rotateVertically(char[] dice) {
        char[] rotatedDice = new char[6];
        rotatedDice[0] = dice[2];
        rotatedDice[1] = dice[4];
        rotatedDice[2] = dice[1];
        rotatedDice[3] = dice[3];
        rotatedDice[4] = dice[0];
        rotatedDice[5] = dice[5];
        return rotatedDice;
    }

    private static boolean areTheSame(char[] dice1, char[] dice2) {
        for (int i = 0; i < dice1.length; i++) {
            if (dice1[i] != dice2[i]) {
                return false;
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
