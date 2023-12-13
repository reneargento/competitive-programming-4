package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/12/23.
 */
@SuppressWarnings("unchecked")
public class TheNet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int routersNumber = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[routersNumber];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int r = 0; r < routersNumber; r++) {
                String[] data = FastReader.getLine().split("-");
                int routerID = Integer.parseInt(data[0]) - 1;

                if (data.length == 2) {
                    String[] neighbors = data[1].split(",");
                    for (String neighbor : neighbors) {
                        int neighborID = Integer.parseInt(neighbor) - 1;
                        adjacencyList[routerID].add(neighborID);
                    }
                }
            }

            outputWriter.printLine("-----");

            int routes = FastReader.nextInt();
            for (int i = 0; i < routes; i++) {
                int startRouter = FastReader.nextInt() - 1;
                int endRouter = FastReader.nextInt() - 1;

                List<Integer> route = computeRoute(adjacencyList, startRouter, endRouter);
                if (route != null) {
                    outputWriter.print(route.get(0));
                    for (int r = 1; r < route.size(); r++) {
                        outputWriter.print(" " + route.get(r));
                    }
                    outputWriter.printLine();
                } else {
                    outputWriter.printLine("connection impossible");
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeRoute(List<Integer>[] adjacencyList, int startRouter, int endRouter) {
        int[] parent = new int[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startRouter);
        visited[startRouter] = true;

        while (!queue.isEmpty()) {
            int router = queue.poll();
            if (router == endRouter) {
                break;
            }

            for (int neighbor : adjacencyList[router]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = router;
                    queue.offer(neighbor);
                }
            }
        }

        if (parent[endRouter] == -1) {
            return null;
        }
        return createRoute(endRouter, parent);
    }

    private static List<Integer> createRoute(int endRouter, int[] parent) {
        List<Integer> route = new ArrayList<>();
        Deque<Integer> routerStack = new ArrayDeque<>();

        for (int router = endRouter; router != -1; router = parent[router]) {
            routerStack.push(router);
        }

        while (!routerStack.isEmpty()) {
            route.add(routerStack.pop() + 1);
        }
        return route;
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
