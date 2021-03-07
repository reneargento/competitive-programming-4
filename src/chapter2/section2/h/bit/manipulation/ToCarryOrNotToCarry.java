package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/03/21.
 */
public class ToCarryOrNotToCarry {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String valuesLine = FastReader.getLine();

        while (valuesLine != null) {
            String[] values = valuesLine.split(" ");
            int number1 = Integer.parseInt(values[0]);
            int number2 = Integer.parseInt(values[1]);
            System.out.println(number1 ^ number2);

            valuesLine = FastReader.getLine();
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
}
