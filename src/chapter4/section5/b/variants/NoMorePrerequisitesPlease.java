package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/03/24.
 */
public class NoMorePrerequisitesPlease {

    private static class CourseData implements Comparable<CourseData> {
        String courseName;
        List<String> prerequisites;

        public CourseData(String courseName, List<String> prerequisites) {
            this.courseName = courseName;
            this.prerequisites = prerequisites;
        }

        @Override
        public int compareTo(CourseData other) {
            return courseName.compareTo(other.courseName);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int courses = FastReader.nextInt();
            Map<String, Integer> courseNameToID = new HashMap<>();
            String[] courseIDToName = new String[courses];
            boolean[][] prerequisites = new boolean[courses][courses];

            for (int c = 0; c < courses; c++) {
                getCourseID(courseNameToID, courseIDToName, FastReader.next());
            }

            int coursesWithPrerequisites = FastReader.nextInt();
            for (int i = 0; i < coursesWithPrerequisites; i++) {
                int courseID = getCourseID(courseNameToID, courseIDToName, FastReader.next());
                int prerequisitesNumber = FastReader.nextInt();
                for (int p = 0; p < prerequisitesNumber; p++) {
                    int prerequisiteID = getCourseID(courseNameToID, courseIDToName, FastReader.next());
                    prerequisites[prerequisiteID][courseID] = true;
                }
            }

            List<CourseData> directPrerequisites = computeDirectPrerequisites(prerequisites, courseIDToName);
            for (CourseData data : directPrerequisites) {
                outputWriter.print(data.courseName + " " + data.prerequisites.size());
                for (String prerequisite : data.prerequisites) {
                    outputWriter.print(" " + prerequisite);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static int getCourseID(Map<String, Integer> courseNameToID, String[] courseIDToName, String courseName) {
        if (!courseNameToID.containsKey(courseName)) {
            int courseID = courseNameToID.size();
            courseNameToID.put(courseName, courseID);
            courseIDToName[courseID] = courseName;
        }
        return courseNameToID.get(courseName);
    }

    private static List<CourseData> computeDirectPrerequisites(boolean[][] prerequisites, String[] courseIDToName) {
        List<CourseData> courseData = new ArrayList<>();
        CourseData[] allCourseData = new CourseData[prerequisites.length];
        for (int courseID = 0; courseID < prerequisites.length; courseID++) {
            allCourseData[courseID] = new CourseData(courseIDToName[courseID], new ArrayList<>());
        }

        transitiveClosure(prerequisites);
        reverseTransitiveClosure(prerequisites);

        for (int courseID1 = 0; courseID1 < prerequisites.length; courseID1++) {
            for (int courseID2 = 0; courseID2 < prerequisites.length; courseID2++) {
                if (prerequisites[courseID1][courseID2]) {
                    allCourseData[courseID2].prerequisites.add(courseIDToName[courseID1]);
                }
            }
        }

        for (int courseID = 0; courseID < prerequisites.length; courseID++) {
            if (!allCourseData[courseID].prerequisites.isEmpty()) {
                CourseData data = allCourseData[courseID];
                Collections.sort(data.prerequisites);
                courseData.add(data);
            }
        }
        Collections.sort(courseData);
        return courseData;
    }

    private static void transitiveClosure(boolean[][] prerequisites) {
        for (int vertex1 = 0; vertex1 < prerequisites.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < prerequisites.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < prerequisites.length; vertex3++) {
                    prerequisites[vertex2][vertex3] |= prerequisites[vertex2][vertex1] && prerequisites[vertex1][vertex3];
                }
            }
        }
    }

    private static void reverseTransitiveClosure(boolean[][] prerequisites) {
        for (int vertex1 = 0; vertex1 < prerequisites.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < prerequisites.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < prerequisites.length; vertex3++) {
                    if (prerequisites[vertex2][vertex1] && prerequisites[vertex1][vertex3]) {
                        prerequisites[vertex2][vertex3] = false;
                    }
                }
            }
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
