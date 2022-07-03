package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class ForeignExchange {

    private static class ExchangeInformation implements Comparable<ExchangeInformation> {
        int origin;
        int destination;
        int frequency;

        public ExchangeInformation(int origin, int destination, int frequency) {
            this.origin = origin;
            this.destination = destination;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(ExchangeInformation other) {
            if (origin != other.origin) {
                return Integer.compare(origin, other.origin);
            }
            return Integer.compare(destination, other.destination);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int candidates = FastReader.nextInt();

        while (candidates != 0) {
            ExchangeInformation[] exchangeInformationList = new ExchangeInformation[candidates];
            for (int i = 0; i < exchangeInformationList.length; i++) {
                exchangeInformationList[i] = new ExchangeInformation(FastReader.nextInt(), FastReader.nextInt(), 1);
            }

            Map<Integer, List<ExchangeInformation>> destinationToOriginMap = createDestinationToOriginMap(exchangeInformationList);
            boolean isFeasible = checkProgramFeasibility(exchangeInformationList, destinationToOriginMap);
            outputWriter.printLine(isFeasible ? "YES" : "NO");
            candidates = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Map<Integer, List<ExchangeInformation>> createDestinationToOriginMap(ExchangeInformation[] exchangeInformationList) {
        Arrays.sort(exchangeInformationList);

        Map<Integer, List<ExchangeInformation>> destinationToOriginMap = new HashMap<>();
        for (int i = 0; i < exchangeInformationList.length; i++) {
            int origin = exchangeInformationList[i].origin;
            int destination = exchangeInformationList[i].destination;
            int frequency = 1;

            for (int j = i + 1; j < exchangeInformationList.length
                    && exchangeInformationList[j].origin == origin
                    && exchangeInformationList[j].destination == destination; j++) {
                frequency++;
                i++;
            }

            if (!destinationToOriginMap.containsKey(destination)) {
                destinationToOriginMap.put(destination, new ArrayList<>());
            }
            List<ExchangeInformation> origins = destinationToOriginMap.get(destination);
            origins.add(new ExchangeInformation(origin, destination, frequency));
        }
        return destinationToOriginMap;
    }

    private static boolean checkProgramFeasibility(ExchangeInformation[] exchangeInformationList,
                                                   Map<Integer, List<ExchangeInformation>> destinationToOriginMap) {
        for (ExchangeInformation exchangeInformation : exchangeInformationList) {
            int origin = exchangeInformation.origin;
            if (!destinationToOriginMap.containsKey(origin)) {
                return false;
            }
            List<ExchangeInformation> destinations = destinationToOriginMap.get(origin);
            ExchangeInformation itemFound = binarySearch(destinations, exchangeInformation.destination);
            if (itemFound == null || itemFound.frequency <= 0) {
                return false;
            }
            itemFound.frequency -= 1;
        }
        return true;
    }

    private static ExchangeInformation binarySearch(List<ExchangeInformation> destinations, int targetDestination) {
        int low = 0;
        int high = destinations.size() - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            int currentDestination = destinations.get(middle).origin;
            if (currentDestination < targetDestination) {
                low = middle + 1;
            } else if (currentDestination > targetDestination) {
                high = middle - 1;
            } else {
                return destinations.get(middle);
            }
        }
        return null;
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