package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class DNATranslation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, String> geneticMap = buildGeneticMap();
        String dnaStrand = FastReader.next();
        while (!dnaStrand.equals("*")) {
            String protein = computeProtein(geneticMap, dnaStrand);
            if (protein == null) {
                outputWriter.printLine("*** No translatable DNA found ***");
            } else {
                outputWriter.printLine(protein);
            }
            dnaStrand = FastReader.next();
        }
        outputWriter.flush();
    }

    private static String computeProtein(Map<String, String> geneticMap, String dnaStrand) {
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                StringBuilder reverseDnaStrand = new StringBuilder(dnaStrand);
                dnaStrand = reverseDnaStrand.reverse().toString();
            }

            String protein = mapCodons(geneticMap, dnaStrand);
            if (protein != null) {
                return protein;
            }

            dnaStrand = computeComplementaryStrand(dnaStrand);
            protein = mapCodons(geneticMap, dnaStrand);
            if (protein != null) {
                return protein;
            }
        }
        return null;
    }

    private static String mapCodons(Map<String, String> geneticMap, String dnaStrand) {
        StringBuilder translation = new StringBuilder();
        dnaStrand = dnaStrand.replaceAll("T", "U");

        for (int startIndex = 0; startIndex < dnaStrand.length(); startIndex++) {
            boolean hasStartSequence = false;
            boolean hasEndSequence = false;

            for (int i = startIndex; i + 3 <= dnaStrand.length(); i += 3) {
                String codon = dnaStrand.substring(i, i + 3);
                if (!hasStartSequence) {
                    if (isStartCodon(codon)) {
                        hasStartSequence = true;
                    }
                    continue;
                }

                if (isEndCodon(codon)) {
                    hasEndSequence = true;
                    break;
                }

                if (!geneticMap.containsKey(codon)) {
                    return null;
                }

                if (translation.length() != 0) {
                    translation.append("-");
                }
                translation.append(geneticMap.get(codon));
            }

            if (hasStartSequence && hasEndSequence) {
                return translation.toString();
            }
        }
        return null;
    }

    private static String computeComplementaryStrand(String dnaStrand) {
        StringBuilder complementaryStrand = new StringBuilder();
        for (char base : dnaStrand.toCharArray()) {
            if (base == 'A') {
                complementaryStrand.append('T');
            } else if (base == 'T') {
                complementaryStrand.append('A');
            } else if (base == 'C') {
                complementaryStrand.append('G');
            } else {
                complementaryStrand.append('C');
            }
        }
        return complementaryStrand.toString();
    }

    private static boolean isStartCodon(String codon) {
        return codon.equals("AUG");
    }

    private static boolean isEndCodon(String codon) {
        return codon.equals("UAA") || codon.equals("UAG") || codon.equals("UGA");
    }

    private static Map<String, String> buildGeneticMap() {
        Map<String, String> geneticMap = new HashMap<>();
        geneticMap.put("UUU", "Phe");
        geneticMap.put("UCU", "Ser");
        geneticMap.put("UAU", "Tyr");
        geneticMap.put("UGU", "Cys");
        geneticMap.put("UUC", "Phe");
        geneticMap.put("UCC", "Ser");
        geneticMap.put("UAC", "Tyr");
        geneticMap.put("UGC", "Cys");
        geneticMap.put("UUA", "Leu");
        geneticMap.put("UCA", "Ser");
        geneticMap.put("UUG", "Leu");
        geneticMap.put("UCG", "Ser");
        geneticMap.put("UGG", "Trp");

        geneticMap.put("CUU", "Leu");
        geneticMap.put("CCU", "Pro");
        geneticMap.put("CAU", "His");
        geneticMap.put("CGU", "Arg");
        geneticMap.put("CUC", "Leu");
        geneticMap.put("CCC", "Pro");
        geneticMap.put("CAC", "His");
        geneticMap.put("CGC", "Arg");
        geneticMap.put("CUA", "Leu");
        geneticMap.put("CCA", "Pro");
        geneticMap.put("CAA", "Gln");
        geneticMap.put("CGA", "Arg");
        geneticMap.put("CUG", "Leu");
        geneticMap.put("CCG", "Pro");
        geneticMap.put("CAG", "Gln");
        geneticMap.put("CGG", "Arg");

        geneticMap.put("AUU", "Ile");
        geneticMap.put("ACU", "Thr");
        geneticMap.put("AAU", "Asn");
        geneticMap.put("AGU", "Ser");
        geneticMap.put("AUC", "Ile");
        geneticMap.put("ACC", "Thr");
        geneticMap.put("AAC", "Asn");
        geneticMap.put("AGC", "Ser");
        geneticMap.put("AUA", "Ile");
        geneticMap.put("ACA", "Thr");
        geneticMap.put("AAA", "Lys");
        geneticMap.put("AGA", "Arg");
        geneticMap.put("AUG", "Met");
        geneticMap.put("ACG", "Thr");
        geneticMap.put("AAG", "Lys");
        geneticMap.put("AGG", "Arg");

        geneticMap.put("GUU", "Val");
        geneticMap.put("GCU", "Ala");
        geneticMap.put("GAU", "Asp");
        geneticMap.put("GGU", "Gly");
        geneticMap.put("GUC", "Val");
        geneticMap.put("GCC", "Ala");
        geneticMap.put("GAC", "Asp");
        geneticMap.put("GGC", "Gly");
        geneticMap.put("GUA", "Val");
        geneticMap.put("GCA", "Ala");
        geneticMap.put("GAA", "Glu");
        geneticMap.put("GGA", "Gly");
        geneticMap.put("GUG", "Val");
        geneticMap.put("GCG", "Ala");
        geneticMap.put("GAG", "Glu");
        geneticMap.put("GGG", "Gly");
        return geneticMap;
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
