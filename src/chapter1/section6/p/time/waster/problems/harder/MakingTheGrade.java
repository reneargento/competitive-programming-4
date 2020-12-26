package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/20.
 */
public class MakingTheGrade {

    private static class Student {
        List<Integer> tests;
        int bonuses;
        int absences;
        double average;

        public Student(List<Integer> tests, int bonuses, int absences) {
            this.tests = tests;
            this.bonuses = bonuses;
            this.absences = absences;
            average = roundValuePrecisionDigits(getAverage(), 1);
        }

        double getAverage() {
            Collections.sort(this.tests);
            double sum = 0;
            int startIndex = 0;
            int testsToConsider = tests.size();

            if (tests.size() > 2) {
                startIndex = 1;
                testsToConsider = tests.size() - 1;
            }

            for (int i = startIndex; i < tests.size(); i++) {
                sum += tests.get(i);
            }
            return sum / testsToConsider;
        }

        char getGrade(Map<Character, Double> gradeCutoffs) {
            average += (bonuses / 2) * 3;
            char grade = 'D';
            if (average >= gradeCutoffs.get('A')) {
                grade = 'A';
            } else if (average >= gradeCutoffs.get('B')) {
                grade = 'B';
            } else if (average >= gradeCutoffs.get('C')) {
                grade = 'C';
            }

            if (absences == 0) {
                grade = increaseGrade(grade);
            } else {
                int lettersToLose = absences / 4;
                for (int i = 0; i < lettersToLose; i++) {
                    grade = decreaseGrade(grade);
                }
            }
            return grade;
        }

        private char decreaseGrade(char grade) {
            switch (grade) {
                case 'A': return 'B';
                case 'B': return 'C';
                case 'C': return 'D';
                default: return 'F';
            }
        }

        private char increaseGrade(char grade) {
            switch (grade) {
                case 'A':
                case 'B':
                    return 'A';
                case 'C': return 'B';
                case 'D': return 'C';
                default: return 'D';
            }
        }

        private double roundValuePrecisionDigits(double value, int digits) {
            long valueToMultiply = (long) Math.pow(10, digits);
            return (double) Math.round(value * valueToMultiply) / valueToMultiply;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int classes = FastReader.nextInt();

        System.out.println("MAKING THE GRADE OUTPUT");
        for (int c = 0; c < classes; c++) {
            int studentsNumber = FastReader.nextInt();
            int tests = FastReader.nextInt();
            List<Student> students = new ArrayList<>();

            for (int s = 0; s < studentsNumber; s++) {
                List<Integer> testScores = new ArrayList<>();
                for (int t = 0; t < tests; t++) {
                    int score = FastReader.nextInt();
                    testScores.add(score);
                }
                int bonuses = FastReader.nextInt();
                int absences = FastReader.nextInt();
                students.add(new Student(testScores, bonuses, absences));
            }
            double mean = getMean(students);
            double standardDeviation = getStandardDeviation(students, mean);
            Map<Character, Double> gradeCutoffs = computeGradeCutoffs(mean, standardDeviation);

            double averageGradePoint = getAverageGradePoint(students, gradeCutoffs);
            System.out.printf("%.1f\n", averageGradePoint);
        }
        System.out.println("END OF OUTPUT");
    }

    private static double getMean(List<Student> students) {
        double sum = 0;
        for (int i = 0; i < students.size(); i++) {
            sum += students.get(i).average;
        }
        return sum / students.size();
    }

    private static double getStandardDeviation(List<Student> students, double mean) {
        double sumOfDifferencesSquared = 0;
        for (int i = 0; i < students.size(); i++) {
            sumOfDifferencesSquared += Math.pow(Math.abs(students.get(i).average - mean), 2);
        }
        return Math.sqrt(sumOfDifferencesSquared / students.size());
    }

    private static Map<Character, Double> computeGradeCutoffs(double mean, double standardDeviation) {
        Map<Character, Double> gradeCutoffs = new HashMap<>();
        gradeCutoffs.put('A', mean + standardDeviation);
        gradeCutoffs.put('B', mean);
        gradeCutoffs.put('C', mean - standardDeviation);
        return gradeCutoffs;
    }

    private static int getPoints(char grade) {
        switch (grade) {
            case 'A': return 4;
            case 'B': return 3;
            case 'C': return 2;
            case 'D': return 1;
            default: return 0;
        }
    }

    private static double getAverageGradePoint(List<Student> students, Map<Character, Double> gradeCutoffs) {
        int totalPoints = 0;
        for (Student student : students) {
            totalPoints += getPoints(student.getGrade(gradeCutoffs));
        }
        return totalPoints / (double) students.size();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
