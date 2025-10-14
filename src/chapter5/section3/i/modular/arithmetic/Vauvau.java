package chapter5.section3.i.modular.arithmetic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/10/25.
 */
public class Vauvau {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int dog1Aggressive = FastReader.nextInt();
        int dog1Calm = FastReader.nextInt();
        int dog2Aggressive = FastReader.nextInt();
        int dog2Calm = FastReader.nextInt();
        int[] arrivalTimes = new int[3];
        for (int i = 0; i < arrivalTimes.length; i++) {
            arrivalTimes[i] = FastReader.nextInt();
        }

        for (int arrivalTime : arrivalTimes) {
            String result = checkAttacks(dog1Aggressive, dog1Calm, dog2Aggressive, dog2Calm, arrivalTime);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String checkAttacks(int dog1Aggressive, int dog1Calm, int dog2Aggressive, int dog2Calm,
                                       int arrivalTime) {
        int attacks = checkAttack(dog1Aggressive, dog1Calm, arrivalTime);
        attacks += checkAttack(dog2Aggressive, dog2Calm, arrivalTime);

        if (attacks == 0) {
            return "none";
        } else if (attacks == 1) {
            return "one";
        }
        return "both";
    }

    private static int checkAttack(int dogAggressive, int dogCalm, int arrivalTime) {
        return (arrivalTime - 1) % (dogAggressive + dogCalm) >= dogAggressive ? 0 : 1;
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
