package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/06/21.
 */
public class DoctorKattis {

    private static class Cat implements Comparable<Cat> {
        String name;
        int arrivalOrder;
        int infectionLevel;

        public Cat(String name, int arrivalOrder, int infectionLevel) {
            this.name = name;
            this.arrivalOrder = arrivalOrder;
            this.infectionLevel = infectionLevel;
        }

        @Override
        public int compareTo(Cat other) {
            if (infectionLevel != other.infectionLevel) {
                return Integer.compare(other.infectionLevel, infectionLevel);
            }
            return Integer.compare(arrivalOrder, other.arrivalOrder);
        }
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int commands = inputReader.readInt();

        TreeSet<Cat> catsBySeverity = new TreeSet<>();
        Map<String, Cat> nameToCatMap = new HashMap<>();
        int arrivalOrder = 0;

        for (int i = 0; i < commands; i++) {
            int type = inputReader.readInt();
            if (type == 3) {
                if (catsBySeverity.isEmpty()) {
                    outputWriter.printLine("The clinic is empty");
                } else {
                    Cat highestAttentionCat = catsBySeverity.first();
                    outputWriter.printLine(highestAttentionCat.name);
                }
            } else {
                String catName = inputReader.readString();
                if (type == 2) {
                    Cat cat = nameToCatMap.get(catName);
                    catsBySeverity.remove(cat);
                } else {
                    int infectionLevel = inputReader.readInt();
                    if (type == 0) {
                        Cat cat = new Cat(catName, arrivalOrder, infectionLevel);
                        catsBySeverity.add(cat);
                        nameToCatMap.put(catName, cat);
                        arrivalOrder++;
                    } else {
                        Cat cat = nameToCatMap.get(catName);
                        catsBySeverity.remove(cat);
                        cat.infectionLevel += infectionLevel;
                        catsBySeverity.add(cat);
                        nameToCatMap.put(catName, cat);
                    }
                }
            }
        }
        outputWriter.flush();
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
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
