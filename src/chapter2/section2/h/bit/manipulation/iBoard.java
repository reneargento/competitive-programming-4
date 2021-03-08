package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class iBoard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String message = FastReader.getLine();

        while (message != null) {
            boolean isUserTrapped = isUserTrapped(message);
            System.out.println(isUserTrapped ? "trapped" : "free");

            message = FastReader.getLine();
        }
    }

    private static boolean isUserTrapped(String message) {
        int zeroBitsCardinality = 0;
        int oneBitsCardinality = 0;

        for (char character : message.toCharArray()) {
            int mask = 1;

            for (int i = 0; i < 7; i++) {
                if ((mask & character) > 0) {
                    oneBitsCardinality++;
                } else {
                    zeroBitsCardinality++;
                }
                mask <<= 1;
            }
        }
        return (zeroBitsCardinality % 2 == 1) || (oneBitsCardinality % 2 == 1);
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
}
