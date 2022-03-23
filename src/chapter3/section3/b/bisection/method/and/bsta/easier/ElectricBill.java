package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/03/22.
 */
public class ElectricBill {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int totalAmountToPay = FastReader.nextInt();
        int differenceInBill = FastReader.nextInt();

        while (totalAmountToPay != 0 || differenceInBill != 0) {
            long amountToPay = computeAmountToPay(totalAmountToPay, differenceInBill);
            outputWriter.printLine(amountToPay);

            totalAmountToPay = FastReader.nextInt();
            differenceInBill = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeAmountToPay(int totalAmountToPay, int differenceInBill) {
        int totalConsumption = consumptionPerPay(totalAmountToPay);
        int energyConsumptionLow = 0;
        int energyConsumptionHigh = totalConsumption;

        while (energyConsumptionLow < energyConsumptionHigh) {
            int consumption = energyConsumptionLow + (energyConsumptionHigh - energyConsumptionLow) / 2;
            int neighborConsumption = totalConsumption - consumption;

            int bill = amountToPayPerConsumption(consumption);
            int neighborBill = amountToPayPerConsumption(neighborConsumption);
            int difference = Math.abs(neighborBill - bill);

            if (difference < differenceInBill) {
                energyConsumptionHigh = consumption;
            } else if (difference > differenceInBill) {
                energyConsumptionLow = consumption;
            } else {
                return Math.min(bill, neighborBill);
            }
        }
        return -1;
    }

    private static int consumptionPerPay(int pay) {
        int consumption = 0;
        int thirdRange = 990000 * 5;
        int secondRange = 9900 * 3;
        int firstRange = 100 * 2;
        int threeRangesSum = thirdRange + secondRange + firstRange;
        int twoRangesSum = secondRange + firstRange;

        if (pay > threeRangesSum) {
            long price = pay - threeRangesSum;
            consumption += price / 7;
            pay = threeRangesSum;
        }
        if (pay > twoRangesSum) {
            long price = pay - twoRangesSum;
            consumption += price / 5;
            pay = twoRangesSum;
        }
        if (pay > firstRange) {
            long price = pay - firstRange;
            consumption += price / 3;
            pay = firstRange;
        }
        if (pay > 0) {
            consumption += pay / 2;
        }
        return consumption;
    }

    private static int amountToPayPerConsumption(int consumption) {
        int amountToPay = 0;

        if (consumption > 1000000) {
            long cwh = consumption - 1000000;
            amountToPay += cwh * 7;
            consumption = 1000000;
        }
        if (consumption > 10000) {
            long cwh = consumption - 10000;
            amountToPay += cwh * 5;
            consumption = 10000;
        }
        if (consumption > 100) {
            long cwh = consumption - 100;
            amountToPay += cwh * 3;
            consumption = 100;
        }
        if (consumption > 0) {
            amountToPay += consumption * 2;
        }
        return amountToPay;
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
