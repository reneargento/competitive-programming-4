package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 14/12/24.
 */
public class FatAndOrial {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            double currentAverageGrade = inputReader.nextDouble();
            double desiredAverageGrade = inputReader.nextDouble();
            int disciplinesAttended = inputReader.nextInt();
            int disciplinesToAttend = inputReader.nextInt();

            double gradePerDisciplineNeeded = computeGradeNeeded(currentAverageGrade, desiredAverageGrade,
                    disciplinesAttended, disciplinesToAttend);
            outputWriter.print("Case #" + t + ": ");
            if (gradePerDisciplineNeeded == Double.POSITIVE_INFINITY) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(gradePerDisciplineNeeded);
            }
        }
        outputWriter.flush();
    }

    private static double computeGradeNeeded(double currentAverageGrade, double desiredAverageGrade,
                                             int disciplinesAttended, int disciplinesToAttend) {
        double totalGradeRequired = desiredAverageGrade * (disciplinesAttended + disciplinesToAttend);
        double totalGradeCurrent = currentAverageGrade * disciplinesAttended;
        double totalGradeNeeded = totalGradeRequired - totalGradeCurrent;
        double gradePerDisciplineNeeded = totalGradeNeeded / disciplinesToAttend;

        if (gradePerDisciplineNeeded < 0 || gradePerDisciplineNeeded > 10) {
            return Double.POSITIVE_INFINITY;
        }
        return roundValuePrecisionDigits(gradePerDisciplineNeeded, 2);
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public double nextDouble() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                res *= 10;
                res += c - '0';
                c = snext();
            }
            if (c == '.') {
                c = snext();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = snext();
                }
            }
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
