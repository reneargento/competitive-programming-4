package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/23.
 */
public class GopherAndHawks {

    private static class Hole {
        double x;
        double y;

        public Hole(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class State {
        int holesVisited;
        int holeID;

        public State(int holesVisited, int holeID) {
            this.holesVisited = holesVisited;
            this.holeID = holeID;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int speed = FastReader.nextInt();
        int maxTimeOutside = FastReader.nextInt() * 60;

        while (speed != 0 || maxTimeOutside != 0) {
            List<Hole> holes = new ArrayList<>();

            Hole startHole = new Hole(FastReader.nextDouble(), FastReader.nextDouble());
            Hole targetHole = new Hole(FastReader.nextDouble(), FastReader.nextDouble());
            holes.add(startHole);
            holes.add(targetHole);

            String line = FastReader.getLine();
            while (!line.isEmpty()) {
                String[] data = line.split(" ");
                double xCoordinate = Double.parseDouble(data[0]);
                double yCoordinate = Double.parseDouble(data[1]);
                holes.add(new Hole(xCoordinate, yCoordinate));

                line = FastReader.getLine();
            }

            int intermediateHoles = computeIntermediateHoles(speed, maxTimeOutside, holes);
            if (intermediateHoles != -1) {
                outputWriter.printLine(String.format("Yes, visiting %d other holes.", intermediateHoles));
            } else {
                outputWriter.printLine("No.");
            }

            speed = FastReader.nextInt();
            maxTimeOutside = FastReader.nextInt() * 60;
        }
        outputWriter.flush();
    }

    private static int computeIntermediateHoles(int speed, int maxTimeOutside, List<Hole> holes) {
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[holes.size()];

        queue.offer(new State(0, 0));
        visited[0] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Hole hole = holes.get(state.holeID);

            if (state.holeID == 1) {
                return state.holesVisited - 1;
            }

            for (int holeID = 1; holeID < holes.size(); holeID++) {
                if (!visited[holeID]) {
                    double distance = distanceBetweenPoints(hole, holes.get(holeID));
                    double timeTaken = distance / speed;
                    if (timeTaken <= maxTimeOutside) {
                        visited[holeID] = true;
                        queue.offer(new State(state.holesVisited + 1, holeID));
                    }
                }
            }
        }
        return -1;
    }

    public static double distanceBetweenPoints(Hole hole1, Hole hole2) {
        return Math.sqrt(Math.pow(hole1.x - hole2.x, 2) + Math.pow(hole1.y - hole2.y, 2));
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

        public void flush() {
            writer.flush();
        }
    }
}
