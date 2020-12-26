package chapter1.section6.p.time.waster.problems.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/12/20.
 */
public class ADigitalSatireOfDigitalAge {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            char[][] balance = new char[7][18];
            int leftWeight = 0;
            int rightWeight = 0;

            char[][] leftBalance = new char[5][8];
            int leftBalanceRowIndex = 0;
            char[][] rightBalance = new char[5][8];
            int rightBalanceRowIndex = 0;
            boolean leftBalanceStarted = false;
            boolean rightBalanceStarted = false;

            for (int row = 0; row < balance.length; row++) {
                String rowData = FastReader.getLine();
                if (!leftBalanceStarted && rowData.charAt(3) == '/') {
                    leftBalanceStarted = true;
                }
                if (leftBalanceStarted && leftBalanceRowIndex < leftBalance.length) {
                    leftBalance[leftBalanceRowIndex++] = rowData.substring(0, 8).toCharArray();
                }

                if (!rightBalanceStarted && rowData.charAt(13) == '/') {
                    rightBalanceStarted = true;
                }
                if (rightBalanceStarted && rightBalanceRowIndex < rightBalance.length) {
                    rightBalance[rightBalanceRowIndex++] = rowData.substring(10).toCharArray();
                }

                for (int column = 0; column < balance[0].length; column++) {
                    char symbol = rowData.charAt(column);
                    if (Character.isLetter(symbol)) {
                        int weight = getWeight(symbol);
                        if (column < 8) {
                            leftWeight += weight;
                        } else {
                            rightWeight += weight;
                        }
                    }
                    balance[row][column] = symbol;
                }
            }
            FastReader.getLine();

            boolean isLeftHeavier = balance[balance.length - 1][0] == '\\';
            boolean isRightHeavier = balance[balance.length - 1][balance[0].length - 1] == '/';

            outputWriter.printLine("Case " + t + ":");
            if ((isLeftHeavier && leftWeight > rightWeight)
                    || (isRightHeavier && rightWeight > leftWeight)
                    || (!isLeftHeavier && !isRightHeavier && leftWeight == rightWeight)) {
                outputWriter.printLine("The figure is correct.");
            } else {
                if (leftWeight == rightWeight) {
                    balance[0] = "........||........".toCharArray();
                    addSection(balance, leftBalance, 1, 0);
                    addSection(balance, rightBalance, 1, 10);
                    balance[balance.length - 1] = "........||........".toCharArray();
                } else if (leftWeight < rightWeight) {
                    clearSection(balance, 0, 1, 10, balance[0].length - 1);
                    clearSection(balance, 5, 6, 0, 7);
                    addSection(balance, leftBalance, 0, 0);
                    addSection(balance, rightBalance, 2, 10);
                } else {
                    clearSection(balance, 0, 1, 0, 7);
                    clearSection(balance, 5, 6, 10, balance[0].length - 1);
                    addSection(balance, leftBalance, 2, 0);
                    addSection(balance, rightBalance, 0, 10);
                }
                printBalance(balance, outputWriter);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printBalance(char[][] balance, OutputWriter outputWriter) {
        for (int r = 0; r < balance.length; r++) {
            for (int c = 0; c < balance[0].length; c++) {
                outputWriter.print(balance[r][c]);
            }
            outputWriter.printLine();
        }
    }

    private static void clearSection(char[][] balance, int startRow, int endRow, int startColumn, int endColumn) {
        for (int r = startRow; r <= endRow; r++) {
            for (int c = startColumn; c <= endColumn; c++) {
                balance[r][c] = '.';
            }
        }
    }

    private static void addSection(char[][] balance, char[][] section, int startRow, int startColumn) {
        for (int r = startRow; r < startRow + 5; r++) {
            for (int c = startColumn; c < startColumn + 8; c++) {
                balance[r][c] = section[r - startRow][c - startColumn];
            }
        }
    }

    private static int getWeight(char character) {
        int setBits = getSetBits(character);
        int totalBits = (int) (Math.log(character) / Math.log(2));
        if (Math.pow(2, totalBits) != character) {
            totalBits++;
        }
        int unsetBits = totalBits - setBits;
        return setBits * 500 + unsetBits * 250;
    }

    private static int getSetBits(int value) {
        int setBits = 0;
        while (value > 0) {
            value &= (value - 1);
            setBits++;
        }
        return setBits;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
