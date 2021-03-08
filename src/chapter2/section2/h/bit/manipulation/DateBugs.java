package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 06/03/21.
 */
public class DateBugs {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int computers = FastReader.nextInt();
        int caseId = 1;

        while (computers != 0) {
            BitSet intersection = getComputerYears();

            for (int i = 1; i < computers; i++) {
                BitSet years = getComputerYears();
                intersection.and(years);
            }

            System.out.printf("Case #%d:\n", caseId);
            if (intersection.cardinality() == 0) {
                System.out.println("Unknown bugs detected.");
            } else {
                int firstYear = intersection.nextSetBit(0);
                System.out.printf("The actual year is %d.\n", firstYear);
            }
            System.out.println();

            computers = FastReader.nextInt();
            caseId++;
        }
    }

    private static BitSet getComputerYears() throws IOException {
        BitSet years = new BitSet();

        int startYear = FastReader.nextInt();
        int displayedYearWhenBug = FastReader.nextInt();
        int realYear = FastReader.nextInt();

        int offset = realYear - displayedYearWhenBug;

        for (int y = startYear; y < 10000; y += offset) {
            years.set(y);
        }
        return years;
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
}
