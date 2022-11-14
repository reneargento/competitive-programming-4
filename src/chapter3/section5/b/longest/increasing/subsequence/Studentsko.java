package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/22.
 */
public class Studentsko {

    private static class Student implements Comparable<Student> {
        int originalIndex;
        int skillLevel;

        public Student(int originalIndex, int skillLevel) {
            this.originalIndex = originalIndex;
            this.skillLevel = skillLevel;
        }

        @Override
        public int compareTo(Student other) {
            if (skillLevel != other.skillLevel) {
                return Integer.compare(skillLevel, other.skillLevel);
            }
            return Integer.compare(originalIndex, other.originalIndex);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Student[] students = new Student[FastReader.nextInt()];
        int teamSize = FastReader.nextInt();

        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i, FastReader.nextInt());
        }
        int minutesRequired = computeMinutesRequired(students, teamSize);
        outputWriter.printLine(minutesRequired);
        outputWriter.flush();
    }

    private static int computeMinutesRequired(Student[] students, int teamSize) {
        int[] targetIndexes = new int[students.length];
        Arrays.sort(students);

        for (int i = 0; i < students.length; i++) {
            int targetIndex = i / teamSize;
            targetIndexes[students[i].originalIndex] = targetIndex;
        }
        return students.length - longestNonDecreasingSubsequence(targetIndexes);
    }

    private static int longestNonDecreasingSubsequence(int[] targetIndexes) {
        if (targetIndexes.length == 0) {
            return 0;
        }
        int[] endIndexes = new int[targetIndexes.length];
        int length = 1;

        for (int i = 1; i < targetIndexes.length; i++) {
            // Case 1 - smallest end element
            if (targetIndexes[i] < targetIndexes[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (targetIndexes[i] >= targetIndexes[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = nextElementIndex(targetIndexes, endIndexes, 0, length - 1, targetIndexes[i]);
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int nextElementIndex(int[] targetIndexes, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (targetIndexes[endIndexes[middle]] > key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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
