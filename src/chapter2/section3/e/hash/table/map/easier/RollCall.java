package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/05/21.
 */
public class RollCall {

    private static class Student implements Comparable<Student> {
        String name;
        String lastName;

        public Student(String name, String lastName) {
            this.name = name;
            this.lastName = lastName;
        }

        @Override
        public int compareTo(Student other) {
            if (!lastName.equals(other.lastName)) {
                return lastName.compareTo(other.lastName);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();

        List<Student> studentList = new ArrayList<>();
        Map<String, Integer> firstNameFrequency = new HashMap<>();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            String firstName = data[0];

            Student student = new Student(firstName, data[1]);
            studentList.add(student);

            int frequency = firstNameFrequency.getOrDefault(firstName, 0);
            firstNameFrequency.put(firstName, frequency + 1);
            line = FastReader.getLine();
        }
        doRollCall(studentList, firstNameFrequency);
    }

    private static void doRollCall(List<Student> studentList, Map<String, Integer> firstNameFrequency) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        Collections.sort(studentList);

        for (Student student : studentList) {
            if (firstNameFrequency.get(student.name) == 1) {
                outputWriter.printLine(student.name);
            } else {
                outputWriter.printLine(student.name + " " + student.lastName);
            }
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
