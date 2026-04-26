package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class BaudotDataCommunicationCode {

    private static final String SHIFT_DOWN = "shift down";
    private static final String SHIFT_UP = "shift up";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String downShiftCharacters = FastReader.getLine();
        String upShiftCharacters = FastReader.getLine();

        Map<String, String> downShiftEncoder = getMapping(downShiftCharacters);
        Map<String, String> upShiftEncoder = getMapping(upShiftCharacters);

        String message = FastReader.getLine();
        while (message != null) {
            StringBuilder translatedMessage = new StringBuilder();
            StringBuilder bits = new StringBuilder();
            boolean isDownShift = true;

            for (int i = 0; i < message.length(); i++) {
                bits.append(message.charAt(i));

                if ((i + 1) % 5 == 0) {
                    String value;

                    if (isDownShift) {
                        value = downShiftEncoder.get(bits.toString());
                    } else {
                        value = upShiftEncoder.get(bits.toString());
                    }

                    if (value.equals(SHIFT_DOWN)) {
                        isDownShift = true;
                    } else if (value.equals(SHIFT_UP)) {
                        isDownShift = false;
                    } else {
                        translatedMessage.append(value);
                    }

                    bits = new StringBuilder();
                }
            }
            outputWriter.printLine(translatedMessage);
            message = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Map<String, String> getMapping(String characterSet) {
        Map<String, String> mapping = new HashMap<>();

        int bitsDecimal = 0;
        for (char character : characterSet.toCharArray()) {
            String bits = appendZeroBits(Integer.toBinaryString(bitsDecimal));

            switch (bits) {
                case "11011":
                    mapping.put(bits, SHIFT_DOWN);
                    break;
                case "11111":
                    mapping.put(bits, SHIFT_UP);
                    break;
                default:
                    mapping.put(bits, String.valueOf(character));
                    break;
            }
            bitsDecimal++;
        }
        return mapping;
    }

    private static String appendZeroBits(String bits) {
        while (bits.length() != 5) {
            bits = "0" + bits;
        }
        return bits;
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
