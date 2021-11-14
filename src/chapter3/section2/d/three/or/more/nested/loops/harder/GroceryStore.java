package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;

/**
 * Created by Rene Argento on 13/11/21.
 */
// Based on https://www.redgreencode.com/equation-solving-is-the-key-to-uva-11236/
public class GroceryStore {

    private static final long MAX_PRODUCT = 2000000000;

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int price1 = 1; price1 <= 2000; price1++) {
            if (price1 * price1 * price1 * price1 > MAX_PRODUCT) {
                break;
            }

            for (int price2 = price1; price2 <= 2000; price2++) {
                if (price1 * price2 * price2 * price2 > MAX_PRODUCT) {
                    break;
                }

                for (int price3 = price2; price3 <= 2000; price3++) {
                    if (price1 * price2 * price3 * price3 > MAX_PRODUCT) {
                        break;
                    }

                    long product = price1 * price2 * price3;
                    int centsConstant = 1000000;
                    if (product == centsConstant) {
                        continue;
                    }

                    long sum = price1 + price2 + price3;
                    long sumInCents = sum * centsConstant;
                    long productInCents = product - centsConstant;

                    if (sumInCents % productInCents != 0) {
                        continue;
                    }

                    long price4 = sumInCents / productInCents;

                    if (price3 > price4) {
                        continue;
                    }
                    if (sum + price4 > 2000) {
                        continue;
                    }
                    if (product * price4 > MAX_PRODUCT) {
                        continue;
                    }

                    double price1Double = price1 / 100.0;
                    double price2Double = price2 / 100.0;
                    double price3Double = price3 / 100.0;
                    double price4Double = price4 / 100.0;

                    outputWriter.printLine(String.format("%.2f %.2f %.2f %.2f", price1Double,
                            price2Double, price3Double, price4Double));
                }
            }
        }
        outputWriter.flush();
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
