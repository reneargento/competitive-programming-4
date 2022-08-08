package chapter3.section4.f.non.classical.harder;

import java.io.*;

/**
 * Created by Rene Argento on 05/08/22.
 */
public class VirusReplication {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String originalDNA = FastReader.getLine();
        String infectedDNA = FastReader.getLine();

        int minimumDNALengthInserted = computeMinimumDNALengthInserted(originalDNA, infectedDNA);
        outputWriter.printLine(minimumDNALengthInserted);
        outputWriter.flush();
    }

    private static int computeMinimumDNALengthInserted(String originalDNA, String infectedDNA) {
        int start = -1;
        int end = -1;

        for (int i = 0; i < infectedDNA.length(); i++) {
            if (i == originalDNA.length()
                    || originalDNA.charAt(i) != infectedDNA.charAt(i)) {
                start = i;
                break;
            }
        }
        if (start == -1 || start == originalDNA.length()) {
            return Math.max(0, infectedDNA.length() - originalDNA.length());
        }

        int originalDNAIndex = originalDNA.length() - 1;
        for (int infectedDNAIndex = infectedDNA.length() - 1; originalDNAIndex >= 0 && infectedDNAIndex >= 0;
             originalDNAIndex--, infectedDNAIndex--) {
            if (originalDNA.charAt(originalDNAIndex) != infectedDNA.charAt(infectedDNAIndex)) {
                end = infectedDNAIndex;
                break;
            }
        }

        if (end == -1 || start > end) {
            return 0;
        }
        return end - start + 1;
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
