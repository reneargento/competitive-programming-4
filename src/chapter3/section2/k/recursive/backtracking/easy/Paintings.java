package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/02/22.
 */
public class Paintings {

    private static class PaintingResult {
        int numberOfPaintings;
        String[] favoritePainting;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] colors = new String[FastReader.nextInt()];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = FastReader.next();
            }

            int restrictions = FastReader.nextInt();
            Map<String, List<String>> forbiddenPairs = new HashMap<>();
            for (int i = 0; i < restrictions; i++) {
                String color1 = FastReader.next();
                String color2 = FastReader.next();

                List<String> color1Restrictions = new ArrayList<>();
                color1Restrictions = forbiddenPairs.getOrDefault(color1, color1Restrictions);
                color1Restrictions.add(color2);
                forbiddenPairs.put(color1, color1Restrictions);

                List<String> color2Restrictions = new ArrayList<>();
                color2Restrictions = forbiddenPairs.getOrDefault(color2, color2Restrictions);
                color2Restrictions.add(color1);
                forbiddenPairs.put(color2, color2Restrictions);
            }

            PaintingResult paintingResult = new PaintingResult();
            String[] currentColors = new String[colors.length];
            computePaintings(colors, forbiddenPairs, currentColors, 0, 0, paintingResult);

            outputWriter.printLine(paintingResult.numberOfPaintings);
            for (int i = 0; i < paintingResult.favoritePainting.length; i++) {
                outputWriter.print(paintingResult.favoritePainting[i]);

                if (i != paintingResult.favoritePainting.length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void computePaintings(String[] colors, Map<String, List<String>> forbiddenPairs,
                                         String[] currentColors, int colorIndex, int maskSelected,
                                         PaintingResult paintingResult) {
        if (colorIndex == colors.length) {
            if (paintingResult.favoritePainting == null) {
                String[] colorsCopy = new String[currentColors.length];
                System.arraycopy(currentColors, 0, colorsCopy, 0, currentColors.length);
                paintingResult.favoritePainting = colorsCopy;
            }
            paintingResult.numberOfPaintings++;
            return;
        }

        String previousColor = null;
        List<String> forbiddenColors = null;
        if (colorIndex > 0) {
            previousColor = currentColors[colorIndex - 1];
            forbiddenColors = forbiddenPairs.getOrDefault(previousColor, new ArrayList<>());
        }
        for (int i = 0; i < colors.length; i++) {
            if ((maskSelected & (1 << i)) == 0) {
                if (previousColor == null
                        || !forbiddenColors.contains(colors[i])) {
                    int newMask = maskSelected | (1 << i);
                    currentColors[colorIndex] = colors[i];
                    computePaintings(colors, forbiddenPairs, currentColors, colorIndex + 1, newMask, paintingResult);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
