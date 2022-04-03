package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/22.
 */
public class HToO {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String inputMolecule = FastReader.next();
        int numberOfMolecules = FastReader.nextInt();
        String outputMolecule = FastReader.next();

        int maximumMolecules = computeMaximumMolecules(inputMolecule, numberOfMolecules, outputMolecule);
        outputWriter.printLine(maximumMolecules);
        outputWriter.flush();
    }

    private static int computeMaximumMolecules(String inputMolecule, int numberOfMolecules, String outputMolecule) {
        Map<Character, Integer> inputAtoms = computeAtoms(inputMolecule);
        Map<Character, Integer> outputAtoms = computeAtoms(outputMolecule);
        int maximumMolecules = Integer.MAX_VALUE;

        for (Character molecule : outputAtoms.keySet()) {
            int outputFrequency = outputAtoms.get(molecule);
            int inputFrequency = inputAtoms.getOrDefault(molecule, 0) * numberOfMolecules;
            int possibleOutput = inputFrequency / outputFrequency;
            maximumMolecules = Math.min(maximumMolecules, possibleOutput);
        }
        return maximumMolecules;
    }

    private static Map<Character, Integer> computeAtoms(String molecule) {
        Map<Character, Integer> atoms = new HashMap<>();

        for (int i = 0; i < molecule.length(); i++) {
            char atom = molecule.charAt(i);
            int number;

            if (i == molecule.length() - 1
                    || !Character.isDigit(molecule.charAt(i + 1))) {
                number = 1;
            } else {
                StringBuilder numberString = new StringBuilder();
                i++;
                while (i < molecule.length() && Character.isDigit(molecule.charAt(i))) {
                    numberString.append(molecule.charAt(i));
                    i++;
                }
                i--;
                number = Integer.parseInt(numberString.toString());
            }

            int frequency = atoms.getOrDefault(atom, 0);
            frequency += number;
            atoms.put(atom, frequency);
        }
        return atoms;
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
