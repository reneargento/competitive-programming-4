package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/06/2026.
 */
public class FifthBankOfSwampCounty {

    private static class Check implements Comparable<Check> {
        String date;
        int number;
        double amount;

        public Check(String date, int number, double amount) {
            this.date = date;
            this.number = number;
            this.amount = amount;
        }

        @Override
        public int compareTo(Check other) {
            return Integer.compare(number, other.number);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine().trim();
            List<Check> checks = new ArrayList<>();

            while (line != null && !line.isEmpty()) {
                String[] data = line.split("\\s+");
                checks.add(new Check(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2])));
                line = FastReader.getLine();
            }
            if (t > 0) {
                outputWriter.printLine();
            }
            printCheckSummary(checks, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printCheckSummary(List<Check> checks, OutputWriter outputWriter) {
        Collections.sort(checks);
        Set<Integer> printedChecks = new HashSet<>();
        String format = "%4d%s %9.2f %6s";
        int itemsPerColumn = (int) Math.ceil(checks.size() / 3.0);

        for (int i = 0; i < checks.size(); i++) {
            for (int j = i; j < checks.size(); j += itemsPerColumn) {
                Check check = checks.get(j);
                if (printedChecks.contains(check.number)) {
                    if (j != i) {
                        outputWriter.printLine();
                    }
                    return;
                }

                if (j != i) {
                    outputWriter.print("   ");
                }
                String outOfOrderCheck = " ";
                if (j > 0) {
                    outOfOrderCheck = getOutOfOrderSign(checks.get(j - 1), check);
                }
                printCheck(format, check, outOfOrderCheck, outputWriter);
                printedChecks.add(check.number);
            }
            outputWriter.printLine();
        }
    }

    private static String getOutOfOrderSign(Check check1, Check check2) {
        return check2.number == check1.number + 1 ? " " : "*";
    }

    private static void printCheck(String format, Check check, String outOfOrder, OutputWriter outputWriter) {
        outputWriter.print(String.format(format, check.number, outOfOrder, check.amount, check.date));
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

        public void flush() {
            writer.flush();
        }
    }
}