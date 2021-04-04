package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/04/21.
 */
public class FerryLoadingIII {

    private static class Car implements Comparable<Car> {
        int index;
        int arrivedTime;
        int deliveredTime;

        public Car(int index, int arrivedTime) {
            this.index = index;
            this.arrivedTime = arrivedTime;
        }

        @Override
        public int compareTo(Car other) {
            return Integer.compare(index, other.index);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int carsSupported = FastReader.nextInt();
            int timeToCross = FastReader.nextInt();
            int cars = FastReader.nextInt();

            int currentTime = 0;
            boolean isOnLeft = true;
            Queue<Car> carArrivalsOnLeft = new LinkedList<>();
            Queue<Car> carArrivalsOnRight = new LinkedList<>();
            List<Car> deliveryTimes = new ArrayList<>();

            for (int i = 0; i < cars; i++) {
                int timeOfArrival = FastReader.nextInt();
                String location = FastReader.next();
                Car car = new Car(i, timeOfArrival);

                if (location.equals("left")) {
                    carArrivalsOnLeft.offer(car);
                } else {
                    carArrivalsOnRight.offer(car);
                }
            }

            while (!carArrivalsOnLeft.isEmpty() || !carArrivalsOnRight.isEmpty()) {
                boolean shouldPickFromRight = shouldPickFromRight(carArrivalsOnLeft, carArrivalsOnRight, isOnLeft, currentTime);

                if (isOnLeft) {
                    if (!shouldPickFromRight) {
                        if (carArrivalsOnLeft.peek().arrivedTime > currentTime) {
                            currentTime = carArrivalsOnLeft.peek().arrivedTime;
                        }
                        transportCars(carsSupported, carArrivalsOnLeft, currentTime, timeToCross, deliveryTimes);
                        isOnLeft = false;
                    } else {
                        if (carArrivalsOnRight.peek().arrivedTime > currentTime) {
                            currentTime = carArrivalsOnRight.peek().arrivedTime;
                        }
                        currentTime += timeToCross;
                        transportCars(carsSupported, carArrivalsOnRight, currentTime, timeToCross, deliveryTimes);
                    }
                } else {
                    if (shouldPickFromRight) {
                        if (carArrivalsOnRight.peek().arrivedTime > currentTime) {
                            currentTime = carArrivalsOnRight.peek().arrivedTime;
                        }
                        transportCars(carsSupported, carArrivalsOnRight, currentTime, timeToCross, deliveryTimes);
                        isOnLeft = true;
                    } else {
                        if (carArrivalsOnLeft.peek().arrivedTime > currentTime) {
                            currentTime = carArrivalsOnLeft.peek().arrivedTime;
                        }
                        currentTime += timeToCross;
                        transportCars(carsSupported, carArrivalsOnLeft, currentTime, timeToCross, deliveryTimes);
                    }
                }
                currentTime += timeToCross;
            }

            Collections.sort(deliveryTimes);
            for (Car car : deliveryTimes) {
                outputWriter.printLine(car.deliveredTime);
            }
        }
        outputWriter.flush();
    }

    private static boolean shouldPickFromRight(Queue<Car> carArrivalsOnLeft, Queue<Car> carArrivalsOnRight,
                                               boolean isOnLeft, int currentTime) {
        return carArrivalsOnLeft.isEmpty()
                || (!isOnLeft && !carArrivalsOnRight.isEmpty() && carArrivalsOnRight.peek().arrivedTime <= currentTime)
                || (isOnLeft && !carArrivalsOnRight.isEmpty() && carArrivalsOnLeft.peek().arrivedTime > currentTime
                      && carArrivalsOnRight.peek().arrivedTime < carArrivalsOnLeft.peek().arrivedTime)
                || (!carArrivalsOnRight.isEmpty() && carArrivalsOnLeft.peek().arrivedTime > currentTime
                      && (carArrivalsOnLeft.peek().arrivedTime > carArrivalsOnRight.peek().arrivedTime
                           || (carArrivalsOnLeft.peek().arrivedTime == carArrivalsOnRight.peek().arrivedTime
                                && !isOnLeft)));
    }

    private static void transportCars(int carsSupported, Queue<Car> carArrivals, int currentTime,
                                      int timeToCross, List<Car> deliveryTimes) {
        for (int c = 0; c < carsSupported; c++) {
            if (carArrivals.isEmpty() || carArrivals.peek().arrivedTime > currentTime) {
                break;
            }
            Car car = carArrivals.poll();
            car.deliveredTime = currentTime + timeToCross;
            deliveryTimes.add(car);
        }
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
