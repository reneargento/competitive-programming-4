package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class PerfectCubes {

    private static class NumberSet implements Comparable<NumberSet> {
        int number1;
        int number2;
        int number3;
        int cubeRoot;

        public NumberSet(int number1, int number2, int number3, int cubeRoot) {
            this.number1 = number1;
            this.number2 = number2;
            this.number3 = number3;
            this.cubeRoot = cubeRoot;
        }

        @Override
        public int compareTo(NumberSet other) {
            if (cubeRoot != other.cubeRoot) {
                return Integer.compare(cubeRoot, other.cubeRoot);
            }
            return Integer.compare(number1, other.number1);
        }
    }

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<NumberSet> numberSetList = new ArrayList<>();

        for (int i = 2; i <= 200; i++) {
            for (int j = 2; j <= 200; j++) {
                for (int k = 2; k <= 200; k++) {
                    long product = (i * i * i) + (j * j * j) + (k * k * k);
                    double cubeRoot = Math.cbrt(product);

                    if (cubeRoot > 200) {
                        break;
                    }

                    int intCubeRoot = (int) cubeRoot;
                    if (cubeRoot == intCubeRoot && i < j && j < k) {
                        numberSetList.add(new NumberSet(i, j, k, intCubeRoot));
                    }
                }
            }
        }
        Collections.sort(numberSetList);

        for (NumberSet numberSet : numberSetList) {
            outputWriter.printLine(String.format("Cube = %d, Triple = (%d,%d,%d)",
                    numberSet.cubeRoot, numberSet.number1, numberSet.number2, numberSet.number3));
        }
        outputWriter.flush();
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
