package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/11/2026.
 */
public class BrowniePointsI {

    private static class BrowniePoint {
        int x;
        int y;

        public BrowniePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Result {
        int stanScore;
        int ollieScore;

        public Result(int stanScore, int ollieScore) {
            this.stanScore = stanScore;
            this.ollieScore = ollieScore;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int browniePointsNumber = FastReader.nextInt();
        while (browniePointsNumber != 0) {
            BrowniePoint[] browniePoints = new BrowniePoint[browniePointsNumber];
            for (int i = 0; i < browniePoints.length; i++) {
                browniePoints[i] = new BrowniePoint(FastReader.nextInt(), FastReader.nextInt());
            }

            Result scores = computeScores(browniePoints);
            outputWriter.printLine(scores.stanScore + " " + scores.ollieScore);
            browniePointsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeScores(BrowniePoint[] browniePoints) {
        int crossedPointIndex = browniePoints.length / 2;
        BrowniePoint crossedPoint = browniePoints[crossedPointIndex];

        int stanScore = 0;
        int ollieScore = 0;

        for (int i = 0; i < browniePoints.length; i++) {
            BrowniePoint browniePoint = browniePoints[i];
            if (browniePoint.x == crossedPoint.x
                    || browniePoint.y == crossedPoint.y) {
                continue;
            }

            if ((browniePoint.x > crossedPoint.x && browniePoint.y > crossedPoint.y)
                    || (browniePoint.x < crossedPoint.x && browniePoint.y < crossedPoint.y) ) {
                stanScore++;
            } else {
                ollieScore++;
            }
        }
        return new Result(stanScore, ollieScore);
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