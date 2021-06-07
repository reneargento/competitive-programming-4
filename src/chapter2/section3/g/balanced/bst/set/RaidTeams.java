package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/06/21.
 */
public class RaidTeams {

    private static class Adventurer {
        String name;
        int[] skills;

        public Adventurer(String name, int[] skills) {
            this.name = name;
            this.skills = skills;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int adventurers = FastReader.nextInt();
        Adventurer[] adventurersBySkill1 = new Adventurer[adventurers];
        Adventurer[] adventurersBySkill2 = new Adventurer[adventurers];
        Adventurer[] adventurersBySkill3 = new Adventurer[adventurers];

        for (int i = 0; i < adventurers; i++) {
            String name = FastReader.next();
            int[] skills = {FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt()};
            Adventurer adventurer = new Adventurer(name, skills);
            adventurersBySkill1[i] = adventurer;
            adventurersBySkill2[i] = adventurer;
            adventurersBySkill3[i] = adventurer;
        }
        makeRaidTeams(adventurersBySkill1, adventurersBySkill2, adventurersBySkill3);
    }

    private static void makeRaidTeams(Adventurer[] adventurersBySkill1, Adventurer[] adventurersBySkill2,
                                      Adventurer[] adventurersBySkill3) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        Set<String> selectedAdventurers = new HashSet<>();
        sortAdventurers(adventurersBySkill1, 0);
        sortAdventurers(adventurersBySkill2, 1);
        sortAdventurers(adventurersBySkill3, 2);

        Adventurer[] selectedTeam = new Adventurer[3];
        int index1 = 0;
        int index2 = 0;
        int index3 = 0;
        while (true) {
            index1 = getAdventurerIndex(index1, adventurersBySkill1, selectedAdventurers, selectedTeam, 0);
            if (index1 == -1) {
                break;
            }
            index2 = getAdventurerIndex(index2, adventurersBySkill2, selectedAdventurers, selectedTeam, 1);
            if (index2 == -1) {
                break;
            }
            index3 = getAdventurerIndex(index3, adventurersBySkill3, selectedAdventurers, selectedTeam, 2);
            if (index3 == -1) {
                break;
            }
            printSelectedTeam(selectedTeam, outputWriter);
        }
        outputWriter.flush();
    }

    private static int getAdventurerIndex(int index, Adventurer[] adventurersBySkill,
                                          Set<String> selectedAdventurers, Adventurer[] selectedTeam,
                                          int selectedTeamIndex) {
        while (index < adventurersBySkill.length
                && selectedAdventurers.contains(adventurersBySkill[index].name)) {
            index++;
        }
        if (index < adventurersBySkill.length) {
            selectedTeam[selectedTeamIndex] = adventurersBySkill[index];
            selectedAdventurers.add(adventurersBySkill[index].name);
            return index + 1;
        } else {
            return -1;
        }
    }

    private static void sortAdventurers(Adventurer[] adventurersBySkill, int skillIndex) {
        Arrays.sort(adventurersBySkill, new Comparator<Adventurer>() {
            @Override
            public int compare(Adventurer adventurer1, Adventurer adventurer2) {
                if (adventurer1.skills[skillIndex] != adventurer2.skills[skillIndex]) {
                    return Integer.compare(adventurer2.skills[skillIndex], adventurer1.skills[skillIndex]);
                }
                return adventurer1.name.compareTo(adventurer2.name);
            }
        });
    }

    private static void printSelectedTeam(Adventurer[] selectedTeam, OutputWriter outputWriter) {
        Arrays.sort(selectedTeam, new Comparator<Adventurer>() {
            @Override
            public int compare(Adventurer adventurer1, Adventurer adventurer2) {
                return adventurer1.name.compareTo(adventurer2.name);
            }
        });
        outputWriter.printLine(selectedTeam[0].name + " " + selectedTeam[1].name + " " + selectedTeam[2].name);
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
