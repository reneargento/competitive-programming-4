package chapter7.section2.section4;

public class Exercise2 {

    private static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }

    private static final double EPSILON = 1E-9;

    public static void main() {
        Point center1 = circumCircleCenter(
                new Point(0, 0),
                new Point(4, 0),
                new Point(0, 4)
        );
        System.out.println("Center: " + center1);
        System.out.println("Expected: 2.0, 2.0");

        Point center2 = circumCircleCenter(
                new Point(0, 0),
                new Point(2, 0),
                new Point(1, Math.sqrt(3))
        );
        System.out.println("\nCenter: " + center2);
        System.out.println("Expected: 1.0, 0.5773502691896256");
    }

    private static Point circumCircleCenter(Point point1, Point point2, Point point3) {
        double determinant = 2 * (point1.x * (point2.y - point3.y)
                + point2.x * (point3.y - point1.y)
                + point3.x * (point1.y - point2.y));

        if (Math.abs(determinant) < EPSILON) {
            // Points are collinear (or numerically almost collinear)
            return null;
        }

        double point1Squared = point1.x * point1.x + point1.y * point1.y;
        double point2Squared = point2.x * point2.x + point2.y * point2.y;
        double point3Squared = point3.x * point3.x + point3.y * point3.y;

        double centerX = (point1Squared * (point2.y - point3.y)
                + point2Squared * (point3.y - point1.y)
                + point3Squared * (point1.y - point2.y)) / determinant;
        double centerY = (point1Squared * (point3.x - point2.x)
                + point2Squared * (point1.x - point3.x)
                + point3Squared * (point2.x - point1.x)) / determinant;
        return new Point(centerX, centerY);
    }
}
