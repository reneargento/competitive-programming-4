package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 27/04/26.
 */
public class TheVigenereCipher {

    private static class Decipherment implements Comparable<Decipherment> {
        String keyword;
        Plaintext plaintext;

        public Decipherment(String keyword, Plaintext plaintext) {
            this.keyword = keyword;
            this.plaintext = plaintext;
        }

        @Override
        public int compareTo(Decipherment other) {
            return Integer.compare(other.plaintext.value, plaintext.value);
        }
    }

    private static class Plaintext {
        String plaintext;
        int value;

        public Plaintext(String plaintext, int value) {
            this.plaintext = plaintext;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        char[][] vigenereSquare = buildVigenereSquare();
        List<Plaintext>[] plaintextsByLength = buildPlaintexts();

        String encipheredMessage = FastReader.getLine();
        while (encipheredMessage != null) {
            List<Decipherment> decipherments = computeDecipherments(vigenereSquare, plaintextsByLength, encipheredMessage);

            if (caseId > 1) {
                outputWriter.printLine();
            }
            for (Decipherment decipherment : decipherments) {
                outputWriter.printLine(decipherment.keyword + " -> " + decipherment.plaintext.plaintext);
            }
            caseId++;
            encipheredMessage = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Decipherment> computeDecipherments(char[][] vigenereSquare, List<Plaintext>[] plaintextsByLength,
                                                           String encipheredMessage) {
        List<Decipherment> decipherments = new ArrayList<>();

        for (Plaintext plaintext : plaintextsByLength[encipheredMessage.length()]) {
            StringBuilder keyword = new StringBuilder();

            for (int i = 0; i < plaintext.plaintext.length(); i++) {
                char character = plaintext.plaintext.charAt(i);
                int column = character - 'a';
                for (int row = 0; row < vigenereSquare.length; row++) {
                    if (vigenereSquare[row][column] == encipheredMessage.charAt(i)) {
                        keyword.append(vigenereSquare[row][vigenereSquare[row].length - 1]);
                    }
                }
            }
            decipherments.add(new Decipherment(keyword.toString(), plaintext));
        }
        Collections.sort(decipherments);
        return decipherments;
    }

    private static char[][] buildVigenereSquare() {
        char[][] vigenereSquare = new char[26][26];
        String values = "BCDEFGHIJKLMNOPQRSTUVWXYZA";

        for (int row = 0; row < vigenereSquare.length; row++) {
            vigenereSquare[row] = values.toCharArray();
            values = values.substring(1) + values.charAt(0);
        }
        return vigenereSquare;
    }

    @SuppressWarnings("unchecked")
    private static List<Plaintext>[] buildPlaintexts() {
        List<Plaintext>[] plaintextsByLength = new List[16];
        for (int i = 0; i < plaintextsByLength.length; i++) {
            plaintextsByLength[i] = new ArrayList<>();
        }
        String[] numbers = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

        for (int number1 = 0; number1 < numbers.length; number1++) {
            for (int number2 = 0; number2 < numbers.length; number2++) {
                for (int number3 = 0; number3 < numbers.length; number3++) {
                    String plaintext = numbers[number1] + numbers[number2] + numbers[number3];
                    String numberString = number1 + String.valueOf(number2) + number3;
                    int number = Integer.parseInt(numberString);
                    int plaintextLength = plaintext.length();
                    plaintextsByLength[plaintextLength].add(new Plaintext(plaintext, number));
                }
            }
        }
        return plaintextsByLength;
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

        public void flush() {
            writer.flush();
        }
    }
}
