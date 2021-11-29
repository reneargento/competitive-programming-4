package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/21.
 */
public class DanceRecital {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] routines = new String[FastReader.nextInt()];
        for (int r = 0; r < routines.length; r++) {
            routines[r] = FastReader.getLine();
        }
        int[][] routineChanges = computeRoutinesChangesTable(routines);

        int minimumQuickChanges = computeMinimumQuickChanges(routines, routineChanges);
        outputWriter.printLine(minimumQuickChanges);
        outputWriter.flush();
    }

    private static int[][] computeRoutinesChangesTable(String[] routines) {
        int[][] routineChanges = new int[routines.length][routines.length];

        for (int i = 0; i < routines.length; i++) {
            for (int j = i + 1; j < routines.length; j++) {
                String routine1 = routines[i];
                String routine2 = routines[j];
                routineChanges[i][j] = computeQuickChanges(routine1, routine2);
                routineChanges[j][i] = routineChanges[i][j];
            }
        }
        return routineChanges;
    }

    private static int computeQuickChanges(String routine1, String routine2) {
        Set<Character> performers = new HashSet<>();
        for (char performer : routine1.toCharArray()) {
            performers.add(performer);
        }
        for (char performer : routine2.toCharArray()) {
            performers.add(performer);
        }
        return (routine1.length() + routine2.length()) - performers.size();
    }

    private static int computeMinimumQuickChanges(String[] routines, int[][] routineChanges) {
        int[] order = new int[routines.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        int[] currentOrder = new int[order.length];
        return computeMinimumQuickChanges(order, currentOrder, 0, 0, routineChanges);
    }

    private static int computeMinimumQuickChanges(int[] order, int[] currentOrder, int index, int mask,
                                                  int[][] routineChanges) {
        if (mask == (1 << order.length) - 1) {
            return computeQuickChanges(currentOrder, routineChanges);
        }

        int minimumQuickChanges = Integer.MAX_VALUE;
        for (int i = 0; i < order.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentOrder[index] = i;
                int quickChanges = computeMinimumQuickChanges(order, currentOrder, index + 1,
                        newMask, routineChanges);
                minimumQuickChanges = Math.min(minimumQuickChanges, quickChanges);
            }
        }
        return minimumQuickChanges;
    }

    private static int computeQuickChanges(int[] order, int[][] routineChanges) {
        int quickChanges = 0;

        for (int i = 1; i < order.length; i++) {
            int index1 = order[i - 1];
            int index2 = order[i];
            quickChanges += routineChanges[index1][index2];
        }
        return quickChanges;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
