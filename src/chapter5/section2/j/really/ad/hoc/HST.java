package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/05/25.
 */
public class HST {

    private static class Category {
        long pst;
        long gst;
        long hst;

        public Category(long pst, long gst, long hst) {
            this.pst = pst;
            this.gst = gst;
            this.hst = hst;
        }
    }

    private static final double EPSILON = .000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Map<String, Integer> categoryNameToId = new HashMap<>();
            Category[] categories = new Category[FastReader.nextInt()];
            int purchases = FastReader.nextInt();
            long taxDifference = 0;

            for (int c = 0; c < categories.length; c++) {
                String[] data = FastReader.getLine().split(" ");
                String categoryName = data[0];
                categoryNameToId.put(categoryName, c);
                long pst = readPercentage(data[1]);
                long gst = readPercentage(data[2]);
                long hst = readPercentage(data[3]);
                categories[c] = new Category(pst, gst, hst);
            }

            for (int p = 0; p < purchases; p++) {
                String categoryName = FastReader.next();
                long price = readPrice(FastReader.next());
                int categoryId = categoryNameToId.get(categoryName);

                long pstTax = roundCeil(price * categories[categoryId].pst);
                long gstTax = roundCeil(price * categories[categoryId].gst);
                long hstTax = roundCeil(price * categories[categoryId].hst);
                taxDifference += hstTax - (pstTax + gstTax);
            }
            outputWriter.printLine(String.format("%.2f", taxDifference / 100.0));
        }
        outputWriter.flush();
    }

    private static long roundCeil(long value) {
        long remaining = value % 10000;
        value /= 10000;
        if (remaining >= 5000) {
            value++;
        }
        return value;
    }

    private static long readPercentage(String value) {
        double percentage = (Double.parseDouble(value.substring(0, value.length() - 1)) + EPSILON) * 100;
        return (long) percentage;
    }

    private static long readPrice(String priceString) {
        double price = (Double.parseDouble(priceString.substring(1)) + EPSILON) * 100;
        return (long) price;
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

        public void flush() {
            writer.flush();
        }
    }
}
