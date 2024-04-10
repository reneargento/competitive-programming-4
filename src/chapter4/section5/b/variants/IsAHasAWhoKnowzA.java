package chapter4.section5.b.variants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/24.
 */
public class IsAHasAWhoKnowzA {

    private static final String IS_A = "is-a";
    private static final int MAX_CLASSES = 500;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int relationships = FastReader.nextInt();
        int queries = FastReader.nextInt();
        Map<String, Integer> classNameToIDMap = new HashMap<>();
        boolean[][] isARelations = new boolean[MAX_CLASSES][MAX_CLASSES];
        boolean[][] hasARelations = new boolean[MAX_CLASSES][MAX_CLASSES];
        boolean[][] isHadByRelations = new boolean[MAX_CLASSES][MAX_CLASSES];
        for (int classID = 0; classID < hasARelations.length; classID++) {
            isARelations[classID][classID] = true;
        }

        for (int i = 0; i < relationships; i++) {
            int classID1 = getClassID(classNameToIDMap, FastReader.next());
            boolean isARelation = FastReader.next().equals(IS_A);
            int classID2 = getClassID(classNameToIDMap, FastReader.next());

            if (isARelation) {
                isARelations[classID1][classID2] = true;
            } else {
                hasARelations[classID1][classID2] = true;
                isHadByRelations[classID2][classID1] = true;
            }
        }

        transitiveClosure(isARelations);
        computeTransitiveHasARelations(isARelations, hasARelations, isHadByRelations);
        transitiveClosure(hasARelations);

        for (int q = 1; q <= queries; q++) {
            int classID1 = getClassID(classNameToIDMap, FastReader.next());
            boolean isARelation = FastReader.next().equals(IS_A);
            int classID2 = getClassID(classNameToIDMap, FastReader.next());
            boolean result;

            if (isARelation) {
                result = isARelations[classID1][classID2];
            } else {
                result = hasARelations[classID1][classID2];
            }
            outputWriter.printLine(String.format("Query %d: %b", q, result));
        }
        outputWriter.flush();
    }

    private static int getClassID(Map<String, Integer> classNameToIDMap, String className) {
        if (!classNameToIDMap.containsKey(className)) {
            classNameToIDMap.put(className, classNameToIDMap.size());
        }
        return classNameToIDMap.get(className);
    }

    private static void transitiveClosure(boolean[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    distances[vertex2][vertex3] |= distances[vertex2][vertex1] && distances[vertex1][vertex3];
                }
            }
        }
    }

    private static void computeTransitiveHasARelations(boolean[][] isARelations, boolean[][] hasARelations,
                                                       boolean[][] isHadByRelations) {
        for (int classID1 = 0; classID1 < isARelations.length; classID1++) {
            for (int classID2 = 0; classID2 < isARelations.length; classID2++) {
                if (!isARelations[classID1][classID2]) {
                    continue;
                }

                for (int classID = 0; classID < isARelations.length; classID++) {
                    if (hasARelations[classID2][classID]) {
                        hasARelations[classID1][classID] = true;
                    }

                    if (isHadByRelations[classID1][classID]) {
                        hasARelations[classID][classID2] = true;
                    }
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
