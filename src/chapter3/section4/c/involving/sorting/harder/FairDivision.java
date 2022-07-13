package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/07/22.
 */
public class FairDivision {

    private static class PersonData {
        int index;
        int maximumPay;
        int amountToPay;

        public PersonData(int index, int maximumPay) {
            this.index = index;
            this.maximumPay = maximumPay;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int price = FastReader.nextInt();
            PersonData[] personData = new PersonData[FastReader.nextInt()];
            long totalPossiblePay = 0;

            for (int i = 0; i < personData.length; i++) {
                personData[i] = new PersonData(i, FastReader.nextInt());
                totalPossiblePay += personData[i].maximumPay;
            }

            if (totalPossiblePay < price) {
                outputWriter.printLine("IMPOSSIBLE");
            } else {
                computePayments(price, personData);
                outputWriter.print(personData[0].amountToPay);
                for (int i = 1; i < personData.length; i++) {
                    outputWriter.print(" " + personData[i].amountToPay);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static void computePayments(int price, PersonData[] personData) {
        sortByPay(personData);
        int peopleRemaining = personData.length;
        int paymentPerPerson = price / peopleRemaining;
        int paymentExtra = price % peopleRemaining;

        for (int i = 0; i < personData.length; i++) {
            if (paymentPerPerson > personData[i].maximumPay) {
                personData[i].amountToPay = personData[i].maximumPay;
                price -= personData[i].maximumPay;
                peopleRemaining--;

                paymentPerPerson = price / peopleRemaining;
                paymentExtra = price % peopleRemaining;
            } else {
                for (int j = personData.length - 1; paymentExtra > 0; j--) {
                    personData[j].amountToPay++;
                    paymentExtra--;
                }

                for (int j = i; j < personData.length; j++) {
                    personData[j].amountToPay += paymentPerPerson;
                }
                break;
            }
        }
        sortByIndex(personData);
    }

    private static void sortByPay(PersonData[] personData) {
        Arrays.sort(personData, new Comparator<PersonData>() {
            @Override
            public int compare(PersonData personData1, PersonData personData2) {
                if (personData1.maximumPay != personData2.maximumPay) {
                    return Integer.compare(personData1.maximumPay, personData2.maximumPay);
                }
                return Integer.compare(personData2.index, personData1.index);
            }
        });
    }

    private static void sortByIndex(PersonData[] personData) {
        Arrays.sort(personData, new Comparator<PersonData>() {
            @Override
            public int compare(PersonData personData1, PersonData personData2) {
                return Integer.compare(personData1.index, personData2.index);
            }
        });
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
