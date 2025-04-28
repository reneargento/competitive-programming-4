package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/04/25.
 */
public class ThermostatTrouble {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;

            if (denominator < 0) {
                this.nominator *= -1;
                this.denominator *= -1;
            }
        }
    }

    private static class UnitSystem {
        long minTemperature;
        long maxTemperature;

        public UnitSystem(long minTemperature, long maxTemperature) {
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        UnitSystem[] unitSystems = new UnitSystem[FastReader.nextInt()];
        int queries = FastReader.nextInt();

        for (int i = 0; i < unitSystems.length; i++) {
            unitSystems[i] = new UnitSystem(FastReader.nextInt(), FastReader.nextInt());
        }

        for (int q = 0; q < queries; q++) {
            int unitSystemId1 = FastReader.nextInt() - 1;
            int unitSystemId2 = FastReader.nextInt() - 1;
            int targetTemperature = FastReader.nextInt();

            Fraction convertedTemperature = convertTemperature(unitSystems, unitSystemId1, unitSystemId2,
                    targetTemperature);
            outputWriter.printLine(convertedTemperature.nominator + "/" + convertedTemperature.denominator);
        }
        outputWriter.flush();
    }

    private static Fraction convertTemperature(UnitSystem[] unitSystems, int unitSystemId1, int unitSystemId2,
                                               long targetTemperature) {
        long minTemperature1 = unitSystems[unitSystemId1].minTemperature;
        long maxTemperature1 = unitSystems[unitSystemId1].maxTemperature;
        long minTemperature2 = unitSystems[unitSystemId2].minTemperature;
        long maxTemperature2 = unitSystems[unitSystemId2].maxTemperature;

        long temperature1Delta = maxTemperature1 - minTemperature1;
        long temperature2Delta = maxTemperature2 - minTemperature2;

        targetTemperature -= minTemperature1;

        Fraction fraction = new Fraction(targetTemperature, temperature1Delta);
        reduceFraction(fraction);

        fraction = multiplyFraction(fraction.nominator, fraction.denominator, temperature2Delta, 1);
        reduceFraction(fraction);
        fraction = sumFraction(fraction.nominator, fraction.denominator, minTemperature2, 1);

        if (fraction.nominator == 0) {
            return new Fraction(0, 1);
        }
        return fraction;
    }

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
    }

    private static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long nominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(nominator, lcm);
    }

    private static Fraction multiplyFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return new Fraction(nominator1 * nominator2, denominator1 * denominator2);
    }

    private static void reduceFraction(Fraction fraction) {
        long gcd = gcd(fraction.nominator, fraction.denominator);
        fraction.nominator /= gcd;
        fraction.denominator /= gcd;
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
