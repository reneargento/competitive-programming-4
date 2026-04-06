package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by Rene Argento on 03/04/22.
 */
public class HToO {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        String inputMolecule = inputReader.next();
        int numberOfMolecules = inputReader.nextInt();
        String outputMolecule = inputReader.next();

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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
