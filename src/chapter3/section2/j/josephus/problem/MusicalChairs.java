package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/22.
 */
public class MusicalChairs {

    private static class FacultyMember {
        int position;
        int favoriteOpus;

        public FacultyMember(int position, int favoriteOpus) {
            this.position = position;
            this.favoriteOpus = favoriteOpus;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int facultyMemberNumber = FastReader.nextInt();
        List<FacultyMember> facultyMembers = new ArrayList<>();

        for (int i = 0; i < facultyMemberNumber; i++) {
            int favoriteOpus = FastReader.nextInt();
            facultyMembers.add(new FacultyMember(i, favoriteOpus));
        }
        int departmentChair = josephus(facultyMembers) + 1;
        outputWriter.printLine(departmentChair);
        outputWriter.flush();
    }

    private static int josephus(List<FacultyMember> facultyMembers) {
        int circleSize = facultyMembers.size();
        int currentFacultyMemberIndex = 0;

        for (int i = 0; i < circleSize - 1; i++) {
            FacultyMember facultyMember = facultyMembers.get(currentFacultyMemberIndex);
            currentFacultyMemberIndex = (currentFacultyMemberIndex + (facultyMember.favoriteOpus - 1))
                    % facultyMembers.size();

            facultyMembers.remove(currentFacultyMemberIndex);
            currentFacultyMemberIndex = currentFacultyMemberIndex % facultyMembers.size();
        }
        return facultyMembers.get(0).position;
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
