package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/11/20.
 */
// Based on http://acm.student.cs.uwaterloo.ca/~acm00/050611/data/sol/Ddjr.c
/*
Let r be the amount of rope-stretch, v be downward speed, g be the downward acceleration of gravity.

Recognizing that
- while James is in free-fall, r = f(t) is a quadratic, and
- while James is constrained by the rope, r = f(t) is a sinusoid,
one finds that in the two cases,

v = sqrt[2g(l + r)]			(r <= 0)
v = sqrt[a - k/w(r - gw/k)^2]	(r >= 0)

where a is the constant that makes the velocities equal when r = 0;
namely a = g(2l + gw/k), and l is the rope nominal length.
*/
public class BungeeJumping {

    private static final double GRAVITY = 9.81;
    private static final double SMASH_VELOCITY = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double ropeStrength = scanner.nextDouble();
        double ropeNominalLength = scanner.nextDouble();
        double bridgeHeight = scanner.nextDouble();
        double weight = scanner.nextDouble();

        while (ropeStrength != 0 || ropeNominalLength != 0 || bridgeHeight != 0 || weight != 0) {
            double deltaRopeLength = bridgeHeight - ropeNominalLength;

            double squaredVelocity;

            if (deltaRopeLength <= 0.0) {
                /* free-fall touchdown */
                squaredVelocity = 2.0 * GRAVITY * bridgeHeight;
            } else {
                double weightByRopeStrength = weight / ropeStrength;
                double force = deltaRopeLength - GRAVITY * weightByRopeStrength;
                double constantThatMakesVelocitiesEqual = GRAVITY * (2.0 * ropeNominalLength + GRAVITY * weightByRopeStrength);
                squaredVelocity = constantThatMakesVelocitiesEqual - force * force / weightByRopeStrength;
            }

            if (squaredVelocity < 0) {
                System.out.println("Stuck in the air.");
            } else if (squaredVelocity <= SMASH_VELOCITY * SMASH_VELOCITY) {
                System.out.println("James Bond survives.");
            } else {
                System.out.println("Killed by the impact.");
            }

            ropeStrength = scanner.nextDouble();
            ropeNominalLength = scanner.nextDouble();
            bridgeHeight = scanner.nextDouble();
            weight = scanner.nextDouble();
        }
    }
}
