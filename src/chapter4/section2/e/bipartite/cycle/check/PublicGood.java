package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/04/23.
 */
@SuppressWarnings("unchecked")
public class PublicGood {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        int walkways = FastReader.nextInt();
        for (int i = 0; i < walkways; i++) {
            int constructionSiteID1 = FastReader.nextInt() - 1;
            int constructionSiteID2 = FastReader.nextInt() - 1;
            adjacencyList[constructionSiteID1].add(constructionSiteID2);
            adjacencyList[constructionSiteID2].add(constructionSiteID1);
        }

        Boolean[] isPub = planCity(adjacencyList);
        if (isPub == null) {
            outputWriter.printLine("Impossible");
        } else {
            outputWriter.print(isPub[0] ? "pub" : "house");
            for (int i = 1; i < isPub.length; i++) {
                outputWriter.print(isPub[i] ? " pub" : " house");
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Boolean[] planCity(List<Integer>[] adjacencyList) {
        Boolean[] isPub = new Boolean[adjacencyList.length];
        for (int constructionSiteID = 0; constructionSiteID < adjacencyList.length; constructionSiteID++) {
            if (adjacencyList[constructionSiteID].isEmpty()) {
                return null;
            }

            if (isPub[constructionSiteID] == null) {
                depthFirstSearch(adjacencyList, isPub, true, constructionSiteID);
            }
        }
        return isPub;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, Boolean[] isPub, boolean shouldBePub,
                                         int constructionSiteID) {
        isPub[constructionSiteID] = shouldBePub;
        for (int neighborID : adjacencyList[constructionSiteID]) {
            if (isPub[neighborID] == null) {
                depthFirstSearch(adjacencyList, isPub, !shouldBePub, neighborID);
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
