package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/06/21.
 */
public class AdministrativeDifficulties {

    private static class Spy implements Comparable<Spy> {
        String name;
        String carName;
        long priceToPay;
        boolean inconsistentLogs;

        public Spy(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Spy other) {
            return name.compareTo(other.name);
        }
    }

    private static class Car {
        int catalogPrice;
        int pickUpCost;
        int costPerKilometerDriven;

        public Car(int catalogPrice, int pickUpCost, int costPerKilometerDriven) {
            this.catalogPrice = catalogPrice;
            this.pickUpCost = pickUpCost;
            this.costPerKilometerDriven = costPerKilometerDriven;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Map<String, Car> nameToCarMap = new HashMap<>();

            int typesOfCars = FastReader.nextInt();
            int events = FastReader.nextInt();

            for (int i = 0; i < typesOfCars; i++) {
                nameToCarMap.put(FastReader.next(), new Car(FastReader.nextInt(), FastReader.nextInt(),
                        FastReader.nextInt()));
            }

            List<Spy> spyList = computeEvents(events, nameToCarMap);
            for (Spy spy : spyList) {
                outputWriter.print(spy.name + " ");
                if (spy.inconsistentLogs) {
                    outputWriter.printLine("INCONSISTENT");
                } else {
                    outputWriter.printLine(spy.priceToPay);
                }
            }
        }
        outputWriter.flush();
    }

    private static List<Spy> computeEvents(int events, Map<String, Car> nameToCarMap) throws IOException {
        Map<String, Spy> nameToSpyMap = new HashMap<>();

        for (int i = 0; i < events; i++) {
            int time = FastReader.nextInt();
            String spyName = FastReader.next();
            String event = FastReader.next();

            if (!nameToSpyMap.containsKey(spyName)) {
                nameToSpyMap.put(spyName, new Spy(spyName));
            }
            Spy spy = nameToSpyMap.get(spyName);

            if (event.equals("p")) {
                String carName = FastReader.next();
                Car car = nameToCarMap.get(carName);

                if (spy.carName != null) {
                    spy.inconsistentLogs = true;
                    continue;
                }

                spy.carName = carName;
                spy.priceToPay += car.pickUpCost;
            } else {
                if (spy.carName == null) {
                    spy.inconsistentLogs = true;
                    FastReader.nextInt();
                    continue;
                }
                Car car = nameToCarMap.get(spy.carName);

                if (event.equals("r")) {
                    long distance = FastReader.nextInt();
                    spy.priceToPay += car.costPerKilometerDriven * distance;
                    spy.carName = null;
                } else {
                    long severity = FastReader.nextInt();
                    long cost = (car.catalogPrice * severity + 99) / 100;
                    spy.priceToPay += cost;
                }
            }
        }

        List<Spy> spyList = new ArrayList<>();
        for (Spy spy : nameToSpyMap.values()) {
            if (spy.carName != null) {
                spy.inconsistentLogs = true;
            }
            spyList.add(spy);
        }
        Collections.sort(spyList);
        return spyList;
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
