package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class CommandoWar {

    private static class Soldier implements Comparable<Soldier> {
        int briefTime;
        int jobTime;

        public Soldier(int briefTime, int jobTime) {
            this.briefTime = briefTime;
            this.jobTime = jobTime;
        }

        @Override
        public int compareTo(Soldier other) {
            return Integer.compare(other.jobTime, jobTime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int soldiersNumber = FastReader.nextInt();
        int caseId = 1;

        while (soldiersNumber != 0) {
            Soldier[] soldiers = new Soldier[soldiersNumber];
            for (int i = 0; i < soldiers.length; i++) {
                soldiers[i] = new Soldier(FastReader.nextInt(), FastReader.nextInt());
            }
            long timeToCompleteAllJobs = computeTimeToCompleteAllJobs(soldiers);
            outputWriter.printLine(String.format("Case %d: %d", caseId, timeToCompleteAllJobs));

            caseId++;
            soldiersNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeTimeToCompleteAllJobs(Soldier[] soldiers) {
        long totalBriefingTime = 0;
        Arrays.sort(soldiers);

        long maxEndTime = 0;

        for (Soldier soldier : soldiers) {
            totalBriefingTime += soldier.briefTime;
            maxEndTime = Math.max(maxEndTime, totalBriefingTime + soldier.jobTime);
        }
        return maxEndTime;
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
