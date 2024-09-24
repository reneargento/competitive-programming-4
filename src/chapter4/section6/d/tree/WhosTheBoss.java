package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/07/24.
 */
@SuppressWarnings("unchecked")
public class WhosTheBoss {

    private static class Employee implements Comparable<Employee> {
        int id;
        int salary;
        int height;
        int index;

        public Employee(int id, int salary, int height) {
            this.id = id;
            this.salary = salary;
            this.height = height;
        }

        @Override
        public int compareTo(Employee other) {
            return Integer.compare(salary, other.salary);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Employee[] employees = new Employee[FastReader.nextInt()];
        int queries = FastReader.nextInt();

        for (int i = 0; i < employees.length; i++) {
            int id = FastReader.nextInt();
            int salary = FastReader.nextInt();
            int height = FastReader.nextInt();
            employees[i] = new Employee(id, salary, height);
        }

        Map<Integer, Integer> employeeIdToIndex = new HashMap<>();
        int[] bossId = new int[employees.length + 1];
        Arrays.fill(bossId, -1);
        LinkedList<Employee>[] adjacencyList = buildTree(employees, employeeIdToIndex, bossId);

        int[] subordinatesNumber = new int[employees.length + 1];
        computeSubordinatesNumber(adjacencyList, subordinatesNumber, employees.length - 1);

        for (int q = 0; q < queries; q++) {
            int employeeId = FastReader.nextInt();
            int index = employeeIdToIndex.get(employeeId);
            int bossIndex = bossId[index];
            int bossIdValue = bossIndex != -1 ? employees[bossIndex].id : 0;
            outputWriter.printLine(bossIdValue + " " + subordinatesNumber[index]);
        }
        outputWriter.flush();
    }

    private static LinkedList<Employee>[] buildTree(Employee[] employees, Map<Integer, Integer> employeeIdToIndex,
                                                    int[] bossId) {
        LinkedList<Employee>[] adjacencyList = new LinkedList[employees.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
        Arrays.sort(employees);
        for (int i = 0; i < employees.length; i++) {
            employees[i].index = i;
            employeeIdToIndex.put(employees[i].id, i);
        }

        for (int index = employees.length - 2; index >= 0; index--) {
            int boss = index + 1;
            Employee employee = employees[index];

            while (boss != -1 && employee.height > employees[boss].height) {
                boss = bossId[boss];
            }

            bossId[index] = boss;
            if (boss != -1) {
                adjacencyList[boss].add(employee);
            }
        }
        return adjacencyList;
    }

    private static void computeSubordinatesNumber(List<Employee>[] adjacencyList, int[] subordinatesNumber, int index) {
        subordinatesNumber[index] = adjacencyList[index].size();
        for (Employee employee : adjacencyList[index]) {
            computeSubordinatesNumber(adjacencyList, subordinatesNumber, employee.index);
            subordinatesNumber[index] += subordinatesNumber[employee.index];
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
