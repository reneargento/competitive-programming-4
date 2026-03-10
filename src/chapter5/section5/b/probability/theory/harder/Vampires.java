package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/03/26.
 */
public class Vampires {

    private static final double EPSILON = .00000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int ev1 = FastReader.nextInt();
        int ev2 = FastReader.nextInt();
        int attack = FastReader.nextInt();
        int damage = FastReader.nextInt();

        while (ev1 != 0 || ev2 != 0 || attack != 0 || damage != 0) {
            double winProbability = computeWinProbability(ev1, ev2, attack, damage);
            outputWriter.printLine(String.format("%.1f", winProbability));

            ev1 = FastReader.nextInt();
            ev2 = FastReader.nextInt();
            attack = FastReader.nextInt();
            damage = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    // Gambler's ruin adaptation
    private static double computeWinProbability(int ev1, int ev2, int attack, int damage) {
        double attackProbability = attack / 6.0;
        double q = 1 - attackProbability;

        int a = (int) Math.ceil(ev1 / (double) damage);
        int b = (int) Math.ceil(ev2 / (double) damage);

        double winProbability;
        if (Math.abs(attackProbability - q) < EPSILON) {
            winProbability = a / (double) (a + b);
        } else {
            double r = q / attackProbability;
            winProbability = (1 - Math.pow(r, a)) / (1 - Math.pow(r, a + b));
        }
        return winProbability * 100;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
