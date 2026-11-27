package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/11/2026.
 */
public class Playball {

    private static class Player {
        int x;
        int y;
        int speed;

        public Player(int x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

    private static class Result {
        int x;
        int y;
        String status;

        public Result(int x, int y, String status) {
            this.x = x;
            this.y = y;
            this.status = status;
        }
    }

    private static final double EPSILON = .000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String playersLine = FastReader.getLine();
        int playersNumber = extractNumber(playersLine);
        Player[] players = new Player[playersNumber];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }

        String ballsLine = FastReader.getLine();
        int ballsNumber = extractNumber(ballsLine);
        for (int i = 1; i <= ballsNumber; i++) {
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();
            int c = FastReader.nextInt();
            int d = FastReader.nextInt();
            int e = FastReader.nextInt();
            int f = FastReader.nextInt();
            int g = FastReader.nextInt();

            Result result = computeBallResult(players, a, b, c, d, e, f, g);
            outputWriter.printLine(String.format("Ball %d was %s at (%d,%d)", i, result.status, result.x, result.y));
        }
        outputWriter.flush();
    }

    private static int extractNumber(String line) {
        int delimiterIndex = line.indexOf('=');
        return Integer.parseInt(line.substring(delimiterIndex + 1));
    }

    private static Result computeBallResult(Player[] players, int a, int b, int c, int d, int e, int f, int g) {
        int ballTimeToReachFloor = computeBallTime(a, b, c);
        int ballX = d * ballTimeToReachFloor + e;
        int ballY = f * ballTimeToReachFloor + g;
        if (ballX < 0 || ballY < 0) {
            return new Result(ballX, ballY, "foul");
        }

        for (Player player : players) {
            if (canPlayerReachBall(player, ballX, ballY, ballTimeToReachFloor)) {
                return new Result(ballX, ballY, "caught");
            }
        }
        return new Result(ballX, ballY, "safe");
    }

    private static int computeBallTime(int a, int b, int c) {
        double[] solutions = solveQuadraticEquation(a, b, c);
        if (solutions[0] <= 0) {
            return (int) Math.ceil(solutions[1]);
        } else {
            return (int) Math.ceil(solutions[0]);
        }
    }

    private static double[] solveQuadraticEquation(int a, int b, int c) {
        double delta = b * b - (4 * a * c);
        double solution1 = (-b + Math.sqrt(delta)) / (2 * a);
        double solution2 = (-b - Math.sqrt(delta)) / (2 * a);
        return new double[] { solution1, solution2 };
    }

    private static boolean canPlayerReachBall(Player player, int ballX, int ballY, int ballTimeToReachFloor) {
        double distanceToCross = distanceBetweenPoints(player.x, player.y, ballX, ballY);
        double timeToReachBallPosition = distanceToCross / player.speed;
        return (ballTimeToReachFloor - timeToReachBallPosition) > EPSILON;
    }

    private static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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