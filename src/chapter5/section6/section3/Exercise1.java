package chapter5.section6.section3;

/**
 * Created by Rene Argento on 20/03/26.
 */
public class Exercise1 {

    private static class Result {
        long cycleStart;
        long length;

        public Result(long cycleStart, long length) {
            this.cycleStart = cycleStart;
            this.length = length;
        }
    }

    public static void main(String[] args) {
        Result result = brentCycleFinding(7);
        System.out.println("Start: " + result.cycleStart + " Length: " + result.length);
        System.out.println("Expected: 3 5");
    }

    // Runtime: O(start + length)
    // Space: O(1)
    private static Result brentCycleFinding(long value0) {
        long slow = value0;
        long fast = function(value0);
        int length = 1;
        long power2 = 1;

        while (slow != fast) {
            if (length == power2) {
                slow = fast;
                length = 0;
                power2 *= 2;
            }

            fast = function(fast);
            length++;
        }

        slow = value0;
        fast = value0;
        for (int i = 0; i < length; i++) {
            fast = function(fast);
        }
        long start = 0;

        while (slow != fast) {
            slow = function(slow);
            fast = function(fast);
            start++;
        }
        return new Result(start, length);
    }

    private static long function(long value) {
        return (26 * value + 11) % 80;
    }
}
