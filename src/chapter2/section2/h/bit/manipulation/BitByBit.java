package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/03/21.
 */
public class BitByBit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int instructions = FastReader.nextInt();

        while (instructions != 0) {
            char[] bits = createInitialBits();

            for (int i = 0; i < instructions; i++) {
                String instruction = FastReader.next();
                int index1 = 31 - FastReader.nextInt();

                if (instruction.equals("SET") || instruction.equals("CLEAR")) {
                    if (instruction.equals("SET")) {
                        bits[index1] = '1';
                    } else {
                        bits[index1] = '0';
                    }
                } else {
                    int index2 = 31 - FastReader.nextInt();
                    if (instruction.equals("AND")) {
                        and(bits, index1, index2);
                    } else {
                        or(bits, index1, index2);
                    }
                }
            }
            System.out.println(new String(bits));
            instructions = FastReader.nextInt();
        }
    }

    private static void and(char[] bits, int index1, int index2) {
        if (bits[index1] == '0' || bits[index2] == '0') {
            bits[index1] = '0';
        } else if (bits[index1] == '1' && bits[index2] == '1') {
            bits[index1] = '1';
        } else if (bits[index2] == '?') {
            bits[index1] = '?';
        }
    }

    private static void or(char[] bits, int index1, int index2) {
        if (bits[index1] == '1' || bits[index2] == '1') {
            bits[index1] = '1';
        } else if (bits[index1] == '0' && bits[index2] == '0') {
            bits[index1] = '0';
        } else if (bits[index2] == '?') {
            bits[index1] = '?';
        }
    }

    private static char[] createInitialBits() {
        char[] bits = new char[32];
        Arrays.fill(bits, '?');
        return bits;
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
}
