package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/03/21.
 */
public class ExtraordinarilyTiredStudents {

    private static class Student {
        int awakenDuration;
        int sleepingDuration;
        int checkFriendsTime;
        int nextSleepStartTime;
        int nextSleepEndTime;

        public Student(int awakenDuration, int sleepingDuration, int startTime) {
            this.awakenDuration = awakenDuration;
            this.sleepingDuration = sleepingDuration;

            if (startTime <= awakenDuration) {
                checkFriendsTime = (awakenDuration - startTime) + 1;
                nextSleepStartTime = Integer.MAX_VALUE;
                nextSleepEndTime = Integer.MAX_VALUE;
            } else {
                int nextAwakeTime = (awakenDuration + sleepingDuration) - startTime + 2;
                checkFriendsTime = nextAwakeTime + awakenDuration - 1;
                nextSleepStartTime = 1;
                nextSleepEndTime = nextSleepStartTime + ((awakenDuration + sleepingDuration) - startTime);
            }
        }

        boolean shouldCheckFriends(int time) {
            return checkFriendsTime == time;
        }

        boolean isSleeping(int time) {
            return nextSleepStartTime <= time && time <= nextSleepEndTime;
        }

        boolean justWokeUp(int time) {
            return time == nextSleepEndTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int studentsNumber = FastReader.nextInt();
        int caseNumber = 1;

        while (studentsNumber != 0) {
            Student[] students = new Student[studentsNumber];

            for (int i = 0; i < students.length; i++) {
                int awakenTime = FastReader.nextInt();
                int sleepingTime = FastReader.nextInt();
                int startTime = FastReader.nextInt();
                students[i] = new Student(awakenTime, sleepingTime, startTime);
            }

            int allAwakenTime = getAllAwakenTime(students);
            outputWriter.printLine(String.format("Case %d: %d", caseNumber, allAwakenTime));

            caseNumber++;
            studentsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getAllAwakenTime(Student[] students) {
        for (int time = 1; time <= 1000; time++) {
            int sleepingStudents = countSleepingStudents(students, time);
            if (sleepingStudents == 0) {
                return time;
            }

            for (Student student : students) {
                if (student.shouldCheckFriends(time)) {
                    int sleepingStudentsIncludingHimself = sleepingStudents;
                    if (student.justWokeUp(time)) {
                        sleepingStudentsIncludingHimself--;
                    }

                    if (sleepingStudentsIncludingHimself == 0) {
                        return time;
                    }

                    if (sleepingStudentsIncludingHimself > students.length / 2) {
                        student.nextSleepStartTime = time + 1;
                        student.nextSleepEndTime = student.nextSleepStartTime + student.sleepingDuration - 1;
                        student.checkFriendsTime = time + student.sleepingDuration + student.awakenDuration;
                    } else {
                        student.checkFriendsTime = time + student.awakenDuration;
                    }
                }
            }
        }
        return -1;
    }

    private static int countSleepingStudents(Student[] students, int time) {
        int count = 0;
        for (Student student : students) {
            if (student.isSleeping(time)) {
                count++;
            }
        }
        return count;
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
