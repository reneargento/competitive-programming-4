package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/01/22.
 */
public class RationalSpiral {

    private static class RationalNumber {
        int numerator;
        int denominator;

        public RationalNumber(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final int OFFSET = 750;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        List<RationalNumber> rationalNumbersList = computeRationalNumbers();

        while (line != null) {
            int index = Integer.parseInt(line.trim());
            RationalNumber rationalNumber = rationalNumbersList.get(index);
            outputWriter.printLine(String.format("%d / %d", rationalNumber.numerator,
                    rationalNumber.denominator));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<RationalNumber> computeRationalNumbers() {
        List<RationalNumber> rationalNumbersList = new ArrayList<>();
        boolean[][] rationalNumbersVisited = new boolean[1500][1500];

        int direction = 0;
        int range = 1;
        boolean updateRange = false;
        Point point = new Point(0, 0);

        while (rationalNumbersList.size() <= 510000) {
            for (int i = 0; i < range; i++) {
                Point nextPoint = computeNextPoint(point, direction);
                Point formattedPoint = new Point(nextPoint.x, nextPoint.y);

                if (nextPoint.y < 0 && nextPoint.x < 0) {
                    formattedPoint.y = Math.abs(formattedPoint.y);
                    formattedPoint.x = Math.abs(formattedPoint.x);
                } else if (nextPoint.y >= 0 && nextPoint.x < 0) {
                    formattedPoint.y *= -1;
                    formattedPoint.x = Math.abs(formattedPoint.x);
                }

                RationalNumber rationalNumber = generateValidRationalNumber(formattedPoint, rationalNumbersVisited);
                if (rationalNumber != null) {
                    rationalNumbersVisited[rationalNumber.numerator + OFFSET][rationalNumber.denominator + OFFSET] = true;
                    rationalNumbersList.add(rationalNumber);
                }
                point = nextPoint;
            }

            if (!updateRange) {
                updateRange = true;
            } else {
                updateRange = false;
                range++;
            }
            direction = (direction + 1) % 4;
        }
        return rationalNumbersList;
    }

    private static Point computeNextPoint(Point point, int direction) {
        Point nextPoint;

        switch (direction) {
            case 0: nextPoint = new Point(point.x, point.y + 1); break;
            case 1: nextPoint = new Point(point.x + 1, point.y); break;
            case 2: nextPoint = new Point(point.x, point.y - 1); break;
            default: nextPoint = new Point(point.x - 1, point.y);
        }
        return nextPoint;
    }

    private static RationalNumber generateValidRationalNumber(Point point, boolean[][] rationalNumbersVisited) {
        if (point.x == 0) {
            return null;
        }

        int gcd = gcd(Math.abs(point.y), Math.abs(point.x));
        int numerator = point.y / gcd;
        int denominator = point.x / gcd;

        RationalNumber rationalNumber = new RationalNumber(numerator, denominator);

        if (rationalNumbersVisited[rationalNumber.numerator + OFFSET][rationalNumber.denominator + OFFSET]) {
            return null;
        }
        return rationalNumber;
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
