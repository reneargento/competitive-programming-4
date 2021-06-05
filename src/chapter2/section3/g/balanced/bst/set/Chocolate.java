package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class Chocolate {

    private static class ChocolateAnalysis {
        Set<Integer> chocolatesThatOnlyPersonHas;
        Set<Integer> chocolatesThatEveryoneButPersonHas;

        ChocolateAnalysis() {
            chocolatesThatOnlyPersonHas = new HashSet<>();
            chocolatesThatEveryoneButPersonHas = new HashSet<>();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int lejuChocolatesNumber = FastReader.nextInt();
            int ronyChocolatesNumber = FastReader.nextInt();
            int sujonChocolatesNumber = FastReader.nextInt();

            Set<Integer> lejuChocolates = readChocolates(lejuChocolatesNumber);
            Set<Integer> ronyChocolates = readChocolates(ronyChocolatesNumber);
            Set<Integer> sujonChocolates = readChocolates(sujonChocolatesNumber);

            ChocolateAnalysis lejuChocolateAnalysis = new ChocolateAnalysis();
            ChocolateAnalysis ronyChocolateAnalysis = new ChocolateAnalysis();
            ChocolateAnalysis sujonChocolateAnalysis = new ChocolateAnalysis();

            verifyChocolates(lejuChocolates, ronyChocolates, sujonChocolates, lejuChocolateAnalysis,
                    ronyChocolateAnalysis, sujonChocolateAnalysis);
            verifyChocolates(ronyChocolates, lejuChocolates, sujonChocolates, ronyChocolateAnalysis,
                    lejuChocolateAnalysis, sujonChocolateAnalysis);
            verifyChocolates(sujonChocolates, ronyChocolates, lejuChocolates, sujonChocolateAnalysis,
                    ronyChocolateAnalysis, lejuChocolateAnalysis);

            outputWriter.printLine(String.format("Case #%d:", t));
            outputWriter.printLine(lejuChocolateAnalysis.chocolatesThatOnlyPersonHas.size() + " " +
                    lejuChocolateAnalysis.chocolatesThatEveryoneButPersonHas.size());
            outputWriter.printLine(ronyChocolateAnalysis.chocolatesThatOnlyPersonHas.size() + " " +
                    ronyChocolateAnalysis.chocolatesThatEveryoneButPersonHas.size());
            outputWriter.printLine(sujonChocolateAnalysis.chocolatesThatOnlyPersonHas.size() + " " +
                    sujonChocolateAnalysis.chocolatesThatEveryoneButPersonHas.size());
        }
        outputWriter.flush();
    }

    private static void verifyChocolates(Set<Integer> chocolates, Set<Integer> person2Chocolates,
                                         Set<Integer> person3Chocolates, ChocolateAnalysis chocolateAnalysisPerson1,
                                         ChocolateAnalysis chocolateAnalysisPerson2,
                                         ChocolateAnalysis chocolateAnalysisPerson3) {
        for (int chocolate : chocolates) {
            boolean person2HasChocolate = person2Chocolates.contains(chocolate);
            boolean person3HasChocolate = person3Chocolates.contains(chocolate);

            if (person2HasChocolate && person3HasChocolate) {
                continue;
            }

            if (!person2HasChocolate && !person3HasChocolate) {
                chocolateAnalysisPerson1.chocolatesThatOnlyPersonHas.add(chocolate);
            } else if (person2HasChocolate) {
                chocolateAnalysisPerson3.chocolatesThatEveryoneButPersonHas.add(chocolate);
            } else {
                chocolateAnalysisPerson2.chocolatesThatEveryoneButPersonHas.add(chocolate);
            }
        }
    }

    private static Set<Integer> readChocolates(int numberOfChocolates) throws IOException {
        Set<Integer> chocolates = new HashSet<>();
        for (int i = 0; i < numberOfChocolates; i++) {
            chocolates.add(FastReader.nextInt());
        }
        return chocolates;
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
