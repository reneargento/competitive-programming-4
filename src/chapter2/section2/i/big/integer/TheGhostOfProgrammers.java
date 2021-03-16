package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 16/03/21.
 */
public class TheGhostOfProgrammers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger firstYear = BigInteger.valueOf(2148);
        BigInteger[] frequencyYears = getFrequencyYears();
        String[] ghosts = getGhosts();
        int caseNumber = 1;

        String yearLine = FastReader.getLine();

        while (!yearLine.equals("0")) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }

            BigInteger year = new BigInteger(yearLine);
            outputWriter.printLine(yearLine);
            uncoverGhosts(firstYear, year, frequencyYears, ghosts, outputWriter);

            yearLine = FastReader.getLine();
            caseNumber++;
        }
        outputWriter.flush();
    }

    private static void uncoverGhosts(BigInteger firstYear, BigInteger year, BigInteger[] frequencyYears,
                                      String[] ghosts, OutputWriter outputWriter) {
        if (year.compareTo(firstYear) < 0) {
            outputWriter.printLine("No ghost will come in this year");
            return;
        }

        boolean anyGhostExists = false;
        BigInteger yearsPassed = year.subtract(firstYear);

        for (int i = 0; i < frequencyYears.length; i++) {
            if (isDivisible(yearsPassed, frequencyYears[i])) {
                outputWriter.printLine(ghosts[i]);
                anyGhostExists = true;
            }
        }

        if (isLeapYear(year)) {
            outputWriter.printLine("Ghost of K. M. Iftekhar!!!");
            anyGhostExists = true;
        }

        if (!anyGhostExists) {
            outputWriter.printLine("No ghost will come in this year");
        }
    }

    private static boolean isDivisible(BigInteger year, BigInteger value) {
        return year.mod(value).equals(BigInteger.ZERO);
    }

    private static boolean isLeapYear(BigInteger year) {
        return year.mod(BigInteger.valueOf(400)).equals(BigInteger.ZERO) ||
                (year.mod(BigInteger.valueOf(4)).equals(BigInteger.ZERO)
                        && !year.mod(BigInteger.valueOf(100)).equals(BigInteger.ZERO));
    }

    private static BigInteger[] getFrequencyYears() {
        return new BigInteger[] { BigInteger.valueOf(2),
                BigInteger.valueOf(5),
                BigInteger.valueOf(7),
                BigInteger.valueOf(11),
                BigInteger.valueOf(15),
                BigInteger.valueOf(20),
                BigInteger.valueOf(28),
                BigInteger.valueOf(36)
        };
    }

    private static String[] getGhosts() {
        return new String[] {
                "Ghost of Tanveer Ahsan!!!",
                "Ghost of Shahriar Manzoor!!!",
                "Ghost of Adrian Kugel!!!",
                "Ghost of Anton Maydell!!!",
                "Ghost of Derek Kisman!!!",
                "Ghost of Rezaul Alam Chowdhury!!!",
                "Ghost of Jimmy Mardell!!!",
                "Ghost of Monirul Hasan!!!"
        };
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
