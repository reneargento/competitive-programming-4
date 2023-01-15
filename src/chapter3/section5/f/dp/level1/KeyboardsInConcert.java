package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/01/23.
 */
public class KeyboardsInConcert {

    private static class Instrument {
        boolean[] notesPlayable;

        public Instrument(boolean[] notesPlayable) {
            this.notesPlayable = notesPlayable;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Instrument[] instruments = new Instrument[FastReader.nextInt()];
        int[] tuneNotes = new int[FastReader.nextInt()];

        for (int i = 0; i < instruments.length; i++) {
            int notes = FastReader.nextInt();
            boolean[] notesPlayable = new boolean[1001];
            for (int n = 0; n < notes; n++) {
                notesPlayable[FastReader.nextInt()] = true;
            }
            instruments[i] = new Instrument(notesPlayable);
        }
        for (int i = 0; i < tuneNotes.length; i++) {
            tuneNotes[i] = FastReader.nextInt();
        }
        int minimumSwitches = computeMinimumSwitches(instruments, tuneNotes);
        outputWriter.printLine(minimumSwitches);
        outputWriter.flush();
    }

    private static int computeMinimumSwitches(Instrument[] instruments, int[] tuneNotes) {
        // dp[tuneId][currentInstrument] = minimumSwitches
        int[][] dp = new int[tuneNotes.length][instruments.length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int minimumSwitches = Integer.MAX_VALUE;
        for (int instrumentId = 0; instrumentId < instruments.length; instrumentId++) {
            if (instruments[instrumentId].notesPlayable[tuneNotes[0]]) {
                int switches = computeMinimumSwitches(instruments, tuneNotes, dp, 1, instrumentId);
                minimumSwitches = Math.min(minimumSwitches, switches);
            }
        }
        return minimumSwitches;
    }

    private static int computeMinimumSwitches(Instrument[] instruments, int[] tuneNotes, int[][] dp, int tuneId,
                                              int currentInstrument) {
        if (tuneId == dp.length) {
            return 0;
        }
        if (dp[tuneId][currentInstrument] != -1) {
            return dp[tuneId][currentInstrument];
        }

        if (instruments[currentInstrument].notesPlayable[tuneNotes[tuneId]]) {
            return computeMinimumSwitches(instruments, tuneNotes, dp, tuneId + 1, currentInstrument);
        }

        int minimumSwitches = Integer.MAX_VALUE;
        for (int instrumentId = 0; instrumentId < instruments.length; instrumentId++) {
            if (instruments[instrumentId].notesPlayable[tuneNotes[tuneId]]) {
                int switches = 1 + computeMinimumSwitches(instruments, tuneNotes, dp, tuneId + 1, instrumentId);
                minimumSwitches = Math.min(minimumSwitches, switches);
            }
        }
        dp[tuneId][currentInstrument] = minimumSwitches;
        return minimumSwitches;
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
