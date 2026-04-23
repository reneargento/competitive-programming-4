package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 21/04/26.
 */
public class MessageDecoding {

    private static class Result {
        int segmentSize;
        String charactersMissing;

        public Result(int segmentSize, String charactersMissing) {
            this.segmentSize = segmentSize;
            this.charactersMissing = charactersMissing;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String header = FastReader.getLine();
        while (header != null) {
            Map<String, Character> decodeMap = buildDecodeMap(header);
            StringBuilder message = new StringBuilder();
            String messageLine = FastReader.getLine();
            int segmentSize = -1;

            while (true) {
                Result result = decodeMessage(decodeMap, message, messageLine, segmentSize);
                if (result == null) {
                    break;
                }
                segmentSize = result.segmentSize;
                messageLine = FastReader.getLine();
                messageLine = result.charactersMissing + messageLine;
            }
            outputWriter.printLine(message);
            header = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result decodeMessage(Map<String, Character> decodeMap, StringBuilder message, String messageLine,
                                        int segmentSize) {
        String charactersMissing = "";
        for (int i = 0; i < messageLine.length(); i += segmentSize) {
            if (segmentSize == -1) {
                if (i + 2 < messageLine.length()) {
                    segmentSize = Integer.parseInt(messageLine.substring(i, i + 3), 2);
                    if (segmentSize == 0) {
                        return null;
                    }

                    i += 2;
                    i = i - segmentSize + 1; // Skip the segment size bits but not anything else
                    continue;
                } else {
                    charactersMissing = messageLine.substring(i);
                    break;
                }
            }

            if (i + segmentSize >= messageLine.length()) {
                charactersMissing = messageLine.substring(i);
                break;
            }

            String bits = messageLine.substring(i, i + segmentSize);
            if (areAllBit1s(bits)) {
                i = i + segmentSize + 1;
                segmentSize = -1;
            } else {
                char decodedCharacter = decodeMap.get(bits);
                message.append(decodedCharacter);
            }
        }
        return new Result(segmentSize, charactersMissing);
    }

    private static Map<String, Character> buildDecodeMap(String header) {
        int key = 0;
        Map<String, Character> decodeMap = new HashMap<>();
        int updateValue = 1;
        int updateBitsLength = 1;

        for (char character : header.toCharArray()) {
            String bits = Integer.toBinaryString(key);
            if (key == updateValue && bits.length() == updateBitsLength) {
                bits = bits.replaceAll("1", "0");
                bits = bits + "0";
                key = 0;

                updateValue = ((updateValue + 1) * 2) - 1;
                updateBitsLength++;
            }

            while (bits.length() < updateBitsLength) {
                bits = "0" + bits;
            }
            decodeMap.put(bits, character);
            key++;
        }
        return decodeMap;
    }

    private static boolean areAllBit1s(String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) != '1') {
                return false;
            }
        }
        return true;
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
