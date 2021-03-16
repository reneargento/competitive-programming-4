package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 15/03/21.
 */
// Considering the district as a graph, the intersections are vertices V and the arcs connecting the vertices are edges E.
// V = n * (n - 1) -> there are n * (n - 1) / 2 pairs of circles and each pair has two intersection points.
// E = 4V / 2 = 2V -> The degree of each vertex is 4 (since exactly 2 circles intersect at each intersection point),
// and by the handshaking lemma the total number of edges is 4V / 2.
// By Euler's formula,
// F = E - V + 2
// F = 2V - V + 2
// F = n * (n - 1) + 2
public class ReallyStrange {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        BigInteger bigIntegerTwo = BigInteger.valueOf(2);
        String circlesString = FastReader.getLine();

        while (circlesString != null) {
            BigInteger circles = new BigInteger(circlesString);
            if (circles.compareTo(BigInteger.ZERO) == 0) {
                System.out.println("1");
            } else {
                BigInteger result = circles.multiply(circles.subtract(BigInteger.ONE)).add(bigIntegerTwo);
                System.out.println(result);
            }

            circlesString = FastReader.getLine();
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
