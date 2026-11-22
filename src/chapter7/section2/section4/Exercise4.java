package chapter7.section2.section4;

/**
 * Geometric solution
 * For a triangle ABC where all angles are smaller than 120°:
 * 1. Construct an equilateral triangle externally on side AB.
 * 2. Construct another equilateral triangle externally on side AC.
 * 3. Let D and E be their respective third vertices.
 * 4. Draw a line from C to D.
 * 5. Draw a line from B to E.
 * 6. The intersection of the lines is the Fermat-Torricelli point F.
 *
 * For the other cases:
 * If angle A is 120° or higher, return A.
 * If angle B is 120° or higher, return B.
 * If angle C is 120° or higher, return C.
 */
public class Exercise4 {

    // Algorithmic solution
    private static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double MINIMUM_MOVEMENT = 1E-10;

    public static void main() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(0, 1);
        Point point3 = new Point(1, 0);

        Point fermatTorricelliPoint = fermatTorricelliPoint(point1, point2, point3);
        System.out.println(String.format("%-24s %.9f, %.9f", "Fermat-Torricelli point:",
                fermatTorricelliPoint.x, fermatTorricelliPoint.y));
        System.out.println(String.format("%-24s %.9f, %.9f", "Expected:", 0.211324866, 0.211324866));
    }

    private static Point fermatTorricelliPoint(Point point1, Point point2, Point point3) {
        if (isAngleAtLeast120(point1, point2, point3)) {
            return point1;
        }
        if (isAngleAtLeast120(point2, point1, point3)) {
            return point2;
        }
        if (isAngleAtLeast120(point3, point1, point2)) {
            return point3;
        }

        // Initial point: triangle centroid
        Point currentPoint = new Point(
                (point1.x + point2.x + point3.x) / 3,
                (point1.y + point2.y + point3.y) / 3
        );

        for (int iteration = 0; iteration < 100000; iteration++) {
            double distancePoint1 = Math.hypot(currentPoint.x - point1.x, currentPoint.y - point1.y);
            double distancePoint2 = Math.hypot(currentPoint.x - point2.x, currentPoint.y - point2.y);
            double distancePoint3 = Math.hypot(currentPoint.x - point3.x, currentPoint.y - point3.y);

            double weight1 = 1 / distancePoint1;
            double weight2 = 1 / distancePoint2;
            double weight3 = 1 / distancePoint3;
            double denominator = weight1 + weight2 + weight3;

            Point nextPoint = new Point(
                    (point1.x * weight1 + point2.x * weight2 + point3.x * weight3) / denominator,
                    (point1.y * weight1 + point2.y * weight2 + point3.y * weight3) / denominator
            );
            double movement = Math.hypot(
                    nextPoint.x - currentPoint.x,
                    nextPoint.y - currentPoint.y
            );
            if (movement < MINIMUM_MOVEMENT) {
                return nextPoint;
            }
            currentPoint = nextPoint;
        }
        return currentPoint;
    }

    private static boolean isAngleAtLeast120(Point vertexPoint, Point point1, Point point2) {
        double xDistance1 = point1.x - vertexPoint.x;
        double yDistance1 = point1.y - vertexPoint.y;
        double xDistance2 = point2.x - vertexPoint.x;
        double yDistance2 = point2.y - vertexPoint.y;

        double dotProduct = xDistance1 * xDistance2 + yDistance1 * yDistance2;
        double lengthsProduct = Math.hypot(xDistance1, yDistance1)
                * Math.hypot(xDistance2, yDistance2);
        return dotProduct <= -0.5 * lengthsProduct;
    }
}
