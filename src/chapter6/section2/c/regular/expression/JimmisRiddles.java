package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 10/05/26.
 */
public class JimmisRiddles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Pattern pattern = generatePattern();
        String riddle = FastReader.getLine();
        while (riddle != null) {
            String result = checkRiddle(pattern, riddle);
            outputWriter.printLine(result);
            riddle = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Pattern generatePattern() {
        String verbSingular = "(hate|love|know|like)";
        String verb = "(" + verbSingular + "|" + verbSingular + "s)";
        String noun = "(tom|jerry|goofy|mickey|jimmy|dog|cat|mouse)";
        String article = "(a|the)";
        String actor = "(" + noun + "|(" + article + " " + noun + "))";

        String actorCompose = " and " + actor;
        String activeList = actor + "(" + actorCompose + ")*";

        String action = "(" + activeList + " " + verb + " " + activeList + ")";

        String actionCompose = " , " + action;
        String statement = action + "(" + actionCompose + ")*";
        return Pattern.compile(statement);
    }

    private static String checkRiddle(Pattern pattern, String riddle) {
        Matcher matcher = pattern.matcher(riddle);
        if (matcher.matches()) {
            return "YES I WILL";
        } else {
            return "NO I WON'T";
        }
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
