package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class Recenice {

    public static void main(String[] args) throws IOException {
        FastReader.init();

        int wordsNumber = FastReader.nextInt();
        Map<Integer, String> numberMap = createNumberMap();
        String[] words = new String[wordsNumber];
        int totalLength = 0;
        int indexToAddWord = 0;

        for (int i = 0; i < words.length; i++) {
            String word = FastReader.next();
            if (word.equals("$")) {
                indexToAddWord = i;
            } else {
                totalLength += word.length();
            }
            words[i] = word;
        }
        addWord(words, indexToAddWord, numberMap, totalLength);
        printSequence(words);
    }

    private static void addWord(String[] words, int indexToAddWord, Map<Integer, String> numberMap,
                                int totalLength) {
        for (int number = 1; number <= 999; number++) {
            String numberDescription = numberMap.get(number);
            if (totalLength + numberDescription.length() == number) {
                words[indexToAddWord] = numberDescription;
                return;
            }
        }
    }

    private static void printSequence(String[] words) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        for (int i = 0; i < words.length; i++) {
            outputWriter.print(words[i]);

            if (i < words.length - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static Map<Integer, String> createNumberMap() {
        Map<Integer, String> numberMap = new HashMap<>();
        numberMap.put(0, "");
        numberMap.put(1, "one");
        numberMap.put(2, "two");
        numberMap.put(3, "three");
        numberMap.put(4, "four");
        numberMap.put(5, "five");
        numberMap.put(6, "six");
        numberMap.put(7, "seven");
        numberMap.put(8, "eight");
        numberMap.put(9, "nine");
        numberMap.put(10, "ten");
        numberMap.put(11, "eleven");
        numberMap.put(12, "twelve");
        numberMap.put(13, "thirteen");
        numberMap.put(14, "fourteen");
        numberMap.put(15, "fifteen");
        numberMap.put(16, "sixteen");
        numberMap.put(17, "seventeen");
        numberMap.put(18, "eighteen");
        numberMap.put(19, "nineteen");
        numberMap.put(20, "twenty");
        numberMap.put(30, "thirty");
        numberMap.put(40, "forty");
        numberMap.put(50, "fifty");
        numberMap.put(60, "sixty");
        numberMap.put(70, "seventy");
        numberMap.put(80, "eighty");
        numberMap.put(90, "ninety");

        for (int number = 21; number <= 999; number++) {
            String numberWord;
            if (number <= 99) {
                numberWord = getTwoDigitNumber(number, numberMap);
            } else {
                numberWord = getThreeDigitNumber(number, numberMap);
            }
            numberMap.put(number, numberWord);
        }
        return numberMap;
    }

    private static String getTwoDigitNumber(int number, Map<Integer, String> numberMap) {
        if (number <= 19) {
            return numberMap.get(number);
        }
        int decimalDigit = number / 10;
        int unitDigit = number % 10;
        return numberMap.get(decimalDigit * 10) + numberMap.get(unitDigit);
    }

    private static String getThreeDigitNumber(int number, Map<Integer, String> numberMap) {
        int hundredDigit = number / 100;
        return numberMap.get(hundredDigit) + "hundred" + getTwoDigitNumber(number % 100, numberMap);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
