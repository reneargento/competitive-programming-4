package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/03/23.
 */
public class AncientMessages {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final char WHITE_PIXEL = '0';
    private static final char BLACK_PIXEL = '1';
    private static final char PROCESSED_PIXEL = '2';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        int caseID = 1;

        while (rows != 0 || columns != 0) {
            char[][] image = new char[rows][columns * 4];
            for (int row = 0; row < image.length; row++) {
                String hexString = FastReader.next();
                String binaryString = hexadecimalToBinary(hexString);
                image[row] = binaryString.toCharArray();
            }

            List<Character> hieroglyphs = decipherHieroglyphs(image, neighborRows, neighborColumns);
            outputWriter.print(String.format("Case %d: ", caseID));
            for (char hieroglyph : hieroglyphs) {
                outputWriter.print(hieroglyph);
            }
            outputWriter.printLine();

            caseID++;
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Character> decipherHieroglyphs(char[][] image, int[] neighborRows, int[] neighborColumns) {
        Map<Integer, Integer> componentIDToInnerSections = new HashMap<>();
        int[][] components = new int[image.length][image[0].length];
        int componentID = 1;

        // First register all components
        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                if (image[row][column] == BLACK_PIXEL && components[row][column] == 0) {
                    registerComponent(image, components, neighborRows, neighborColumns, componentID, row, column);
                    componentID++;
                }
            }
        }

        // Then check adjacent components to identify hieroglyphs
        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                if (image[row][column] == WHITE_PIXEL) {
                    Set<Integer> adjacentComponents = new HashSet<>();
                    findAdjacentComponents(image, components, neighborRows, neighborColumns, adjacentComponents, row,
                            column);
                    if (adjacentComponents.size() == 1) {
                        int componentIDValue = adjacentComponents.iterator().next();
                        int sectionsCount = componentIDToInnerSections.getOrDefault(componentIDValue, 0);
                        componentIDToInnerSections.put(componentIDValue, sectionsCount + 1);
                    }
                }
            }
        }

        List<Character> hieroglyphs = new ArrayList<>();
        for (int componentIDValue = 1; componentIDValue < componentID; componentIDValue++) {
            int innerSections = componentIDToInnerSections.getOrDefault(componentIDValue, 0);
            switch (innerSections) {
                case 0: hieroglyphs.add('W'); break;
                case 1: hieroglyphs.add('A'); break;
                case 2: hieroglyphs.add('K'); break;
                case 3: hieroglyphs.add('J'); break;
                case 4: hieroglyphs.add('S'); break;
                default: hieroglyphs.add('D');
            }
        }
        Collections.sort(hieroglyphs);
        return hieroglyphs;
    }

    private static void registerComponent(char[][] image, int[][] components, int[] neighborRows, int[] neighborColumns,
                                          int componentID, int row, int column) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));
        components[row][column] = componentID;

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = currentCell.row + neighborRows[i];
                int neighborColumn = currentCell.column + neighborColumns[i];
                if (isValid(image, neighborRow, neighborColumn)
                        && components[neighborRow][neighborColumn] == 0
                        && image[neighborRow][neighborColumn] == BLACK_PIXEL) {
                    components[neighborRow][neighborColumn] = componentID;
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
    }

    private static void findAdjacentComponents(char[][] image, int[][] components, int[] neighborRows,
                                               int[] neighborColumns, Set<Integer> adjacentComponents, int row,
                                               int column) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));
        image[row][column] = PROCESSED_PIXEL;

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = currentCell.row + neighborRows[i];
                int neighborColumn = currentCell.column + neighborColumns[i];
                if (isValid(image, neighborRow, neighborColumn)) {
                    if (components[neighborRow][neighborColumn] != 0) {
                        adjacentComponents.add(components[neighborRow][neighborColumn]);
                    }

                    if (image[neighborRow][neighborColumn] == WHITE_PIXEL) {
                        image[neighborRow][neighborColumn] = PROCESSED_PIXEL;
                        queue.offer(new Cell(neighborRow, neighborColumn));
                    }
                } else {
                    adjacentComponents.add(-1);
                }
            }
        }
    }

    private static boolean isValid(char[][] image, int row, int column) {
        return row >= 0 && row < image.length && column >= 0 && column < image[0].length;
    }

    private static String hexadecimalToBinary(String hexString){
        hexString = hexString.replaceAll("0", "0000");
        hexString = hexString.replaceAll("1", "0001");
        hexString = hexString.replaceAll("2", "0010");
        hexString = hexString.replaceAll("3", "0011");
        hexString = hexString.replaceAll("4", "0100");
        hexString = hexString.replaceAll("5", "0101");
        hexString = hexString.replaceAll("6", "0110");
        hexString = hexString.replaceAll("7", "0111");
        hexString = hexString.replaceAll("8", "1000");
        hexString = hexString.replaceAll("9", "1001");
        hexString = hexString.replaceAll("a", "1010");
        hexString = hexString.replaceAll("b", "1011");
        hexString = hexString.replaceAll("c", "1100");
        hexString = hexString.replaceAll("d", "1101");
        hexString = hexString.replaceAll("e", "1110");
        hexString = hexString.replaceAll("f", "1111");
        return hexString;
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
