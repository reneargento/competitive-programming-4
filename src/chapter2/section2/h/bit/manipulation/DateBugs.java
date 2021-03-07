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
            Set<Integer> intersection = getComputerYears();

            for (int i = 1; i < computers; i++) {
                Set<Integer> years = getComputerYears();
                intersection = getIntersection(intersection, years);
            }

            System.out.printf("Case #%d:\n", caseId);
            if (intersection.isEmpty()) {
                System.out.println("Unknown bugs detected.");
            } else {
                List<Integer> sortedYears = new ArrayList<>(intersection);
                Collections.sort(sortedYears);
                System.out.printf("The actual year is %d.\n", sortedYears.get(0));
            }
            System.out.println();

            computers = FastReader.nextInt();
            caseId++;
        }
    }

    private static Set<Integer> getComputerYears() throws IOException {
        Set<Integer> years = new HashSet<>();

        int startYear = FastReader.nextInt();
        int displayedYearWhenBug = FastReader.nextInt();
        int realYear = FastReader.nextInt();

        int offset = realYear - displayedYearWhenBug;

        for (int y = startYear; y < 10000; y += offset) {
            years.add(y);
        }
        return years;
    }

    private static Set<Integer> getIntersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> intersection = new HashSet<>();

        for (Integer year : set1) {
            if (set2.contains(year)) {
                intersection.add(year);
            }
        }
        return intersection;
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
