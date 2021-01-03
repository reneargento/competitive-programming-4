package chapter1.section6.p.time.waster.problems.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

/**
 * Created by Rene Argento on 28/12/20.
 */
public class Sabor {

    private static class MP {
        int id;
        Character party;
        List<Integer> discussions;

        public MP(int id) {
            this.id = id;
            discussions = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int mpsNumber = inputReader.readInt();
        MP[] mps = new MP[mpsNumber];
        initMps(mps);

        for (int l = 0; l < 5; l++) {
            int pairs = inputReader.readInt();
            for (int p = 0; p < pairs; p++) {
                int mp1 = inputReader.readInt() - 1;
                int mp2 = inputReader.readInt() - 1;
                mps[mp1].discussions.add(mp2);
                mps[mp2].discussions.add(mp1);
            }
        }

        findParties(mps);
        for (MP mp : mps) {
            outputWriter.print(mp.party);
        }
        outputWriter.printLine();
        outputWriter.flush();
        outputWriter.close();
    }

    private static void findParties(MP[] mps) {
        List<MP> mpsToCheck = new ArrayList<>();
        Random random = new Random();
        for (MP mp : mps) {
            if (random.nextInt(2) == 0) {
                mp.party = 'A';
            } else {
                mp.party = 'B';
            }
            mpsToCheck.add(mp);
        }

        fixAssignments(mps, mpsToCheck);
    }

    private static void fixAssignments(MP[] mps, List<MP> mpsToCheck) {
        List<MP> nextMPsToCheck = new ArrayList<>();

        for (MP mp : mpsToCheck) {
            char party = mp.party;
            List<MP> partyA = new ArrayList<>();
            List<MP> partyB = new ArrayList<>();

            for (int discussionMPId : mp.discussions) {
                if (mps[discussionMPId].party == 'A') {
                    partyA.add(mps[discussionMPId]);
                } else {
                    partyB.add(mps[discussionMPId]);
                }
            }

            if (party == 'A' && partyA.size() > 2) {
                mp.party = 'B';
                nextMPsToCheck.addAll(partyB);
            } else if (party == 'B' && partyB.size() > 2) {
                mp.party = 'A';
                nextMPsToCheck.addAll(partyA);
            }
        }

        if (!nextMPsToCheck.isEmpty()) {
            fixAssignments(mps, nextMPsToCheck);
        }
    }

    private static void initMps(MP[] mps) {
        for (int i = 0; i < mps.length; i++) {
            mps[i] = new MP(i);
        }
    }

    private static class InputReader {
        private InputStream stream;
        private byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
