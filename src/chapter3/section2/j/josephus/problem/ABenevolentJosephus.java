package chapter3.section2.j.josephus.problem;

import java.io.*;

/**
 * Created by Rene Argento on 22/01/22.
 */
public class ABenevolentJosephus {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int players = Integer.parseInt(line);
            int money = computeMoney(players);
            outputWriter.printLine(money);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMoney(int players) {
        int money = 0;

        while (true) {
            int survivor = josephusSkip2(players);

            if (survivor != players) {
                money += players - survivor;
                players = survivor;
            } else {
                money += players * 2;
                break;
            }
        }
        return money;
    }

    // Special case of josephus with skip 2 can be solved by:
    // 1- Converting the circle size to binary
    // 2- Moving the most significant bit to the end
    // 3- Converting the bits to decimal. That is the survivor position.
    private static int josephusSkip2(int circleSize) {
        String bits = toBinary(circleSize);
        bits = bits.substring(1) + bits.charAt(0);
        return Integer.parseInt(bits, 2);
    }

    private static String toBinary(int number) {
        StringBuilder bits = new StringBuilder();
        while (number > 0) {
            int bit = number % 2;
            number /= 2;
            bits.append(bit);
        }
        return bits.reverse().toString();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
