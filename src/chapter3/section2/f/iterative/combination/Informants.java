package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/21.
 */
@SuppressWarnings("unchecked")
public class Informants {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int informants = FastReader.nextInt();
        int answers = FastReader.nextInt();

        while (informants != 0 || answers != 0) {
            List<Integer>[] answersPerInformant = new List[informants];
            for (int i = 0; i < informants; i++) {
                answersPerInformant[i] = new ArrayList<>();
            }

            for (int i = 0; i < answers; i++) {
                int informant = FastReader.nextInt() - 1;
                int answer = FastReader.nextInt();
                answersPerInformant[informant].add(answer);
            }

            int maxReliableInformants = computeMaxReliableInformants(answersPerInformant);
            outputWriter.printLine(maxReliableInformants);

            informants = FastReader.nextInt();
            answers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaxReliableInformants(List<Integer>[] answersPerInformant) {
        return computeMaxReliableInformants(answersPerInformant, 0, 0);
    }

    private static int computeMaxReliableInformants(List<Integer>[] answersPerInformant, int index, int mask) {
        if (index == answersPerInformant.length) {
            boolean[] reliableInformants = buildReliableArray(mask, answersPerInformant.length);
            return isPossibleScenario(reliableInformants, answersPerInformant);
        }

        int maskWithReliableInformant = mask | (1 << index);
        int reliablesWithMaskSet = computeMaxReliableInformants(answersPerInformant, index + 1, maskWithReliableInformant);
        int reliablesWithMaskUnset = computeMaxReliableInformants(answersPerInformant, index + 1, mask);
        return Math.max(reliablesWithMaskSet, reliablesWithMaskUnset);
    }

    private static boolean[] buildReliableArray(int mask, int informants) {
        boolean[] reliableInformants = new boolean[informants];
        for (int i = 0; i < informants; i++) {
            if ((mask & (1 << i)) > 0) {
                reliableInformants[i] = true;
            }
        }
        return reliableInformants;
    }

    private static int isPossibleScenario(boolean[] reliableInformants, List<Integer>[] answersPerInformant) {
        int reliable = 0;

        for (int i = 0; i < reliableInformants.length; i++) {
            if (reliableInformants[i]) {
                reliable++;

                for (int answer : answersPerInformant[i]) {
                    if (answer > 0 && !reliableInformants[answer - 1]) {
                        return 0;
                    }
                    if (answer < 0 && reliableInformants[Math.abs(answer + 1)]) {
                        return 0;
                    }
                }
            }
        }
        return reliable;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
