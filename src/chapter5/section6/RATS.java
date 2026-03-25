package chapter5.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 22/03/26.
 */
public class RATS {

    private static class Result {
        char type;
        String value;

        public Result(char type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int termsNumber = FastReader.nextInt();
            String firstTerm = FastReader.next();

            Result result = computeTerms(termsNumber, firstTerm);
            outputWriter.print(dataSetNumber + " ");
            if (result.type != '*') {
                outputWriter.print(result.type + " ");
            }
            outputWriter.printLine(result.value);
        }
        outputWriter.flush();
    }

    private static Result computeTerms(int termsNumber, String firstTerm) {
        String[] terms = new String[termsNumber];
        terms[0] = firstTerm;
        if (isCreeper(firstTerm)) {
            return new Result('C', "1");
        }

        for (int i = 1; i < terms.length; i++) {
            String rats = computeRats(terms[i - 1]);
            terms[i] = rats;

            if (isCreeper(terms[i])) {
                return new Result('C', String.valueOf(i + 1));
            }
        }

        int firstRepeatIndex = hasCycle(terms);
        if (firstRepeatIndex != -1) {
            return new Result('R', String.valueOf(firstRepeatIndex));
        }
        return new Result('*', terms[termsNumber - 1]);
    }

    private static String computeRats(String term) {
        BigInteger value1 = new BigInteger(term);
        BigInteger value2 = new BigInteger(new StringBuilder(term).reverse().toString());
        char[] digits = value1.add(value2).toString().toCharArray();
        Arrays.sort(digits);
        return remove0Prefix(new String(digits));
    }

    private static boolean isCreeper(String term) {
        return isCreeper(term, "1233", "4444", '3')
                || isCreeper(term, "5566", "7777", '6');
    }

    private static boolean isCreeper(String term, String creeperStart, String creeperEnd, char middleDigit) {
        if (term.startsWith(creeperStart) && term.endsWith(creeperEnd)) {
            for (int i = 3; i < term.length() - 4; i++) {
                if (term.charAt(i) != middleDigit) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static int hasCycle(String[] terms) {
        Set<String> valuesSet = new HashSet<>();
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            if (valuesSet.contains(term)) {
                return i + 1;
            }
            valuesSet.add(term);
        }
        return -1;
    }

    private static String remove0Prefix(String term) {
        int startIndex = -1;
        for (int i = 0; i < term.length(); i++) {
            if (term.charAt(i) != '0') {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            return "0";
        }
        return term.substring(startIndex);
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
