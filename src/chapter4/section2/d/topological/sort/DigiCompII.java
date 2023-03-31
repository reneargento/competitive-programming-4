package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/03/23.
 */
public class DigiCompII {

    private static class Switch {
        int id;
        char state;
        int leftSwitchID;
        int rightSwitchID;

        public Switch(int id, char state, int leftSwitchID, int rightSwitchID) {
            this.id = id;
            this.state = state;
            this.leftSwitchID = leftSwitchID;
            this.rightSwitchID = rightSwitchID;
        }

        char getOppositeState() {
            return (state == 'L') ? 'R' : 'L';
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long balls = FastReader.nextLong();
        Switch[] switches = new Switch[FastReader.nextInt()];
        int[] inDegrees = new int[switches.length];

        for (int switchID = 0; switchID < switches.length; switchID++) {
            char state = FastReader.next().charAt(0);
            int leftVertexID = FastReader.nextInt() - 1;
            int rightVertexID = FastReader.nextInt() - 1;

            switches[switchID] = new Switch(switchID, state, leftVertexID, rightVertexID);
            if (leftVertexID != -1) {
                inDegrees[leftVertexID]++;
            }
            if (rightVertexID != -1) {
                inDegrees[rightVertexID]++;
            }
        }

        String finalState = computeFinalState(switches, inDegrees, balls);
        outputWriter.printLine(finalState);
        outputWriter.flush();
    }

    private static String computeFinalState(Switch[] switches, int[] inDegrees, long balls) {
        char[] finalStates = new char[switches.length];
        long[] ballArrivals = new long[switches.length];
        ballArrivals[0] = balls;

        computeBallArrivals(switches, inDegrees, ballArrivals);
        computeFinalState(switches, ballArrivals, finalStates);
        return new String(finalStates);
    }

    private static void computeBallArrivals(Switch[] switches, int[] inDegrees, long[] ballArrivals) {
        Queue<Integer> queue = new LinkedList<>();
        for (int switchID = 0; switchID < inDegrees.length; switchID++) {
            if (inDegrees[switchID] == 0) {
                queue.offer(switchID);
            }
        }

        while (!queue.isEmpty()) {
            int switchID = queue.poll();
            if (switchID == -1) {
                continue;
            }

            long ballsSentLeft = ballArrivals[switchID] / 2;
            long ballsSentRight = ballsSentLeft;

            if (ballArrivals[switchID] % 2 == 1) {
                if (switches[switchID].state == 'L') {
                    ballsSentLeft++;
                } else {
                    ballsSentRight++;
                }
            }

            int leftSwitch = switches[switchID].leftSwitchID;
            int rightSwitch = switches[switchID].rightSwitchID;

            if (leftSwitch != -1) {
                ballArrivals[leftSwitch] += ballsSentLeft;
                inDegrees[leftSwitch]--;
                if (inDegrees[leftSwitch] == 0) {
                    queue.offer(leftSwitch);
                }
            }
            if (rightSwitch != -1) {
                ballArrivals[rightSwitch] += ballsSentRight;
                inDegrees[rightSwitch]--;
                if (inDegrees[rightSwitch] == 0) {
                    queue.offer(rightSwitch);
                }
            }
        }
    }

    private static void computeFinalState(Switch[] switches, long[] ballArrivals, char[] finalStates) {
        for (int switchID = 0; switchID < finalStates.length; switchID++) {
            finalStates[switchID] = switches[switchID].state;
            if (ballArrivals[switchID] % 2 == 1) {
                finalStates[switchID] = switches[switchID].getOppositeState();
            }
        }
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
