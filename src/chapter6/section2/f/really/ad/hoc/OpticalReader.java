package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class OpticalReader {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int questions = FastReader.nextInt();
        while (questions != 0) {
            for (int q = 0; q < questions; q++) {
                int[] grayLevels = new int[5];
                for (int i = 0; i < grayLevels.length; i++) {
                    grayLevels[i] = FastReader.nextInt();
                }
                char result = evaluateQuestion(grayLevels);
                outputWriter.printLine(result);
            }
            questions = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static char evaluateQuestion(int[] grayLevels) {
        char result = '?';
        for (int i = 0; i < grayLevels.length; i++) {
            if (grayLevels[i] <= 127) {
                char answer = (char) ('A' + i);
                if (result == '?') {
                    result = answer;
                } else {
                    result = '*';
                }
            }
        }
        return result == '?' ? '*' : result;
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

        public void flush() {
            writer.flush();
        }
    }
}