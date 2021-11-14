package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class CubePainting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String cubes = FastReader.getLine();

        while (cubes != null) {
            String cube1 = cubes.substring(0, 6);
            String cube2 = cubes.substring(6);

            boolean canBeObtained = canBeObtained(cube1, cube2);
            if (canBeObtained) {
                outputWriter.printLine("TRUE");
            } else {
                outputWriter.printLine("FALSE");
            }
            cubes = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean canBeObtained(String cube1, String cube2) {
        for (int i = 0; i < 4; i++) {
            cube2 = rotateAroundVertical(cube2);
            if (cube1.equals(cube2)) {
                return true;
            }

            for (int j = 0; j < 4; j++) {
                cube2 = rotateAroundHorizontal(cube2);
                if (cube1.equals(cube2)) {
                    return true;
                }

                for (int k = 0; k < 4; k++) {
                    cube2 = rotateAroundVertical(cube2);
                    if (cube1.equals(cube2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String rotateAroundVertical(String cube) {
        char[] colors = cube.toCharArray();
        char[] newColors = new char[6];

        newColors[0] = colors[0];
        newColors[5] = colors[5];
        newColors[1] = colors[2];
        newColors[2] = colors[4];
        newColors[4] = colors[3];
        newColors[3] = colors[1];
        return new String(newColors);
    }

    private static String rotateAroundHorizontal(String cube) {
        char[] colors = cube.toCharArray();
        char[] newColors = new char[6];

        newColors[2] = colors[2];
        newColors[3] = colors[3];
        newColors[0] = colors[1];
        newColors[1] = colors[5];
        newColors[5] = colors[4];
        newColors[4] = colors[0];
        return new String(newColors);
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
