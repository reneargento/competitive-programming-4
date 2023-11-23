package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
@SuppressWarnings("unchecked")
public class HorrorList {

    private static class State {
        int movieID;
        int horrorIndex;

        public State(int movieID, int horrorIndex) {
            this.movieID = movieID;
            this.horrorIndex = horrorIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numberOfMovies = FastReader.nextInt();
        int[] moviesInHorrorList = new int[FastReader.nextInt()];
        int similarities = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[numberOfMovies];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < moviesInHorrorList.length; i++) {
            moviesInHorrorList[i] = FastReader.nextInt();
        }
        for (int i = 0; i < similarities; i++) {
            int movieID1 = FastReader.nextInt();
            int movieID2 = FastReader.nextInt();
            adjacencyList[movieID1].add(movieID2);
            adjacencyList[movieID2].add(movieID1);
        }

        int highestHorrorIndexMovie = computeHighestHorrorIndexMovie(adjacencyList, moviesInHorrorList);
        outputWriter.printLine(highestHorrorIndexMovie);
        outputWriter.flush();
    }

    private static int computeHighestHorrorIndexMovie(List<Integer>[] adjacencyList, int[] moviesInHorrorList) {
        int[] horrorIndexes = new int[adjacencyList.length];
        Arrays.fill(horrorIndexes, Integer.MAX_VALUE);

        boolean[] visited = new boolean[adjacencyList.length];

        Queue<State> queue = new LinkedList<>();
        for (int movieID : moviesInHorrorList) {
            State state = new State(movieID, 0);
            queue.offer(state);
            horrorIndexes[movieID] = 0;
            visited[movieID] = true;
        }

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            for (int neighborID : adjacencyList[currentState.movieID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    int nextHorrorIndex = currentState.horrorIndex + 1;
                    horrorIndexes[neighborID] = nextHorrorIndex;
                    queue.offer(new State(neighborID, nextHorrorIndex));
                }
            }
        }
        return computeHighestHorrorIndexMovie(horrorIndexes);
    }

    private static int computeHighestHorrorIndexMovie(int[] horrorIndexes) {
        int highestHorrorIndex = -1;
        int highestHorrorIndexMovieID = -1;

        for (int index = 0; index < horrorIndexes.length; index++) {
            if (horrorIndexes[index] > highestHorrorIndex) {
                highestHorrorIndex = horrorIndexes[index];
                highestHorrorIndexMovieID = index;
            }
        }
        return highestHorrorIndexMovieID;
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
