package chapter3.section2.a.precalculateable.fooling.around;

import java.util.*;

/**
 * Created by Rene Argento on 04/09/21.
 */
// Pre-computes games where Bob wins.
public class FoolingAroundPreComputation {

    private static final int MAX_GAME = 1000000001;

    public static void main(String[] args) {
        BitSet primes = eratosthenesSieve();

        List<Integer> stonesWhereBobWins = computeStonesWhereBobWins(primes);
        System.out.println(stonesWhereBobWins.size());

        for (int i = 0; i < stonesWhereBobWins.size(); i++) {
            int stones = stonesWhereBobWins.get(i);
            System.out.print(stones);

            if (i != stonesWhereBobWins.size() - 1) {
                System.out.print(", ");
            }
        }
    }

    // Generates prime numbers up to 10^9
    private static BitSet eratosthenesSieve() {
        BitSet primes = new BitSet(MAX_GAME);

        for(int i = 2; i < MAX_GAME; i++) {
            primes.set(i);
        }

        for(long i = 2; i < MAX_GAME; i++) {

            if (primes.get((int) i)) {
                for (long j = i * i; j < MAX_GAME; j += i) {
                    primes.clear((int) j);
                }
            }
        }
        return primes;
    }

    private static List<Integer> computeStonesWhereBobWins(BitSet primes) {
        List<Integer> stonesWhereBobWins = new ArrayList<>();

        for (int stones = 1; stones < MAX_GAME; stones++) {
            if (primes.get(stones + 1)) {
                continue;
            }

            boolean bobWins = true;
            for (int bobStonesWin : stonesWhereBobWins) {
                if (bobStonesWin >= stones + 1) {
                    break;
                }

                int index = (stones + 1) - bobStonesWin;
                if (primes.get(index)) {
                    bobWins = false;
                    break;
                }
            }

            if (bobWins) {
                stonesWhereBobWins.add(stones);
            }
        }
        return stonesWhereBobWins;
    }
}
