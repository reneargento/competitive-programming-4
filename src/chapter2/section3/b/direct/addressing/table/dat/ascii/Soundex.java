package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class Soundex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Character, Integer> soundexMap = createSoundexMap();

        String word = FastReader.getLine();
        while (word != null) {
            String soundexCode = getSoundexCode(word, soundexMap);
            outputWriter.printLine(soundexCode);
            word = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String getSoundexCode(String word, Map<Character, Integer> soundexMap) {
        StringBuilder soundexCode = new StringBuilder();
        int currentDigit = -1;

        for (char letter : word.toCharArray()) {
            if (soundexMap.containsKey(letter)) {
                int digit = soundexMap.get(letter);

                if (digit == currentDigit) {
                    continue;
                }
                soundexCode.append(digit);
                currentDigit = digit;
            } else {
                currentDigit = -1;
            }
        }
        return soundexCode.toString();
    }

    private static Map<Character, Integer> createSoundexMap() {
        Map<Character, Integer> soundexMap = new HashMap<>();
        soundexMap.put('B', 1);
        soundexMap.put('F', 1);
        soundexMap.put('P', 1);
        soundexMap.put('V', 1);
        soundexMap.put('C', 2);
        soundexMap.put('G', 2);
        soundexMap.put('J', 2);
        soundexMap.put('K', 2);
        soundexMap.put('Q', 2);
        soundexMap.put('S', 2);
        soundexMap.put('X', 2);
        soundexMap.put('Z', 2);
        soundexMap.put('D', 3);
        soundexMap.put('T', 3);
        soundexMap.put('L', 4);
        soundexMap.put('M', 5);
        soundexMap.put('N', 5);
        soundexMap.put('R', 6);
        return soundexMap;
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
