package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/02/21.
 */
public class AClassyProblem {

    private static class Person implements Comparable<Person> {
        String name;
        String[] classDescription;

        public Person(String name, String[] classDescription) {
            this.name = name;
            this.classDescription = classDescription;
        }

        @Override
        public int compareTo(Person other) {
            int length1 = classDescription.length;
            int length2 = other.classDescription.length;
            int maxLength = Math.max(length1, length2);

            for (int i = 0; i < maxLength; i++) {
                int index1 = length1 - 1 - i;
                int index2 = length2 - 1 - i;

                int class1 = getClassValue(classDescription, index1);
                int class2 = getClassValue(other.classDescription, index2);

                if (class1 != class2) {
                    return class1 - class2;
                }
            }
            return name.compareTo(other.name);
        }
    }

    private static int getClassValue(String[] classDescription, int index) {
        if (index < 0) {
            return 1;
        }

        switch (classDescription[index]) {
            case "upper": return 0;
            case "middle": return 1;
            default: return 2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int population = FastReader.nextInt();
            Person[] people = new Person[population];

            for (int i = 0; i < people.length; i++) {
                String[] values = FastReader.getLine().split(" ");
                String name = values[0].substring(0, values[0].length() - 1);
                String[] classDescription = values[1].split("-");
                people[i] = new Person(name, classDescription);
            }

            Arrays.sort(people);

            for (Person person : people) {
                outputWriter.printLine(person.name);
            }
            outputWriter.printLine("==============================");
        }
        outputWriter.flush();
        outputWriter.close();
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
