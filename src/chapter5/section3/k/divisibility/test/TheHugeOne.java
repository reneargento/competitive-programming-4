package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/10/25.
 */
public class TheHugeOne {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String number = FastReader.next();
            int[] set = new int[FastReader.nextInt()];
            for (int i = 0; i < set.length; i++) {
                set[i] = FastReader.nextInt();
            }

            boolean isWonderful = isWonderful(number, set);
            outputWriter.print(number + " - ");
            if (isWonderful) {
                outputWriter.printLine("Wonderful.");
            } else {
                outputWriter.printLine("Simple.");
            }
        }
        outputWriter.flush();
    }

    private static boolean isWonderful(String number, int[] set) {
        for (int divisor : set) {
            switch (divisor) {
                case 2: if (!isDivisibleBy2(number)) {
                    return false;
                }
                break;
                case 3: if (!isDivisibleBy3(number)) {
                    return false;
                }
                break;
                case 4: if (!isDivisibleBy4(number)) {
                    return false;
                }
                break;
                case 5: if (!isDivisibleBy5(number)) {
                    return false;
                }
                break;
                case 6: if (!isDivisibleBy6(number)) {
                    return false;
                }
                break;
                case 7: if (!isDivisibleBy7(number)) {
                    return false;
                }
                break;
                case 8: if (!isDivisibleBy8(number)) {
                    return false;
                }
                break;
                case 9: if (!isDivisibleBy9(number)) {
                    return false;
                }
                break;
                case 10: if (!isDivisibleBy10(number)) {
                    return false;
                }
                break;
                case 11: if (!isDivisibleBy11(number)) {
                    return false;
                }
                break;
                case 12: if (!isDivisibleBy12(number)) {
                    return false;
                }
                break;
            }
        }
        return true;
    }

    private static boolean isDivisibleBy2(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit % 2 == 0;
    }

    private static boolean isDivisibleBy3(String number) {
        int digitSum = 0;
        for (char digit : number.toCharArray()) {
            digitSum += Character.getNumericValue(digit);
        }
        return digitSum % 3 == 0;
    }

    private static boolean isDivisibleBy4(String number) {
        int startIndex = Math.max(0, number.length() - 2);
        int last2Digits = Integer.parseInt(number.substring(startIndex));
        return last2Digits % 4 == 0;
    }

    private static boolean isDivisibleBy5(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit == 0 || lastDigit == 5;
    }

    private static boolean isDivisibleBy6(String number) {
        return isDivisibleBy2(number) && isDivisibleBy3(number);
    }

    private static boolean isDivisibleBy7(String number) {
        int sum = 0;
        boolean add = false;

        for (int i = number.length() - 1; i >= 0; i -= 3) {
            int startIndex = Math.max(0, i - 2);
            int value = Integer.parseInt(number.substring(startIndex, i + 1));

            if (add) {
                sum += value;
            } else {
                sum -= value;
            }
            add = !add;
        }
        return sum % 7 == 0;
    }

    private static boolean isDivisibleBy8(String number) {
        int startIndex = Math.max(0, number.length() - 3);
        int last3Digits = Integer.parseInt(number.substring(startIndex));
        return last3Digits % 8 == 0;
    }

    private static boolean isDivisibleBy9(String number) {
        int digitSum = 0;
        for (char digit : number.toCharArray()) {
            digitSum += Character.getNumericValue(digit);
        }
        return digitSum % 9 == 0;
    }

    private static boolean isDivisibleBy10(String number) {
        int lastDigit = Character.getNumericValue(number.charAt(number.length() - 1));
        return lastDigit == 0;
    }

    private static boolean isDivisibleBy11(String number) {
        int digitSum = 0;
        boolean add = false;

        for (char digit : number.toCharArray()) {
            int value = Character.getNumericValue(digit);
            if (add) {
                digitSum += value;
            } else {
                digitSum -= value;
            }
            add = !add;
        }
        return digitSum % 11 == 0;
    }

    private static boolean isDivisibleBy12(String number) {
        return isDivisibleBy3(number) && isDivisibleBy4(number);
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

        public void flush() {
            writer.flush();
        }
    }
}
