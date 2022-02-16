package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/02/22.
 */
public class Transportation {

    private static class TicketOrder {
        int startStation;
        int destinationStation;
        int passengers;
        long price;

        public TicketOrder(int startStation, int destinationStation, int passengers) {
            this.startStation = startStation;
            this.destinationStation = destinationStation;
            this.passengers = passengers;
            price = destinationStation - startStation;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int capacity = FastReader.nextInt();
        int finalDestination = FastReader.nextInt();
        int orders = FastReader.nextInt();

        while (capacity != 0 || finalDestination != 0 || orders != 0) {
            TicketOrder[] ticketOrders = new TicketOrder[orders];
            for (int i = 0; i < ticketOrders.length; i++) {
                ticketOrders[i] = new TicketOrder(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }
            Arrays.sort(ticketOrders, new Comparator<TicketOrder>() {
                @Override
                public int compare(TicketOrder ticketOrder1, TicketOrder ticketOrder2) {
                    return Integer.compare(ticketOrder1.startStation, ticketOrder2.startStation);
                }
            });

            long biggestEarning = computeBiggestEarning(capacity, finalDestination, ticketOrders,
                    0, 0,  0);
            outputWriter.printLine(biggestEarning);

            capacity = FastReader.nextInt();
            finalDestination = FastReader.nextInt();
            orders = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeBiggestEarning(int capacity, int finalDestination, TicketOrder[] ticketOrders,
                                              int mask, int index, int currentPassengers) {
        int earnings = 0;
        int currentStation;
        if (index < ticketOrders.length) {
            currentStation = ticketOrders[index].startStation;
        } else {
            currentStation = finalDestination;
        }

        for (int i = 0; i < ticketOrders.length; i++) {
            if ((mask & (1 << i)) != 0
                    && ticketOrders[i].destinationStation <= currentStation) {
                TicketOrder ticket = ticketOrders[i];
                int passengers = ticket.passengers;
                currentPassengers -= passengers;
                earnings += passengers * ticket.price;
                mask = mask & ~(1 << i);
            }
        }

        if (index == ticketOrders.length) {
            return earnings;
        }

        TicketOrder nextTicketOrder = ticketOrders[index];
        int updatedPassengers = currentPassengers;
        long earningsWithOrder = 0;

        if (updatedPassengers + nextTicketOrder.passengers <= capacity) {
            updatedPassengers += nextTicketOrder.passengers;
            int maskWithTicket = mask | (1 << index);
            earningsWithOrder = computeBiggestEarning(capacity, finalDestination, ticketOrders,
                    maskWithTicket,index + 1, updatedPassengers);
        }

        long earningsWithoutOrder = computeBiggestEarning(capacity, finalDestination, ticketOrders,
                mask, index + 1, currentPassengers);

        return earnings + Math.max(earningsWithOrder, earningsWithoutOrder);
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
