package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Rene Argento on 06/03/21.
 */
public class OneLittleTwoLittleThreeLittleEndians {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String numberString = FastReader.getLine();

        while (numberString != null) {
            int number = Integer.parseInt(numberString);
            int oppositeEndianNumber = convertToOtherEndianNumber(number);

            System.out.printf("%d converts to %d\n", number, oppositeEndianNumber);
            numberString = FastReader.getLine();
        }
    }

    private static int convertToOtherEndianNumber(int number) {
        int otherEndianNumber = 0;
        long rightMask = 1 << 24;
        long leftMask = 1;

        for (int i = 0; i < 4; i++) {
            for (int b = 0; b < 8; b++) {
                boolean isSet = (number & leftMask) > 0;

                if (isSet) {
                    otherEndianNumber |= rightMask;
                }

                leftMask <<= 1;
                rightMask <<= 1;
            }
            rightMask >>= 16;
        }
        return otherEndianNumber;
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
