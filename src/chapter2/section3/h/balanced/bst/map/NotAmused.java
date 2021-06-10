package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 10/06/21.
 */
public class NotAmused {

    private static class PersonData {
        double bill;
        int lastEnterTime;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int day = 1;
        String line = FastReader.getLine();
        while (line != null) {
            if (day > 1) {
                outputWriter.printLine();
            }
            line = FastReader.getLine();
            Map<String, PersonData> nameToPersonMap = new TreeMap<>();

            while (!line.equals("CLOSE")) {
                String[] data = line.split(" ");
                String event = data[0];
                String name = data[1];
                int time = Integer.parseInt(data[2]);

                if (event.equals("ENTER")) {
                    if (!nameToPersonMap.containsKey(name)) {
                        nameToPersonMap.put(name, new PersonData());
                    }
                    nameToPersonMap.get(name).lastEnterTime = time;
                } else {
                    PersonData personData = nameToPersonMap.get(name);
                    int timeSpent = time - personData.lastEnterTime;
                    personData.bill += timeSpent * 0.1;
                }
                line = FastReader.getLine();
            }
            printReport(nameToPersonMap, day, outputWriter);
            day++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printReport(Map<String, PersonData> nameToPersonMap, int day,
                                    OutputWriter outputWriter) {
        outputWriter.printLine("Day " + day);
        for (Map.Entry<String, PersonData> personEntry : nameToPersonMap.entrySet()) {
            outputWriter.printLine(String.format("%s $%.2f", personEntry.getKey(), personEntry.getValue().bill));
        }
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
