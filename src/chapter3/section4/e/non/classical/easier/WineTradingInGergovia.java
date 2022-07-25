package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/07/22.
 */
public class WineTradingInGergovia {

    private static class Person {
        int index;
        int quantity;
        boolean isBuying;

        public Person(int index, int quantity, boolean isBuying) {
            this.index = index;
            this.quantity = quantity;
            this.isBuying = isBuying;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int inhabitants = FastReader.nextInt();

        while (inhabitants!= 0) {
            Person[] people = new Person[inhabitants];
            for (int i = 0; i < people.length; i++) {
                int quantity = FastReader.nextInt();
                if (quantity >= 0) {
                    people[i] = new Person(i, quantity, true);
                } else {
                    people[i] = new Person(i, -quantity, false);
                }
            }
            long minimumWorkUnits = computeMinimumWorkUnits(people);
            outputWriter.printLine(minimumWorkUnits);
            inhabitants = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMinimumWorkUnits(Person[] people) {
        long minimumWorkUnits = 0;
        Deque<Person> buyers = new ArrayDeque<>();
        Deque<Person> sellers = new ArrayDeque<>();

        for (Person person : people) {
            if (person.isBuying) {
                minimumWorkUnits += processDeque(sellers, buyers, person);
            } else {
                minimumWorkUnits += processDeque(buyers, sellers, person);
            }
        }
        return minimumWorkUnits;
    }

    private static long processDeque(Deque<Person> deque1, Deque<Person> deque2, Person person) {
        long workUnits = 0;

        while (!deque1.isEmpty() && person.quantity > 0) {
            Person nextPerson = deque1.pop();
            long quantity;
            long distance = Math.abs(person.index - nextPerson.index);

            if (person.quantity <= nextPerson.quantity) {
                quantity = person.quantity;
                nextPerson.quantity -= person.quantity;
                if (nextPerson.quantity > 0) {
                    deque1.push(nextPerson);
                }
            } else {
                quantity = nextPerson.quantity;
            }
            workUnits += quantity * distance;
            person.quantity -= quantity;
        }

        if (person.quantity > 0) {
            deque2.push(person);
        }
        return workUnits;
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
