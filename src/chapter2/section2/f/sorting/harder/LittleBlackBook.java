package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/02/21.
 */
public class LittleBlackBook {

    private static class Record implements Comparable<Record> {
        String department;
        String title;
        String firstName;
        String lastName;
        String address;
        String homePhone;
        String workPhone;
        String campusBox;

        public Record(String department, String title, String firstName, String lastName, String address,
                      String homePhone, String workPhone, String campusBox) {
            this.department = department;
            this.title = title;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.homePhone = homePhone;
            this.workPhone = workPhone;
            this.campusBox = campusBox;
        }

        @Override
        public int compareTo(Record other) {
            return lastName.compareTo(other.lastName);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int departments = FastReader.nextInt();
        List<Record> records = new ArrayList<>();

        for (int i = 0; i < departments; i++) {
            String department = FastReader.getLine();
            String line = FastReader.getLine();

            while (line != null && !line.isEmpty()) {
                String[] values = line.split(",");
                Record record = new Record(department, values[0], values[1], values[2], values[3], values[4], values[5],
                        values[6]);
                records.add(record);

                line = FastReader.getLine();
            }
        }

        Collections.sort(records);
        printRecords(records);
    }

    private static void printRecords(List<Record> records) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (Record record : records) {
            outputWriter.printLine("----------------------------------------");
            outputWriter.printLine(record.title + " " + record.firstName + " " + record.lastName);
            outputWriter.printLine(record.address);
            outputWriter.printLine("Department: " + record.department);
            outputWriter.printLine("Home Phone: " + record.homePhone);
            outputWriter.printLine("Work Phone: " + record.workPhone);
            outputWriter.printLine("Campus Box: " + record.campusBox);
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
