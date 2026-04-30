package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/26.
 */
public class MonumentMaker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int linesOriginal = FastReader.nextInt();
        int widthOriginal = FastReader.nextInt(); // not used
        int widthNew = FastReader.nextInt();

        List<Integer> wordWidths = new ArrayList<>();
        int extraCharactersLength = 0;
        for (int i = 0; i < linesOriginal; i++) {
            String line = FastReader.next();
            if (i % 2 == 1) {
                line = new StringBuilder(line).reverse().toString();
            }

            boolean hasPeriod = line.contains(".");
            if (hasPeriod) {
                int lastPeriodIndex = getLastPeriodIndex(line);
                String completeWordsLine = line.substring(0, lastPeriodIndex);

                String[] words = completeWordsLine.split("\\.");
                for (int w = 0; w < words.length; w++) {
                    int length = words[w].length();
                    if (w == 0) {
                        length += extraCharactersLength;
                    }
                    wordWidths.add(length);
                }
            } else {
                wordWidths.add(extraCharactersLength + line.length());
            }
            extraCharactersLength = getExtraCharactersLength(line);
        }

        int newStoneLines = computeNewStoneLines(wordWidths, widthNew);
        outputWriter.printLine(newStoneLines);
        outputWriter.flush();
    }

    private static int getExtraCharactersLength(String line) {
        if (!line.contains(".")) {
            return 0;
        }
        return line.length() - 1 - getLastPeriodIndex(line);
    }

    private static int getLastPeriodIndex(String line) {
        for (int i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == '.') {
                return i;
            }
        }
        return -1;
    }

    private static int computeNewStoneLines(List<Integer> wordWidths, int widthNew) {
        int newStoneLines = 1;
        int currentWidth = 0;

        for (int width : wordWidths) {
            if (currentWidth + width <= widthNew) {
                currentWidth += width;
                currentWidth++;
            } else {
                newStoneLines++;
                currentWidth = width + 1;
            }
        }
        return newStoneLines;
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
