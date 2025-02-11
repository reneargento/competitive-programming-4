package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/02/25.
 */
public class AlienNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String number = FastReader.next();
            String sourceLanguage = FastReader.next();
            String targetLanguage = FastReader.next();

            String translatedNumber = translateNumber(number, sourceLanguage, targetLanguage);
            outputWriter.printLine(String.format("Case #%d: %s", t, translatedNumber));
        }
        outputWriter.flush();
    }

    private static String translateNumber(String number, String sourceLanguage, String targetLanguage) {
        long numberIndex = getNumberIndex(number, sourceLanguage);
        return getNumber(numberIndex, targetLanguage);
    }

    private static long getNumberIndex(String number, String language) {
        long index = 0;
        int alphabetSize = language.length();
        long multiplier = 1;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = language.indexOf(number.charAt(i));
            index += digit * multiplier;
            multiplier *= alphabetSize;
        }
        return index;
    }

    private static String getNumber(long index, String language) {
        StringBuilder number = new StringBuilder();
        int alphabetSize = language.length();

        while (index > 0) {
            int digit = (int) index % alphabetSize;
            number.append(language.charAt(digit));
            index /= alphabetSize;
        }
        return number.reverse().toString();
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
