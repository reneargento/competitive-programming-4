package chapter4.section5.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/24.
 */
public class CatAndMouse {

    private static class Result {
        char catAndMouseMeet;
        char canMouseAvoidCat;

        public Result(char catAndMouseMeet, char canMouseAvoidCat) {
            this.catAndMouseMeet = catAndMouseMeet;
            this.canMouseAvoidCat = canMouseAvoidCat;
        }
    }

    private static class Edge {
        int roomID1;
        int roomID2;

        public Edge(int roomID1, int roomID2) {
            this.roomID1 = roomID1;
            this.roomID2 = roomID2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rooms = FastReader.nextInt();
            int catHome = FastReader.nextInt() - 1;
            int mouseHome = FastReader.nextInt() - 1;

            boolean[][] catRooms = new boolean[rooms][rooms];
            boolean[][] mouseRooms = new boolean[rooms][rooms];

            for (int roomID = 0; roomID < catRooms.length; roomID++) {
                catRooms[roomID][roomID] = true;
                mouseRooms[roomID][roomID] = true;
            }

            int originRoom = FastReader.nextInt();
            int destinationRoom = FastReader.nextInt();
            while (originRoom != -1) {
                catRooms[originRoom - 1][destinationRoom - 1] = true;
                originRoom = FastReader.nextInt();
                destinationRoom = FastReader.nextInt();
            }

            List<Edge> mouseEdges = new ArrayList<>();
            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                originRoom = Integer.parseInt(data[0]) - 1;
                destinationRoom = Integer.parseInt(data[1]) - 1;

                mouseRooms[originRoom][destinationRoom] = true;
                mouseEdges.add(new Edge(originRoom, destinationRoom));
                line = FastReader.getLine();
            }

            Result result = computePaths(catRooms, mouseRooms, mouseEdges, catHome, mouseHome);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(result.catAndMouseMeet + " " + result.canMouseAvoidCat);
        }
        outputWriter.flush();
    }

    private static Result computePaths(boolean[][] catRooms, boolean[][] mouseRooms, List<Edge> mouseEdges,
                                       int catHome, int mouseHome) {
        transitiveClosure(catRooms);
        transitiveClosure(mouseRooms);

        char catAndMouseMeet = catAndMouseMeet(catRooms, mouseRooms, catHome, mouseHome) ? 'Y' : 'N';
        char canMouseAvoidCat = canMouseAvoidCat(catRooms, mouseEdges, catHome, mouseHome) ? 'Y' : 'N';
        return new Result(catAndMouseMeet, canMouseAvoidCat);
    }

    private static boolean catAndMouseMeet(boolean[][] catRooms, boolean[][] mouseRooms, int catHome, int mouseHome) {
        for (int roomID = 0; roomID < catRooms.length; roomID++) {
            if (catRooms[catHome][roomID] && mouseRooms[mouseHome][roomID]) {
                return true;
            }
        }
        return false;
    }

    private static boolean canMouseAvoidCat(boolean[][] catRooms, List<Edge> mouseEdges, int catHome, int mouseHome) {
        boolean[][] mouseOnlyRooms = new boolean[catRooms.length][catRooms.length];

        for (Edge edge : mouseEdges) {
            if (catRooms[catHome][edge.roomID2]) {
                continue;
            }
            mouseOnlyRooms[edge.roomID1][edge.roomID2] = true;
        }

        transitiveClosure(mouseOnlyRooms);
        return mouseOnlyRooms[mouseHome][mouseHome];
    }

    private static void transitiveClosure(boolean[][] rooms) {
        for (int vertex1 = 0; vertex1 < rooms.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < rooms.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < rooms.length; vertex3++) {
                    rooms[vertex2][vertex3] |= rooms[vertex2][vertex1] && rooms[vertex1][vertex3];
                }
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
