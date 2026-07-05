package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/07/2026.
 */
public class Kolone {

    private static class Ant {
        char identifier;
        int row;

        public Ant(char identifier, int row) {
            this.identifier = identifier;
            this.row = row;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        FastReader.nextInt();
        FastReader.nextInt();
        String row1 = FastReader.next();
        String row2 = FastReader.next();
        int seconds = FastReader.nextInt();

        String order = getOrder(row1, row2, seconds);
        outputWriter.printLine(order);
        outputWriter.flush();
    }

    private static String getOrder(String row1, String row2, int seconds) {
        Ant[] ants = new Ant[row1.length() + row2.length()];
        int antsIndex = 0;
        for (int i = row1.length() - 1; i >= 0; i--) {
            char identifier = row1.charAt(i);
            ants[antsIndex++] = new Ant(identifier, 1);
        }
        for (int i = 0; i < row2.length(); i++) {
            char identifier = row2.charAt(i);
            ants[antsIndex++] = new Ant(identifier, 2);
        }

        for (int s = 0; s < seconds; s++) {
            for (int i = 0; i < ants.length - 1; i++) {
                if (ants[i].row != ants[i + 1].row
                        && ants[i].row == 1) {
                    Ant aux = ants[i];
                    ants[i] = ants[i + 1];
                    ants[i + 1] = aux;
                    i++;
                }
            }
        }
        return getOrder(ants);
    }

    private static String getOrder(Ant[] ants) {
        StringBuilder order = new StringBuilder();
        for (Ant ant : ants) {
            order.append(ant.identifier);
        }
        return order.toString();
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