package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/12/21.
 */
public class ClassPicture {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int numberOfStudents = Integer.parseInt(line);
            String[] students = new String[numberOfStudents];
            for (int i = 0; i < students.length; i++) {
                students[i] = FastReader.next();
            }

            int constraints = FastReader.nextInt();
            Map<String, List<String>> constraintMap = new HashMap<>();
            for (int i = 0; i < constraints; i++) {
                String student1 = FastReader.next();
                String student2 = FastReader.next();

                if (!constraintMap.containsKey(student1)) {
                    constraintMap.put(student1, new ArrayList<>());
                }
                if (!constraintMap.containsKey(student2)) {
                    constraintMap.put(student2, new ArrayList<>());
                }
                constraintMap.get(student1).add(student2);
                constraintMap.get(student2).add(student1);
            }

            Arrays.sort(students);

            String[] order = getOrder(students, constraintMap);
            if (order == null) {
                outputWriter.printLine("You all need therapy.");
            } else {
                for (int i = 0; i < order.length; i++) {
                    outputWriter.print(order[i]);

                    if (i != order.length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String[] getOrder(String[] students, Map<String, List<String>> constraintMap) {
        String[] currentOrder = new String[students.length];
        return getOrder(students, constraintMap, currentOrder, 0, 0);
    }

    private static String[] getOrder(String[] students, Map<String, List<String>> constraintMap,
                                     String[] currentOrder, int index, int mask) {
        if (mask == (1 << students.length) - 1) {
            return currentOrder;
        }

        for (int i = 0; i < students.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentOrder[index] = students[i];

                if (index > 0) {
                    if (!isPairValid(constraintMap, currentOrder, index, index - 1)) {
                        continue;
                    }
                }

                String[] order = getOrder(students, constraintMap, currentOrder, index + 1, newMask);
                if (order != null) {
                    return order;
                }
            }
        }
        return null;
    }

    private static boolean isPairValid(Map<String, List<String>> constraintMap, String[] order, int index1,
                                       int index2) {
        String currentStudent = order[index1];
        String nextStudent = order[index2];

        return !constraintMap.containsKey(currentStudent)
                || !constraintMap.get(currentStudent).contains(nextStudent);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
