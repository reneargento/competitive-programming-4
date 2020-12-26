package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/20.
 */
public class ParkingLot {

    private static class Car implements Comparable<Car>{
        int originalPosition;
        int position;
        int order;
        int parkedPosition;
        boolean isWaitingToPark;

        public Car(int originalPosition, int position, int order) {
            this.originalPosition = originalPosition;
            this.position = position;
            this.order = order;
            isWaitingToPark = true;
        }

        void move(int positions) {
            int newPosition = position + positions;
            if (newPosition > 20) {
                newPosition -= 20;
            }
            position = newPosition;
        }

        @Override
        public int compareTo(Car otherCar) {
            if (isWaitingToPark) {
                return position - otherCar.position;
            }
            return order - otherCar.order;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            List<Car> waitingCars = new ArrayList<>();
            List<Car> parkedCars = new ArrayList<>();
            int position = FastReader.nextInt();
            int carOrder = 0;

            while (position != 99) {
                waitingCars.add(new Car(position, position, carOrder++));
                position = FastReader.nextInt();
            }
            Collections.sort(waitingCars);

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                int freePosition = Integer.parseInt(line);
                int carIndex = getNearestCarIndex(waitingCars, freePosition);
                Car car = waitingCars.get(carIndex);

                waitingCars.remove(carIndex);
                car.parkedPosition = freePosition;
                car.isWaitingToPark = false;
                parkedCars.add(car);

                int moves;
                if (freePosition > car.position) {
                    moves = Math.abs(freePosition - car.position);
                } else {
                    moves = 20 - car.position + freePosition;
                }

                moveCars(waitingCars, moves);
                Collections.sort(waitingCars);

                line = FastReader.getLine();
            }

            for (Car car : waitingCars) {
                car.isWaitingToPark = false;
            }
            parkedCars.addAll(waitingCars);
            Collections.sort(parkedCars);

            for (Car car : parkedCars) {
                System.out.printf("Original position %d ", car.originalPosition);
                if (car.parkedPosition != 0) {
                    System.out.printf("parked in %d\n", car.parkedPosition);
                } else {
                    System.out.println("did not park");
                }
            }
        }
    }

    private static int getNearestCarIndex(List<Car> waitingCars, int position) {
        for (int i = 0; i < waitingCars.size(); i++) {
            if (waitingCars.get(i).position <= position
                    && i < waitingCars.size() - 1 && waitingCars.get(i + 1).position > position) {
                return i;
            }
        }
        return waitingCars.size() - 1;
    }

    private static void moveCars(List<Car> waitingCars, int positions) {
        for (Car car : waitingCars) {
            car.move(positions);
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
