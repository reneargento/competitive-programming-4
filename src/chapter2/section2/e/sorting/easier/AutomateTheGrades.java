package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/02/21.
 */
public class AutomateTheGrades {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int term1 = FastReader.nextInt();
            int term2 = FastReader.nextInt();
            int finalExam = FastReader.nextInt();
            int attendance = FastReader.nextInt();
            int[] classTests = new int[3];
            for (int i = 0; i < classTests.length; i++) {
                classTests[i] = FastReader.nextInt();
            }

            char grade = getGrade(term1, term2, finalExam, attendance, classTests);
            System.out.printf("Case %d: %c\n", t, grade);
        }
    }

    private static char getGrade(int term1, int term2, int finalExam, int attendance, int[] classTests) {
        Arrays.sort(classTests);

        int totalScore = term1 + term2 + finalExam + attendance + ((classTests[1] + classTests[2]) / 2);
        if (totalScore >= 90) {
            return 'A';
        } if (totalScore >= 80) {
            return 'B';
        } if (totalScore >= 70) {
            return 'C';
        } if (totalScore >= 60) {
            return 'D';
        }
        return 'F';
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
}
