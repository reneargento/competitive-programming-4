package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/06/21.
 */
public class OpenSource {

    private static class Project implements Comparable<Project> {
        String name;
        int signups;

        public Project(String name, int signups) {
            this.name = name;
            this.signups = signups;
        }

        @Override
        public int compareTo(Project other) {
            if (signups != other.signups) {
                return Integer.compare(other.signups, signups);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line.charAt(0) != '0') {
            Map<String, Set<String>> projectToStudentIdsMap = new HashMap<>();
            Map<String, String> signedUpStudentsToProjects = new HashMap<>();
            Set<String> studentsToIgnore = new HashSet<>();
            String currentProject = null;

            while (line.charAt(0) != '1') {
                if (Character.isUpperCase(line.charAt(0))) {
                    currentProject = line;
                    projectToStudentIdsMap.put(currentProject, new HashSet<>());
                } else {
                    String studentId = line;
                    projectToStudentIdsMap.get(currentProject).add(studentId);

                    if (signedUpStudentsToProjects.containsKey(studentId)
                            && !signedUpStudentsToProjects.get(studentId).equals(currentProject)) {
                        studentsToIgnore.add(studentId);
                    }
                    signedUpStudentsToProjects.put(studentId, currentProject);
                }
                line = FastReader.getLine();
            }
            removeIgnoredStudents(projectToStudentIdsMap, studentsToIgnore);
            printProjectSummaries(projectToStudentIdsMap, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void removeIgnoredStudents(Map<String, Set<String>> projectToStudentIdsMap,
                                              Set<String> studentsToIgnore) {
        for (Set<String> studentIds : projectToStudentIdsMap.values()) {
            studentIds.removeAll(studentsToIgnore);
        }
    }

    private static void printProjectSummaries(Map<String, Set<String>> projectToStudentIdsMap,
                                              OutputWriter outputWriter) {
        List<Project> projects = new ArrayList<>();
        for (Map.Entry<String, Set<String>> projectData : projectToStudentIdsMap.entrySet()) {
            projects.add(new Project(projectData.getKey(), projectData.getValue().size()));
        }
        Collections.sort(projects);

        for (Project project : projects) {
            outputWriter.printLine(project.name + " " + project.signups);
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
