package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/05/23.
 */
@SuppressWarnings("unchecked")
public class Boss {

    private static class Employee {
        int employeeID;

        public Employee(int employeeID) {
            this.employeeID = employeeID;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int employeesNumber = Integer.parseInt(data[0]);
            int manageRelations = Integer.parseInt(data[1]);
            int instructions = Integer.parseInt(data[2]);

            Employee[] employees = new Employee[employeesNumber];
            int[] idToIndex = new int[employeesNumber];
            List<Employee>[] adjacencyList = new List[employeesNumber];
            int[] ages = new int[employeesNumber];
            for (int i = 0; i < adjacencyList.length; i++) {
                ages[i] = FastReader.nextInt();
                adjacencyList[i] = new ArrayList<>();
                employees[i] = new Employee(i);
                idToIndex[i] = i;
            }

            for (int i = 0; i < manageRelations; i++) {
                int employeeID1 = FastReader.nextInt() - 1;
                int employeeID2 = FastReader.nextInt() - 1;
                adjacencyList[employeeID2].add(employees[employeeID1]);
            }

            for (int i = 0; i < instructions; i++) {
                String instructionType = FastReader.next();
                int employeeID = FastReader.nextInt() - 1;

                if (instructionType.equals("T")) {
                    int employeeID2 = FastReader.nextInt() - 1;
                    int index1 = idToIndex[employeeID];
                    int index2 = idToIndex[employeeID2];

                    employees[index1].employeeID = employeeID2;
                    employees[index2].employeeID = employeeID;

                    idToIndex[employeeID] = index2;
                    idToIndex[employeeID2] = index1;
                } else {
                    int index = idToIndex[employeeID];
                    int youngestManager = queryYoungestAge(adjacencyList, employees, ages, idToIndex, index);
                    if (youngestManager != Integer.MAX_VALUE) {
                        outputWriter.printLine(youngestManager);
                    } else {
                        outputWriter.printLine("*");
                    }
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int queryYoungestAge(List<Employee>[] adjacencyList, Employee[] employees, int[] ages,
                                        int[] idToIndex, int startIndex) {
        int youngestAge = Integer.MAX_VALUE;
        boolean[] visited = new boolean[adjacencyList.length];
        visited[startIndex] = true;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startIndex);

        while (!queue.isEmpty()) {
            int currentIndex = queue.poll();

            if (currentIndex != startIndex) {
                int employeeID = employees[currentIndex].employeeID;
                int age = ages[employeeID];
                if (age < youngestAge) {
                    youngestAge = age;
                }
            }

            for (Employee neighborEmployee : adjacencyList[currentIndex]) {
                int neighborIndex = idToIndex[neighborEmployee.employeeID];

                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.offer(neighborIndex);
                }
            }
        }
        return youngestAge;
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
