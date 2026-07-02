package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 02/07/2026.
 */
// This problem requires the use of the charset ISO-8859-1 for the output
public class LearningPortuguese {

    private static final char O_ACUTE = (char) 243;

    private static final String[] pronouns = {
            "eu", "tu", "ele/ela", "n" + O_ACUTE + "s", "v" + O_ACUTE + "s", "eles/elas"
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter();

        String line = FastReader.getLine();
        int dataSetId = 1;
        while (line != null) {
            String[] data = line.trim().split("\\s+");
            String verbPortuguese = data[0];
            String verbEnglish = data[1];

            if (dataSetId > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("%s (to %s)", verbPortuguese, verbEnglish));
            conjugateVerb(verbPortuguese, outputWriter);

            dataSetId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void conjugateVerb(String verb, OutputWriter outputWriter) {
        if (verb.length() < 2) {
            outputWriter.printLine("Unknown conjugation");
            return;
        }
        int verbLength = verb.length();
        String verbEnding = verb.substring(verbLength - 2);
        String root = verb.substring(0, verbLength - 2);
        String format = "%-10s%s";
        String[] conjugations = new String[6];
        char thematicVowel = verbEnding.charAt(0);

        if (verbEnding.equals("ar") || verbEnding.equals("er")) {
            conjugations[0] = root + "o";
            conjugations[1] = root + thematicVowel + "s";
            conjugations[2] = root + thematicVowel;
            conjugations[3] = root + thematicVowel + "mos";
            conjugations[4] = root + thematicVowel + "is";
            conjugations[5] = root + thematicVowel + "m";
        } else if (verbEnding.equals("ir")) {
            conjugations[0] = root + "o";
            conjugations[1] = root + "es";
            conjugations[2] = root + "e";
            conjugations[3] = root + thematicVowel + "mos";
            conjugations[4] = root + thematicVowel + "s";
            conjugations[5] = root + "em";
        } else {
            outputWriter.printLine("Unknown conjugation");
            return;
        }

        for (int i = 0; i < conjugations.length; i++) {
            outputWriter.printLine(String.format(format, pronouns[i], conjugations[i]));
        }
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

        public OutputWriter() throws UnsupportedEncodingException {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out, "ISO-8859-1")));
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