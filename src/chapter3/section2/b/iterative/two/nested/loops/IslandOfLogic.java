package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/09/21.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume005/592%20-%20Island%20of%20Logic.cpp
public class IslandOfLogic {

    private static class State {
        int[] personTypes;
        int day;

        public State(int[] personTypes, int day) {
            this.personTypes = personTypes;
            this.day = day;
        }
    }

    private static class Statement {
        String authorName;
        String person;
        boolean isNot;
        String type;

        public Statement(String authorName, String person, boolean isNot, String type) {
            this.authorName = authorName;
            this.person = person;
            this.isNot = isNot;
            this.type = type;
        }
    }

    private static final int DIVINE = 0;
    private static final int EVIL = 1;
    private static final int HUMAN = 2;
    private static final int DAY = 0;
    private static final int NIGHT = 1;

    private static final String DIVINE_STATEMENT = "divine.";
    private static final String EVIL_STATEMENT = "evil.";
    private static final String HUMAN_STATEMENT = "human.";
    private static final String DAY_STATEMENT = "day.";
    private static final String NIGHT_STATEMENT = "night.";
    private static final String LYING_STATEMENT = "lying.";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<State> states = generateAllStates();

        int statementsNumber = FastReader.nextInt();
        int conversation = 1;

        while (statementsNumber != 0) {
            List<Statement> statements = new ArrayList<>();

            for (int i = 0; i < statementsNumber; i++) {
                String line = FastReader.getLine();
                String[] data = line.split(" ");
                String authorName = data[0].substring(0, 1);

                String person = data[1].substring(0, 1);
                String type;
                boolean isNot = data[3].equals("not");
                if (isNot) {
                    type = data[4];
                } else {
                    type = data[3];
                }
                statements.add(new Statement(authorName, person, isNot, type));
            }

            int[][] personTypesAndDay = getFacts(states, statements);
            boolean impossible = false;
            boolean factsExist = false;

            for (int i = 0; i < personTypesAndDay.length; i++) {
                int solutions;
                if (i < 5) {
                    solutions = personTypesAndDay[i][DIVINE]
                            + personTypesAndDay[i][EVIL] + personTypesAndDay[i][HUMAN];
                } else {
                    solutions = personTypesAndDay[i][DAY] + personTypesAndDay[i][NIGHT];
                }

                if (solutions == 0) {
                    impossible = true;
                } else if (solutions == 1) {
                    factsExist = true;
                }
            }

            outputWriter.printLine(String.format("Conversation #%d", conversation));
            if (impossible) {
                outputWriter.printLine("This is impossible.");
            } else if (factsExist) {
                for (int i = 0; i < personTypesAndDay.length; i++) {
                    int solutions;
                    if (i < 5) {
                        solutions = personTypesAndDay[i][DIVINE]
                                + personTypesAndDay[i][EVIL] + personTypesAndDay[i][HUMAN];
                    } else {
                        solutions = personTypesAndDay[i][DAY] + personTypesAndDay[i][NIGHT];
                    }

                    if (solutions == 1) {
                        if (i < 5) {
                            char person = (char) ('A' + i);
                            if (personTypesAndDay[i][DIVINE] == 1) {
                                outputWriter.printLine(String.format("%c is divine.", person));
                            } else if (personTypesAndDay[i][EVIL] == 1) {
                                outputWriter.printLine(String.format("%c is evil.", person));
                            } else {
                                outputWriter.printLine(String.format("%c is human.", person));
                            }
                        } else {
                            if (personTypesAndDay[i][DAY] == 1) {
                                outputWriter.printLine("It is day.");
                            } else {
                                outputWriter.printLine("It is night.");
                            }
                        }
                    }
                }
            } else {
                outputWriter.printLine("No facts are deducible.");
            }
            outputWriter.printLine();

            conversation++;
            statementsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<State> generateAllStates() {
        List<State> states = new ArrayList<>();
        generateAllStates(states, new int[5], 0);
        return states;
    }

    private static void generateAllStates(List<State> states, int[] personTypes, int personIndex) {
        if (personIndex == personTypes.length) {
            int[] personTypesCopy = new int[personTypes.length];
            System.arraycopy(personTypes, 0, personTypesCopy, 0, personTypes.length);

            State state1 = new State(personTypesCopy, DAY);
            State state2 = new State(personTypesCopy, NIGHT);
            states.add(state1);
            states.add(state2);
            return;
        }

        for (int personType = 0; personType < 3; personType++) {
            personTypes[personIndex] = personType;
            generateAllStates(states, personTypes, personIndex + 1);
        }
    }

    private static int[][] getFacts(List<State> states, List<Statement> statements) {
        int[][] personTypesAndDay = new int[6][3];

        for (State state : states) {
            checkStatementsWithState(statements, state, personTypesAndDay);
        }
        return personTypesAndDay;
    }

    private static void checkStatementsWithState(List<Statement> statements, State state,
                                                 int[][] personTypesAndDay) {
        for (Statement statement : statements) {
            int personId;
            if (statement.person.equals("I")) {
                personId = statement.authorName.charAt(0) - 'A';
            } else {
                personId = statement.person.charAt(0) - 'A';
            }

            int personType = state.personTypes[personId];
            int authorId = statement.authorName.charAt(0) - 'A';
            int authorType = state.personTypes[authorId];

            if (statement.isNot) {
                if (isTrueStatement(state, authorType)) {
                    if (personType == DIVINE && statement.type.equals(DIVINE_STATEMENT)) {
                        return;
                    } else if (personType == HUMAN && (statement.type.equals(HUMAN_STATEMENT)
                            || (state.day == NIGHT && statement.type.equals(LYING_STATEMENT)))) {
                        return;
                    } else if (personType == EVIL && (statement.type.equals(EVIL_STATEMENT)
                            || statement.type.equals(LYING_STATEMENT))) {
                        return;
                    }

                    if (state.day == DAY && statement.type.equals(DAY_STATEMENT)) {
                        return;
                    } else if (state.day == NIGHT && statement.type.equals(NIGHT_STATEMENT)) {
                        return;
                    }
                } else {
                    if (personType == DIVINE && (statement.type.equals(EVIL_STATEMENT)
                            || statement.type.equals(HUMAN_STATEMENT) || statement.type.equals(LYING_STATEMENT))) {
                        return;
                    } if (personType == EVIL && (statement.type.equals(DIVINE_STATEMENT)
                            || statement.type.equals(HUMAN_STATEMENT))) {
                        return;
                    } if (personType == HUMAN && (statement.type.equals(DIVINE_STATEMENT)
                            || statement.type.equals(EVIL_STATEMENT)
                            || (state.day == NIGHT && statement.type.equals(LYING_STATEMENT)))) {
                        return;
                    }

                    if (state.day == DAY && statement.type.equals(NIGHT_STATEMENT)) {
                        return;
                    } else if (state.day == NIGHT && statement.type.equals(DAY_STATEMENT)) {
                        return;
                    }
                }
            } else {
                if (isTrueStatement(state, authorType)) {
                    if (personType == DIVINE && (statement.type.equals(HUMAN_STATEMENT)
                            || statement.type.equals(EVIL_STATEMENT) || statement.type.equals(LYING_STATEMENT))) {
                        return;
                    } else if (personType == EVIL && (statement.type.equals(DIVINE_STATEMENT)
                            || statement.type.equals(HUMAN_STATEMENT))) {
                        return;
                    } else if (personType == HUMAN && (statement.type.equals(DIVINE_STATEMENT)
                            || statement.type.equals(EVIL_STATEMENT)
                            || (state.day == DAY && statement.type.equals(LYING_STATEMENT)))) {
                        return;
                    }

                    if (state.day == DAY && statement.type.equals(NIGHT_STATEMENT)) {
                        return;
                    } else if (state.day == NIGHT && statement.type.equals(DAY_STATEMENT)) {
                        return;
                    }
                } else {
                    if (personType == DIVINE && statement.type.equals(DIVINE_STATEMENT)) {
                        return;
                    } else if (personType == EVIL && (statement.type.equals(EVIL_STATEMENT)
                            || statement.type.equals(LYING_STATEMENT))) {
                        return;
                    } else if (personType == HUMAN && (statement.type.equals(HUMAN_STATEMENT)
                            || (state.day == NIGHT && statement.type.equals(LYING_STATEMENT)))) {
                        return;
                    }

                    if (state.day == DAY && statement.type.equals(DAY_STATEMENT)) {
                        return;
                    } else if (state.day == NIGHT && statement.type.equals(NIGHT_STATEMENT)) {
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < personTypesAndDay.length; i++) {
            if (i < 5) {
                if (state.personTypes[i] == DIVINE) {
                    personTypesAndDay[i][DIVINE] = 1;
                } else if (state.personTypes[i] == EVIL) {
                    personTypesAndDay[i][EVIL] = 1;
                } else {
                    personTypesAndDay[i][HUMAN] = 1;
                }
            } else {
                if (state.day == DAY) {
                    personTypesAndDay[i][DAY] = 1;
                } else {
                    personTypesAndDay[i][NIGHT] = 1;
                }
            }
        }
    }

    private static boolean isTrueStatement(State state, int personType) {
        return personType == DIVINE ||
                (personType == HUMAN && state.day == DAY);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}