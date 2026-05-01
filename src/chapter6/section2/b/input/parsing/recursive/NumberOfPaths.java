package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/04/26.
 */
public class NumberOfPaths {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<String> commandsList = new ArrayList<>();
            String command = FastReader.next();
            commandsList.add(command);
            while (!command.equals("ENDPROGRAM")) {
                command = FastReader.next();
                commandsList.add(command);
            }

            long executionPaths = countExecutionPaths(commandsList);
            outputWriter.printLine(executionPaths);
        }
        outputWriter.flush();
    }

    private static long countExecutionPaths(List<String> commandsList) {
        String[] commands = new String[commandsList.size()];
        commands = commandsList.toArray(commands);
        return countExecutionPaths(commands, 0, commands.length - 1);
    }

    private static long countExecutionPaths(String[] commands, int left, int right) {
        if (left > right) {
            return 1;
        }
        if (commands[left].equals("ENDPROGRAM")) {
            return 1;
        }

        long executionPaths = 1;

        if (commands[left].equals("IF")) {
            int elseIndex = -1;
            int endIfIndex = -1;
            int ifsOpen = 1;
            int elsesOpen = 0;
            int endIfsOpen = 0;

            for (int i = left + 1; i <= right; i++) {
                if (commands[i].equals("IF")) {
                    ifsOpen++;
                } else if (commands[i].equals("ELSE")) {
                    elsesOpen++;
                    if (ifsOpen == elsesOpen && elseIndex == -1) {
                        elseIndex = i;
                    }
                } else if (commands[i].equals("END_IF")) {
                    endIfsOpen++;
                    if (ifsOpen == endIfsOpen) {
                        endIfIndex = i;
                        break;
                    }
                }
            }
            executionPaths = countExecutionPaths(commands, left + 1, elseIndex - 1) +
                    countExecutionPaths(commands, elseIndex + 1, endIfIndex - 1);
            left = endIfIndex;
        }
        executionPaths *= countExecutionPaths(commands, left + 1, right);
        return executionPaths;
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
