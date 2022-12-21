package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/22.
 */
public class PokemonGoGo {

    private static class PokeStop {
        int id;
        int x;
        int y;
        List<String> names;

        public PokeStop(int id, int x, int y, String name) {
            this.id = id;
            this.x = x;
            this.y = y;
            names = new ArrayList<>();
            names.add(name);
        }
    }

    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int pokeStopsNumber = FastReader.nextInt();
        PokeStop originPokeStop = new PokeStop(0, 0, 0, "");

        Map<Integer, PokeStop> pokeStopIdToPokeStopMap = new HashMap<>();
        Map<String, Integer> nameToIdMap = new HashMap<>();
        List<List<PokeStop>> pokemonIdToPokeStopMap = new ArrayList<>();

        pokemonIdToPokeStopMap.add(new ArrayList<>());
        pokemonIdToPokeStopMap.get(0).add(originPokeStop);
        nameToIdMap.put("", 0);
        pokeStopIdToPokeStopMap.put(0, originPokeStop);

        for (int i = 0; i < pokeStopsNumber; i++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();
            String name = FastReader.next();

            int pokeStopId = computePokeStopId(x, y);
            PokeStop pokeStop;
            if (!pokeStopIdToPokeStopMap.containsKey(pokeStopId)) {
                pokeStop = new PokeStop(i + 1, x, y, name);
                pokeStopIdToPokeStopMap.put(pokeStopId, pokeStop);
            } else {
                pokeStop = pokeStopIdToPokeStopMap.get(pokeStopId);
                if (!pokeStop.names.contains(name)) {
                    pokeStop.names.add(name);
                }
            }

            int pokemonId;
            if (!nameToIdMap.containsKey(name)) {
                pokemonId = nameToIdMap.size();
                nameToIdMap.put(name, pokemonId);
                pokemonIdToPokeStopMap.add(new ArrayList<>());
            } else {
                pokemonId = nameToIdMap.get(name);
            }
            pokemonIdToPokeStopMap.get(pokemonId).add(pokeStop);
        }
        int shortestDistance = computeShortestDistance(pokemonIdToPokeStopMap, nameToIdMap, originPokeStop,
                pokeStopsNumber + 1);
        outputWriter.printLine(shortestDistance);
        outputWriter.flush();
    }

    private static int computeShortestDistance(List<List<PokeStop>> pokemonIdToPokeStopMap,
                                               Map<String, Integer> nameToIdMap, PokeStop originPokeStop,
                                               int pokeStopsNumber) {
        int bitmaskSize = 1 << pokeStopsNumber;
        int[][] dp = new int[nameToIdMap.size()][bitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int pokemonBitmask = computeNewPokemonBitmask(0, nameToIdMap, originPokeStop);
        return computeShortestDistance(pokemonIdToPokeStopMap, nameToIdMap, originPokeStop, dp, originPokeStop,
                0, 1, pokemonBitmask);
    }

    private static int computeShortestDistance(List<List<PokeStop>> pokemonIdToPokeStopMap,
                                               Map<String, Integer> nameToIdMap, PokeStop originPokeStop,
                                               int[][] dp, PokeStop currentPokeStop, int currentPokemonId,
                                               int pokeStopBitmask, int pokemonBitmask) {
        if (pokemonBitmask == (1 << nameToIdMap.size()) - 1) {
            return computeDistance(currentPokeStop, originPokeStop);
        }
        if (dp[currentPokemonId][pokeStopBitmask] != -1) {
            return dp[currentPokemonId][pokeStopBitmask];
        }

        int shortestDistance = INFINITE;
        for (int pokemonId = 1; pokemonId < pokemonIdToPokeStopMap.size(); pokemonId++) {
            if ((pokemonBitmask & (1 << pokemonId)) == 0) {
                for (PokeStop pokeStop : pokemonIdToPokeStopMap.get(pokemonId)) {
                    int newPokemonBitmask = computeNewPokemonBitmask(pokemonBitmask, nameToIdMap, pokeStop);
                    int newPokeStopBitmask = pokeStopBitmask | (1 << pokeStop.id);
                    int distance = computeDistance(currentPokeStop, pokeStop) +
                            computeShortestDistance(pokemonIdToPokeStopMap, nameToIdMap, originPokeStop, dp, pokeStop,
                                    pokemonId, newPokeStopBitmask, newPokemonBitmask);
                    shortestDistance = Math.min(shortestDistance, distance);
                }
            }
        }
        dp[currentPokemonId][pokeStopBitmask] = shortestDistance;
        return dp[currentPokemonId][pokeStopBitmask];
    }

    private static int computeNewPokemonBitmask(int pokemonBitmask, Map<String, Integer> nameToIdMap, PokeStop pokeStop) {
        for (String name : pokeStop.names) {
            int id = nameToIdMap.get(name);
            pokemonBitmask |= (1 << id);
        }
        return pokemonBitmask;
    }

    private static int computePokeStopId(int x, int y) {
        return x * 1000 + y;
    }

    private static int computeDistance(PokeStop pokeStop1, PokeStop pokeStop2) {
        return Math.abs(pokeStop1.x - pokeStop2.x) + Math.abs(pokeStop1.y - pokeStop2.y);
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
