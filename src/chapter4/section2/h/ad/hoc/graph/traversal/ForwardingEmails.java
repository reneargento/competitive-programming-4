package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/04/23.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume124/12442%20-%20Forwarding%20Emails.cpp
public class ForwardingEmails {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] sendTargets = new int[FastReader.nextInt()];
            int[] cycleRoots = new int[sendTargets.length];

            for (int i = 0; i < sendTargets.length; i++) {
                int martianID = FastReader.nextInt() - 1;
                int otherMartianID = FastReader.nextInt() - 1;
                sendTargets[martianID] = otherMartianID;
                cycleRoots[i] = i;
            }

            int selectedMartianID = selectMartianID(sendTargets, cycleRoots);
            outputWriter.printLine(String.format("Case %d: %d", t, selectedMartianID));
        }
        outputWriter.flush();
    }

    private static int selectMartianID(int[] sendTargets, int[] cycleRoots) {
        int[] lengths = new int[sendTargets.length];
        boolean[] visited = new boolean[sendTargets.length];
        boolean[] inStack = new boolean[sendTargets.length];
        int[] times = new int[sendTargets.length];
        Deque<Integer> stack = new ArrayDeque<>();
        int highestLength = 0;
        int selectedMartianID = 1;
        Arrays.fill(lengths, 1);

        for (int martianID = 0; martianID < sendTargets.length; martianID++) {
            if (!visited[martianID]) {
                depthFirstSearch(sendTargets, visited, inStack, cycleRoots, lengths, times, stack, 1,
                        martianID);
            }
        }

        for (int martianID = 0; martianID < sendTargets.length; martianID++) {
            if (lengths[martianID] > highestLength) {
                highestLength = lengths[martianID];
                selectedMartianID = martianID + 1;
            }
        }
        return selectedMartianID;
    }

    private static int depthFirstSearch(int[] sendTargets, boolean[] visited, boolean[] inStack, int[] cycleRoots,
                                        int[] lengths, int[] times, Deque<Integer> stack, int currentTime,
                                        int martianID) {
        visited[martianID] = true;
        inStack[martianID] = true;
        stack.push(martianID);

        times[martianID] = currentTime;
        int time = times[martianID];
        int neighborID = sendTargets[martianID];

        if (!visited[neighborID]) {
            time = Math.min(time, depthFirstSearch(sendTargets, visited, inStack, cycleRoots, lengths,
                    times, stack, currentTime + 1, neighborID));
        }
        if (inStack[neighborID]) {
            time = Math.min(time, times[neighborID]);
        }

        if (time == times[martianID]) {
            int cycleLength = 0;
            do {
                int martianIDInStack = stack.peek();
                cycleRoots[martianIDInStack] = martianID;
                inStack[martianIDInStack] = false;
                cycleLength++;
            } while(stack.pop() != martianID);

            if (cycleLength > 1) {
                lengths[martianID] += (cycleLength - 1);
            } else {
                int cycleRootID = cycleRoots[neighborID];
                lengths[martianID] += lengths[cycleRootID];
            }
        }
        return time;
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
