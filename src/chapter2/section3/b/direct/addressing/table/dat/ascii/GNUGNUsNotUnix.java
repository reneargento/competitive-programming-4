package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/05/21.
 */
public class GNUGNUsNotUnix {

    private static class Rule {
        Map<Character, BigInteger> frequencies;

        public Rule(Map<Character, BigInteger> frequencies) {
            this.frequencies = frequencies;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rulesNumber = FastReader.nextInt();
            Rule[] rules = new Rule[100];

            for (int r = 0; r < rulesNumber; r++) {
                String[] rule = FastReader.next().split("->");
                int index = rule[0].charAt(0) - 33;
                rules[index] = createRule(rule[1]);
            }

            int queries = FastReader.nextInt();

            for (int q = 0; q < queries; q++) {
                String initialString = FastReader.next();
                char character = FastReader.next().charAt(0);
                int timesToApply = FastReader.nextInt();

                BigInteger[] frequency = countFrequency(initialString, timesToApply, rules);

                int index = character - 33;
                BigInteger occurrences = frequency[index];
                outputWriter.printLine(occurrences);
            }
        }
        outputWriter.flush();
    }

    private static Rule createRule(String characters) {
        Map<Character, BigInteger> frequencies = new HashMap<>();

        for (char character : characters.toCharArray()) {
            BigInteger frequency = frequencies.getOrDefault(character, BigInteger.ZERO);
            frequency = frequency.add(BigInteger.ONE);
            frequencies.put(character, frequency);
        }
        return new Rule(frequencies);
    }

    private static BigInteger[] countFrequency(String initialString, int timesToApply, Rule[] rules) {
        BigInteger[] frequency = createEmptyFrequencyArray();

        for (char character : initialString.toCharArray()) {
            int index = character - 33;
            frequency[index] = frequency[index].add(BigInteger.ONE);
        }

        for (int i = 0; i < timesToApply; i++) {
            BigInteger[] newFrequency = createEmptyFrequencyArray();

            for (int f = 0; f < frequency.length; f++) {
                if (frequency[f].compareTo(BigInteger.ZERO) > 0) {
                    BigInteger currentFrequency = frequency[f];

                    if (rules[f] != null) {
                        for (char characterInRule : rules[f].frequencies.keySet()) {
                            int index = characterInRule - 33;
                            BigInteger newOccurrences = rules[f].frequencies.get(characterInRule).multiply(currentFrequency);
                            newFrequency[index] = newFrequency[index].add(newOccurrences);
                        }
                    } else {
                        newFrequency[f] = newFrequency[f].add(currentFrequency);
                    }
                }
            }
            frequency = newFrequency;
        }
        return frequency;
    }

    private static BigInteger[] createEmptyFrequencyArray() {
        BigInteger[] frequency = new BigInteger[100];
        Arrays.fill(frequency, BigInteger.ZERO);
        return frequency;
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
