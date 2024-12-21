package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 19/12/24.
 */
public class IntergalacticMortgage {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        long principal = inputReader.nextInt();
        long monthlyPayment = inputReader.nextInt();
        int yearsToPay = inputReader.nextInt();
        double interestRate = inputReader.nextDouble();

        while (principal != 0 || monthlyPayment != 0 || yearsToPay != 0 || interestRate != 0) {
            boolean canPayMortgage = canPayMortgage(principal, monthlyPayment, yearsToPay, interestRate);
            outputWriter.printLine(canPayMortgage ? "YES" : "NO");

            principal = inputReader.nextInt();
            monthlyPayment = inputReader.nextInt();
            yearsToPay = inputReader.nextInt();
            interestRate = inputReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static boolean canPayMortgage(long principal, long monthlyPayment, int yearsToPay, double interestRate) {
        if (interestRate == 0) {
            return principal <= (12 * monthlyPayment) * yearsToPay;
        }

        double monthlyInterest = (1 + (interestRate / 100 / 12.0));
        double monthlyInterestPowTime = Math.pow(monthlyInterest, 12 * yearsToPay);
        double totalToPay = principal * monthlyInterestPowTime;
        double paymentsOverTime = monthlyPayment * (monthlyInterestPowTime - 1) / (interestRate / 100 / 12.0);
        return totalToPay - paymentsOverTime <= 0;
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
