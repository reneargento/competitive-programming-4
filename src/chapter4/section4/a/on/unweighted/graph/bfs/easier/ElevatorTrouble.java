package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/12/23.
 */
public class ElevatorTrouble {

    private static class State {
        int floor;
        int pushes;

        public State(int floor, int pushes) {
            this.floor = floor;
            this.pushes = pushes;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int floors = FastReader.nextInt();
        int startFloor = FastReader.nextInt() - 1;
        int goalFloor = FastReader.nextInt() - 1;
        int upFloors = FastReader.nextInt();
        int downFloors = FastReader.nextInt();

        int pushesNeeded = computePushesNeeded(floors, startFloor, goalFloor, upFloors, downFloors);
        if (pushesNeeded != -1) {
            outputWriter.printLine(pushesNeeded);
        } else {
            outputWriter.printLine("use the stairs");
        }
        outputWriter.flush();
    }

    private static int computePushesNeeded(int floors, int startFloor, int goalFloor, int upFloors, int downFloors) {
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[floors];

        queue.offer(new State(startFloor, 0));
        visited[startFloor] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.floor == goalFloor) {
                return state.pushes;
            }

            int nextFloorUp = state.floor + upFloors;
            int nextFloorDown = state.floor - downFloors;
            int nextPushes = state.pushes + 1;

            if (nextFloorUp < floors && !visited[nextFloorUp]) {
                queue.offer(new State(nextFloorUp, nextPushes));
                visited[nextFloorUp] = true;
            }
            if (nextFloorDown >= 0 && !visited[nextFloorDown]) {
                queue.offer(new State(nextFloorDown, nextPushes));
                visited[nextFloorDown] = true;
            }
        }
        return -1;
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
