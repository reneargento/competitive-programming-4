package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 28/06/2026.
 */
public class ImmediateDecodability {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int setId = 1;
        while (line != null) {
            List<String> codes = new ArrayList<>();
            while (!line.equals("9")) {
                codes.add(line);
                line = FastReader.getLine();
            }
            boolean isImmediatelyDecodable = isImmediatelyDecodable(codes);
            outputWriter.print(String.format("Set %d is ", setId));
            if (!isImmediatelyDecodable) {
                outputWriter.print("not ");
            }
            outputWriter.printLine("immediately decodable");

            setId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isImmediatelyDecodable(List<String> codes) {
        for (int i = 0; i < codes.size(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (codes.get(j).startsWith(codes.get(i))) {
                    return false;
                }
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