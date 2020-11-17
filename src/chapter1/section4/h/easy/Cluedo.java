package chapter1.section4.h.easy;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class Cluedo {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        int murderer = 1;
        int location = 1;
        int weapon = 1;

        while (true) {
            int result = theory(murderer, location, weapon);

            switch (result) {
                case 0: return;
                case 1: murderer++; break;
                case 2: location++; break;
                default: weapon++; break;
            }
        }
    }

    // Mock implementation of grader method
    private static int theory(int murderer, int location, int weapon) {
        return 0;
    }

}
