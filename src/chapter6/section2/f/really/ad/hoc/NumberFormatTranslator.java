package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class NumberFormatTranslator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        outputWriter.printLine(tests);

        for (int t = 0; t < tests; t++) {
            String numeralCardinal = FastReader.getLine();
            long arabicDigits = convertNumber(numeralCardinal);
            outputWriter.printLine(arabicDigits);
        }
        outputWriter.flush();
    }

    private static long convertNumber(String numeralCardinal) {
        numeralCardinal = numeralCardinal.replace(",", "");
        String[] tokens = numeralCardinal.split("\\s+");

        long totalNumber = 0;
        long currentNumber = 0;
        for (String token : tokens) {
            if (token.equals("e")) {
                continue;
            }
            if (isMultiplier(currentNumber, token)) {
                currentNumber = multiply(currentNumber, token);
                totalNumber += currentNumber;
                currentNumber = 0;
            } else {
                currentNumber += getNumericValue(token);
            }
        }
        totalNumber += currentNumber;
        return totalNumber;
    }

    private static boolean isMultiplier(long currentNumber, String token) {
        if (token.equals("mil") && currentNumber == 0) {
            return false;
        }
        return token.equals("mil")
                || token.equals("milhao")
                || token.equals("milhoes")
                || token.equals("biliao")
                || token.equals("bilioes")
                || token.equals("bilhao")
                || token.equals("bilhoes");
    }

    private static long multiply(long number, String multiplier) {
        if (multiplier.equals("mil")) {
            return number * 1000;
        }
        if (multiplier.equals("milhao")
                || multiplier.equals("milhoes")) {
            return number * 1000000;
        }
        return number * 1000000000;
    }

    private static int getNumericValue(String number) {
        if (number.equals("zero")) {
            return 0;
        }
        if (number.equals("um")) {
            return 1;
        }
        if (number.equals("dois")) {
            return 2;
        }
        if (number.equals("tres")) {
            return 3;
        }
        if (number.equals("quatro")) {
            return 4;
        }
        if (number.equals("cinco")) {
            return 5;
        }
        if (number.equals("seis")) {
            return 6;
        }
        if (number.equals("sete")) {
            return 7;
        }
        if (number.equals("oito")) {
            return 8;
        }
        if (number.equals("nove")) {
            return 9;
        }
        if (number.equals("dez")) {
            return 10;
        }
        if (number.equals("onze")) {
            return 11;
        }
        if (number.equals("doze")) {
            return 12;
        }
        if (number.equals("treze")) {
            return 13;
        }
        if (number.equals("quatorze") || number.equals("catorze")) {
            return 14;
        }
        if (number.equals("quinze")) {
            return 15;
        }
        if (number.equals("dezasseis") || number.equals("dezesseis")) {
            return 16;
        }
        if (number.equals("dezassete") || number.equals("dezessete")) {
            return 17;
        }
        if (number.equals("dezoito")) {
            return 18;
        }
        if (number.equals("dezanove") || number.equals("dezenove")) {
            return 19;
        }
        if (number.equals("vinte")) {
            return 20;
        }
        if (number.equals("trinta")) {
            return 30;
        }
        if (number.equals("quarenta")) {
            return 40;
        }
        if (number.equals("cinquenta")) {
            return 50;
        }
        if (number.equals("sessenta")) {
            return 60;
        }
        if (number.equals("setenta")) {
            return 70;
        }
        if (number.equals("oitenta")) {
            return 80;
        }
        if (number.equals("noventa")) {
            return 90;
        }
        if (number.equals("cem") || number.equals("cento")) {
            return 100;
        }
        if (number.equals("duzentos")) {
            return 200;
        }
        if (number.equals("trezentos")) {
            return 300;
        }
        if (number.equals("quatrocentos")) {
            return 400;
        }
        if (number.equals("quinhentos")) {
            return 500;
        }
        if (number.equals("seiscentos")) {
            return 600;
        }
        if (number.equals("setecentos")) {
            return 700;
        }
        if (number.equals("oitocentos")) {
            return 800;
        }
        if (number.equals("novecentos")) {
            return 900;
        }
        if (number.equals("mil")) {
            return 1000;
        }
        return 0;
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

        public void flush() {
            writer.flush();
        }
    }
}