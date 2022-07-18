package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/07/22.
 */
public class Convoy {

    private static class Person implements Comparable<Person> {
        int id;
        int secondsToDrive;
        long timeAvailable;

        public Person(int id, int secondsToDrive) {
            this.id = id;
            this.secondsToDrive = secondsToDrive;
            timeAvailable = secondsToDrive;
        }

        @Override
        public int compareTo(Person other) {
            return Long.compare(timeAvailable, other.timeAvailable);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int peopleNumber = FastReader.nextInt();
        int cars = FastReader.nextInt();
        PriorityQueue<Person> peopleInHouse = new PriorityQueue<>();
        Person[] people = new Person[peopleNumber];

        for (int i = 0; i < people.length; i++) {
            Person person = new Person(i, FastReader.nextInt());
            people[i] = person;
            peopleInHouse.offer(person);
        }
        long timeToMoveAllPeople = computeTimeToMoveAllPeople(peopleInHouse, people, cars);
        outputWriter.printLine(timeToMoveAllPeople);
        outputWriter.flush();
    }

    private static long computeTimeToMoveAllPeople(PriorityQueue<Person> peopleInHousePQ, Person[] people, int cars) {
        long timeToMoveAllPeople = 0;
        int carsInHouse = cars;

        Arrays.sort(people);
        PriorityQueue<Person> peopleInStadiumPQ = new PriorityQueue<>();
        boolean[] isInStadium = new boolean[peopleInHousePQ.size()];
        int slowPeopleIndex = people.length - 1;

        while (!peopleInHousePQ.isEmpty()) {
            Person personInHouse = peopleInHousePQ.peek();
            if (isInStadium[personInHouse.id]) {
                peopleInHousePQ.poll();
                continue;
            }

            long time1 = personInHouse.timeAvailable;
            long time2 = Long.MAX_VALUE;
            if (!peopleInStadiumPQ.isEmpty()) {
                Person personInStadium = peopleInStadiumPQ.peek();
                time2 = personInStadium.timeAvailable;
            }

            if (time1 <= time2 && carsInHouse > 0) {
                timeToMoveAllPeople = Math.max(timeToMoveAllPeople, personInHouse.timeAvailable);
                peopleInHousePQ.poll();
                personInHouse.timeAvailable += (personInHouse.secondsToDrive * 2);
                peopleInStadiumPQ.offer(personInHouse);
                isInStadium[personInHouse.id] = true;
                carsInHouse--;
            } else {
                Person personInStadium = peopleInStadiumPQ.poll();
                timeToMoveAllPeople = Math.max(timeToMoveAllPeople, personInStadium.timeAvailable);

                personInStadium.timeAvailable += (personInStadium.secondsToDrive * 2);
                peopleInStadiumPQ.offer(personInStadium);
            }

            for (int i = 0; i < 4 && slowPeopleIndex >= 0; i++, slowPeopleIndex--) {
                isInStadium[people[slowPeopleIndex].id] = true;
            }
        }
        return timeToMoveAllPeople;
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
