package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/08/22.
 */
public class Troublemakers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int students = FastReader.nextInt();
            int pairs = FastReader.nextInt();
            Map<Integer, Set<Integer>> studentToPairsMap = new HashMap<>();

            for (int i = 0; i < pairs; i++) {
                int student1 = FastReader.nextInt();
                int student2 = FastReader.nextInt();

                if (!studentToPairsMap.containsKey(student1)) {
                    studentToPairsMap.put(student1, new HashSet<>());
                }
                if (!studentToPairsMap.containsKey(student2)) {
                    studentToPairsMap.put(student2, new HashSet<>());
                }
                studentToPairsMap.get(student1).add(student2);
                studentToPairsMap.get(student2).add(student1);
            }

            List<Integer> kidsToMove = computeKidsToMove(students, studentToPairsMap);

            outputWriter.printLine(String.format("Case #%d: %d", t, kidsToMove.size()));
            if (kidsToMove.isEmpty()) {
                outputWriter.printLine();
            } else {
                outputWriter.print(kidsToMove.get(0));
                for (int i = 1; i < kidsToMove.size(); i++) {
                    outputWriter.print(" " + kidsToMove.get(i));
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeKidsToMove(int students, Map<Integer, Set<Integer>> studentToPairsMap) {
        Set<Integer> classroom1 = new HashSet<>();
        Set<Integer> classroom2 = new HashSet<>();

        for (int student = 1; student <= students; student++) {
            int pairsWithRoom1 = countPairsInRoom(classroom1, studentToPairsMap, student);
            int pairsWithRoom2 = countPairsInRoom(classroom2, studentToPairsMap, student);

            if (pairsWithRoom1 <= pairsWithRoom2) {
                classroom1.add(student);
            } else {
                classroom2.add(student);
            }
        }
        List<Integer> kidsToMove = new ArrayList<>(classroom2);
        Collections.sort(kidsToMove);
        return kidsToMove;
    }

    private static int countPairsInRoom(Set<Integer> classroom, Map<Integer, Set<Integer>> studentToPairsMap,
                                        int student) {
        if (!studentToPairsMap.containsKey(student)) {
            return 0;
        }

        int pairs = 0;
        Set<Integer> troublemakerPairs = studentToPairsMap.get(student);
        for (int studentInRoom : classroom) {
            if (troublemakerPairs.contains(studentInRoom)) {
                pairs++;
            }
        }
        return pairs;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
