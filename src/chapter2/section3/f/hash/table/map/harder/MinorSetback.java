package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/06/21.
 */
public class MinorSetback {

    private static class PitchNames {
        String name1;
        String name2;

        public PitchNames(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Double, PitchNames> frequencyToPitchNameMap = createFrequencyToPitchNameMap();
        Map<String, Set<String>> musicalKeyMap = createMusicalKeyMap();

        int frequenciesNumber = FastReader.nextInt();
        double[] frequencies = new double[frequenciesNumber];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = FastReader.nextDouble();
        }

        String musicalKey = getMusicalKey(frequencies, musicalKeyMap, frequencyToPitchNameMap);
        if (musicalKey == null) {
            outputWriter.printLine("cannot determine key");
        } else {
            outputWriter.printLine(musicalKey);
            for (double frequency : frequencies) {
                String pitch = getPitch(frequency, frequencyToPitchNameMap, musicalKeyMap.get(musicalKey));
                outputWriter.printLine(pitch);
            }
        }
        outputWriter.flush();
    }

    private static String getMusicalKey(double[] frequencies, Map<String, Set<String>> musicalKeyMap,
                                        Map<Double, PitchNames> frequencyToPitchNameMap) {
        List<String> keys = new ArrayList<>();

        for (String musicalKey : musicalKeyMap.keySet()) {
            Set<String> pitches = musicalKeyMap.get(musicalKey);
            if (canBeMusicalKey(frequencies, pitches, frequencyToPitchNameMap)) {
                keys.add(musicalKey);
            }
        }

        if (keys.size() != 1) {
            return null;
        }
        return keys.get(0);
    }

    private static boolean canBeMusicalKey(double[] frequencies, Set<String> pitches,
                                           Map<Double, PitchNames> frequencyToPitchNameMap) {
        for (Double frequency : frequencies) {
            double baseFrequency = getBaseFrequency(frequency);
            PitchNames pitchNames = frequencyToPitchNameMap.get(baseFrequency);
            if (!pitches.contains(pitchNames.name1)
                    && (pitchNames.name2 == null || !pitches.contains(pitchNames.name2))) {
                return false;
            }
        }
        return true;
    }

    private static double getBaseFrequency(double frequency) {
        while (frequency < 440) {
            frequency *= 2;
        }
        while (frequency >= 880) {
            frequency /= 2;
        }
        return roundValuePrecisionDigits(frequency, 2);
    }

    private static String getPitch(double frequency, Map<Double, PitchNames> frequencyToPitchNameMap,
                                   Set<String> musicalKey) {
        double baseFrequency = getBaseFrequency(frequency);
        PitchNames pitchNames = frequencyToPitchNameMap.get(baseFrequency);
        if (musicalKey.contains(pitchNames.name1)) {
            return pitchNames.name1;
        }
        return pitchNames.name2;
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

    private static Map<Double, PitchNames> createFrequencyToPitchNameMap() {
        Map<Double, PitchNames> frequencyToPitchNameMap = new HashMap<>();
        frequencyToPitchNameMap.put(440.0, new PitchNames("A", null));
        frequencyToPitchNameMap.put(466.16, new PitchNames("A#", "Bb"));
        frequencyToPitchNameMap.put(493.88, new PitchNames("B", null));
        frequencyToPitchNameMap.put(523.25, new PitchNames("C", null));
        frequencyToPitchNameMap.put(554.37, new PitchNames("C#", "Db"));
        frequencyToPitchNameMap.put(587.33, new PitchNames("D", null));
        frequencyToPitchNameMap.put(622.25, new PitchNames("D#", "Eb"));
        frequencyToPitchNameMap.put(659.26, new PitchNames("E", null));
        frequencyToPitchNameMap.put(698.46, new PitchNames("F", null));
        frequencyToPitchNameMap.put(739.99, new PitchNames("F#", "Gb"));
        frequencyToPitchNameMap.put(783.99, new PitchNames("G", null));
        frequencyToPitchNameMap.put(830.61, new PitchNames("G#", "Ab"));
        return frequencyToPitchNameMap;
    }

    private static Map<String, Set<String>> createMusicalKeyMap() {
        Map<String, Set<String>> musicalKeyMap = new HashMap<>();

        Set<String> gMajorPitches = new HashSet<>();
        gMajorPitches.add("G");
        gMajorPitches.add("A");
        gMajorPitches.add("B");
        gMajorPitches.add("C");
        gMajorPitches.add("D");
        gMajorPitches.add("E");
        gMajorPitches.add("F#");
        musicalKeyMap.put("G major", gMajorPitches);

        Set<String> cMajorPitches = new HashSet<>();
        cMajorPitches.add("C");
        cMajorPitches.add("D");
        cMajorPitches.add("E");
        cMajorPitches.add("F");
        cMajorPitches.add("G");
        cMajorPitches.add("A");
        cMajorPitches.add("B");
        musicalKeyMap.put("C major", cMajorPitches);

        Set<String> eFlatMajorPitches = new HashSet<>();
        eFlatMajorPitches.add("Eb");
        eFlatMajorPitches.add("F");
        eFlatMajorPitches.add("G");
        eFlatMajorPitches.add("Ab");
        eFlatMajorPitches.add("Bb");
        eFlatMajorPitches.add("C");
        eFlatMajorPitches.add("D");
        musicalKeyMap.put("Eb major", eFlatMajorPitches);

        Set<String> fSharpMinorPitches = new HashSet<>();
        fSharpMinorPitches.add("F#");
        fSharpMinorPitches.add("G#");
        fSharpMinorPitches.add("A");
        fSharpMinorPitches.add("B");
        fSharpMinorPitches.add("C#");
        fSharpMinorPitches.add("D");
        fSharpMinorPitches.add("E");
        musicalKeyMap.put("F# minor", fSharpMinorPitches);

        Set<String> gMinorPitches = new HashSet<>();
        gMinorPitches.add("G");
        gMinorPitches.add("A");
        gMinorPitches.add("Bb");
        gMinorPitches.add("C");
        gMinorPitches.add("D");
        gMinorPitches.add("Eb");
        gMinorPitches.add("F");
        musicalKeyMap.put("G minor", gMinorPitches);

        return musicalKeyMap;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
