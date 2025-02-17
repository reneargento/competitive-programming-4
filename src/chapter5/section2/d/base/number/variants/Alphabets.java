package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/02/25.
 */
// Problem located at https://ioi.te.lv/locations/ioi11/contest/day0_tasks.shtml
public class Alphabets {

    private static List<Character> transferredData;
    private static int transferredDataIndex;
    private static final OutputWriter outputWriter = new OutputWriter(System.out);

    public static void main(String[] args) throws IOException {
        int[] sampleData = { 10, 20, 30 };
        test(sampleData);
        outputWriter.printLine();

        int[] testBig = new int[100];
        for (int i = 0; i < testBig.length; i++) {
            testBig[i] = 255;
        }
        test(testBig);
        outputWriter.flush();
    }

    public static void encode(int dataLength, int[] data) {
        transferredData = new ArrayList<>();
        transferredDataIndex = 0;

        for (int i = 0; i < dataLength; i++) {
            int value = data[i];

            for (int dataIndex = 0; dataIndex < 2; dataIndex++) {
                int encodedDataInt = value % 26;
                char encodedData = getCharValue(encodedDataInt);
                sendData(encodedData);
                value /= 26;
            }
        }
    }

    public static void sendData(char character) {
        transferredData.add(character);
    }

    public static void decode(int encodedDataLength) {
        for (int i = 0; i < encodedDataLength; i += 2) {
            int decodedData = 0;
            int multiplier = 1;

            for (int dataIndex = 0; dataIndex < 2; dataIndex++) {
                char encodedData = readData();
                int encodedDataInt = getIntValue(encodedData);

                if (encodedDataInt > 0) {
                    decodedData += encodedDataInt * multiplier;
                    multiplier *= 26;
                }
                transferredDataIndex++;
            }
            outputData(decodedData);
        }
    }

    public static char readData() {
        return transferredData.get(transferredDataIndex);
    }

    public static void outputData(int data) {
        outputWriter.printLine(data);
    }

    private static char getCharValue(int value) {
        return (char) ('a' + value);
    }

    private static int getIntValue(char value) {
        return (value - 'a');
    }

    private static void test(int[] data) {
        encode(data.length, data);
        int encodedDataLength = transferredData.size();

        if (encodedDataLength > 2 * data.length) {
            throw new RuntimeException("The encoded message may not contain more than 2N characters");
        }
        decode(encodedDataLength);
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
