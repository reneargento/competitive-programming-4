package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class ConformityUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int frosh = FastReader.nextInt();

        while (frosh != 0) {
            Map<String, Integer> classesChosen = new HashMap<>();
            List<String> studentChoices = new ArrayList<>();
            int highestFrequencyCombination = 0;
            Set<String> mostPopularCourses = new HashSet<>();

            for (int i = 0; i < frosh; i++) {
                String[] classes = new String[5];
                for (int c = 0; c < 5; c++) {
                    classes[c] = FastReader.next();
                }
                String classesKey = getClassesKey(classes);

                int frequency = classesChosen.getOrDefault(classesKey, 0);
                frequency++;
                if (frequency > highestFrequencyCombination) {
                    highestFrequencyCombination = frequency;
                    mostPopularCourses = new HashSet<>();
                    mostPopularCourses.add(classesKey);
                } else if (frequency == highestFrequencyCombination) {
                    mostPopularCourses.add(classesKey);
                }
                classesChosen.put(classesKey, frequency);
                studentChoices.add(classesKey);
            }

            int studentsTakingMostPopularCourses = countStudentsTakingMostPopularCourses(mostPopularCourses, studentChoices);
            outputWriter.printLine(studentsTakingMostPopularCourses);
            frosh = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String getClassesKey(String[] classes) {
        Arrays.sort(classes);
        StringBuilder classesKey = new StringBuilder();
        for (int i = 0; i < classes.length; i++) {
            classesKey.append(classes[i]);

            if (i != classes.length - 1) {
                classesKey.append("-");
            }
        }
        return classesKey.toString();
    }

    private static int countStudentsTakingMostPopularCourses(Set<String> mostPopularCourses,
                                                             List<String> studentChoices) {
        int studentsTakingMostPopularCourses = 0;
        for (String studentClasses : studentChoices) {
            if (mostPopularCourses.contains(studentClasses)) {
                studentsTakingMostPopularCourses++;
            }
        }
        return studentsTakingMostPopularCourses;
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
