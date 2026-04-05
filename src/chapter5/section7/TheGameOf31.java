package chapter5.section7;

import java.io.*;

/**
 * Created by Rene Argento on 04/04/26.
 */
public class TheGameOf31 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            char winner = computeWinner(line);
            outputWriter.printLine(line + " " + winner);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static char computeWinner(String digits) {
        int sum = computeCurrentSum(digits);
        int[] count = computeCardCount(digits);
        char player = digits.length() % 2 == 0 ? 'A' : 'B';
        char otherPlayer = player == 'A' ? 'B' : 'A';
        return canPlayerWin(sum, count) ? player : otherPlayer;
    }

    private static boolean canPlayerWin(int sum, int[] count) {
        if (sum == 31) {
            return false;
        }

        boolean canWin = false;
        for (int card = 1; card <= 6; card++) {
            if (count[card] < 4 && sum + card <= 31) {
                count[card]++;
                boolean result = !canPlayerWin(sum + card, count);
                if (result) {
                    canWin = true;
                    count[card]--;
                    break;
                }
                count[card]--;
            }
        }
        return canWin;
    }

    private static int computeCurrentSum(String digits) {
        int sum = 0;
        for (char c : digits.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return sum;
    }

    private static int[] computeCardCount(String digits) {
        int[] count = new int[7];

        for (char c : digits.toCharArray()) {
            int card = Character.getNumericValue(c);
            count[card]++;
        }
        return count;
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
