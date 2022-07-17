package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/07/22.
 */
public class DistributingBallotBoxesKattis {

    private static class City implements Comparable<City> {
        double population;
        int ballotBoxes;

        public City(double population) {
            this.population = population;
            ballotBoxes = 1;
        }

        public double getRatio() {
            return population / ballotBoxes;
        }

        @Override
        public int compareTo(City other) {
            return Double.compare(other.getRatio(), getRatio());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int ballotBoxes = FastReader.nextInt();

        while (cities != -1 || ballotBoxes != -1) {
            int[] population = new int[cities];
            for (int i = 0; i < cities; i++) {
                population[i] = FastReader.nextInt();
            }
            int maxPeoplePerBox = computeMaxPeoplePerBox(population, cities, ballotBoxes);
            outputWriter.printLine(maxPeoplePerBox);

            cities = FastReader.nextInt();
            ballotBoxes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaxPeoplePerBox(int[] population, int cities, int ballotBoxes) {
        PriorityQueue<City> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < cities; i++) {
            priorityQueue.offer(new City(population[i]));
        }
        ballotBoxes -= cities;

        while (ballotBoxes > 0) {
            City nextCity = priorityQueue.poll();
            nextCity.ballotBoxes++;
            priorityQueue.offer(nextCity);
            ballotBoxes--;
        }
        City cityWithMaxPeoplePerBox = priorityQueue.peek();
        return (int) Math.ceil(cityWithMaxPeoplePerBox.getRatio());
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
