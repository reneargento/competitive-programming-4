package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 02/05/23.
 */
@SuppressWarnings("unchecked")
public class TheseusAndTheMinotaur {

    private static class Result {
        List<Character> cavernsWithCandles;
        char trappedCavern;

        public Result(List<Character> cavernsWithCandles, char trappedCavern) {
            this.cavernsWithCandles = cavernsWithCandles;
            this.trappedCavern = trappedCavern;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (!line.equals("#")) {
            List<Integer>[] adjacencyList = new List[26];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int separatorIndex = line.indexOf('.');
            String firstPart = line.substring(0, separatorIndex);
            String secondPart = line.substring(separatorIndex + 2);

            String[] cavernConnections = firstPart.split(";");
            for (String cavernConnection : cavernConnections) {
                char cavernName = cavernConnection.charAt(0);
                int cavernID = getCavernID(cavernName);

                for (int j = 2; j < cavernConnection.length(); j++) {
                    char otherCavernName = cavernConnection.charAt(j);
                    int otherCavernID = getCavernID(otherCavernName);
                    adjacencyList[cavernID].add(otherCavernID);
                }
            }

            char minotaurCavernName = secondPart.charAt(0);
            char theseusCavernName = secondPart.charAt(2);
            int minotaurCavernID = getCavernID(minotaurCavernName);
            int theseusCavernID = getCavernID(theseusCavernName);

            String thirdPart = secondPart.substring(4);
            int kValue = Integer.parseInt(thirdPart);

            Result result = chaseMinotaur(adjacencyList, minotaurCavernID, theseusCavernID, kValue);
            for (char cavern : result.cavernsWithCandles) {
                outputWriter.print(cavern + " ");
            }
            outputWriter.printLine("/" + result.trappedCavern);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result chaseMinotaur(List<Integer>[] adjacencyList, int minotaurCavernID, int theseusCavernID,
                                        int kValue) {
        List<Character> cavernsWithCandles = new ArrayList<>();
        char trappedCavern = '.';
        boolean[] hasCandle = new boolean[adjacencyList.length];
        int cavernExploredCount = 0;

        while (true) {
            int currentMinotaurCavernID = minotaurCavernID;

            for (int connectedCavernID : adjacencyList[minotaurCavernID]) {
                if (connectedCavernID != theseusCavernID && !hasCandle[connectedCavernID]) {
                    minotaurCavernID = connectedCavernID;
                    break;
                }
            }
            theseusCavernID = currentMinotaurCavernID;

            if (theseusCavernID == minotaurCavernID) {
                trappedCavern = getCavernName(theseusCavernID);
                break;
            }

            cavernExploredCount++;
            if (cavernExploredCount % kValue == 0) {
                char cavernName = getCavernName(theseusCavernID);
                cavernsWithCandles.add(cavernName);
                hasCandle[theseusCavernID] = true;
            }
        }
        return new Result(cavernsWithCandles, trappedCavern);
    }

    private static char getCavernName(int cavernID) {
        return (char) ('A' + cavernID);
    }

    private static int getCavernID(char cavernName) {
        return cavernName - 'A';
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

        public void flush() {
            writer.flush();
        }
    }
}
