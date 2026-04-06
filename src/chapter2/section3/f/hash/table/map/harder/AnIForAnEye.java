package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by Rene Argento on 02/06/21.
 */
public class AnIForAnEye {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Character> abbreviationMap = createAbbreviationMap();
        int lines = inputReader.nextInt();

        for (int l = 0; l < lines; l++) {
            String line = inputReader.nextLine();
            mapLine(line, abbreviationMap, outputWriter);
        }
        outputWriter.flush();
    }

    private static void mapLine(String line, Map<String, Character> abbreviationMap, OutputWriter outputWriter) {
        for (int i = 0; i < line.length(); i++) {
            StringBuilder wordCandidate = new StringBuilder();
            for (int j = i; j < i + 4 && j < line.length(); j++) {
                wordCandidate.append(line.charAt(j));
            }

            int nextIndex = -1;
            boolean replaced = false;
            char replacedCharacter = '@';
            int wordCandidateLength = wordCandidate.length();
            for (int j = 0; j < wordCandidateLength; j++) {
                if (abbreviationMap.containsKey(wordCandidate.toString().toLowerCase())) {
                    replacedCharacter = abbreviationMap.get(wordCandidate.toString().toLowerCase());
                    if (Character.isUpperCase(wordCandidate.charAt(0))) {
                        replacedCharacter = Character.toUpperCase(replacedCharacter);
                    }
                    nextIndex = i + (wordCandidateLength - j);
                    replaced = true;
                    break;
                }
                wordCandidate.deleteCharAt(wordCandidate.length() - 1);
            }

            if (replaced) {
                outputWriter.print(replacedCharacter);
                i = nextIndex - 1;
            } else {
                outputWriter.print(line.charAt(i));
            }
        }
        outputWriter.printLine();
    }

    private static Map<String, Character> createAbbreviationMap() {
        Map<String, Character> abbreviationMap = new HashMap<>();
        abbreviationMap.put("at", '@');
        abbreviationMap.put("and", '&');
        abbreviationMap.put("one", '1');
        abbreviationMap.put("won", '1');
        abbreviationMap.put("to", '2');
        abbreviationMap.put("too", '2');
        abbreviationMap.put("two", '2');
        abbreviationMap.put("for", '4');
        abbreviationMap.put("four", '4');
        abbreviationMap.put("bea", 'b');
        abbreviationMap.put("be", 'b');
        abbreviationMap.put("bee", 'b');
        abbreviationMap.put("sea", 'c');
        abbreviationMap.put("see", 'c');
        abbreviationMap.put("eye", 'i');
        abbreviationMap.put("oh", 'o');
        abbreviationMap.put("owe", 'o');
        abbreviationMap.put("are", 'r');
        abbreviationMap.put("you", 'u');
        abbreviationMap.put("why", 'y');
        return abbreviationMap;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
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

        private int nextInt() throws IOException {
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

        private String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
