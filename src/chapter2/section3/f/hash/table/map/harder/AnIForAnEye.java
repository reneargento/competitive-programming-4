package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/21.
 */
public class AnIForAnEye {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Character> abbreviationMap = createAbbreviationMap();
        int lines = FastReader.nextInt();

        for (int l = 0; l < lines; l++) {
            String line = FastReader.getLine();
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
