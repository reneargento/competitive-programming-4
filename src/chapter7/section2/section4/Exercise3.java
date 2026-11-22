package chapter7.section2.section4;

public class Exercise3 {

    private static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double EPSILON = 1E-9;

    public static void main() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(4, 0);
        Point point3 = new Point(0, 4);

        int result1 = isPointInsideCircumCircle(point1, point2, point3, new Point(2, 1));
        System.out.println("Is inside: " + result1);
        System.out.println("Expected: 0");
        int result2 = isPointInsideCircumCircle(point1, point2, point3, new Point(5, 5));
        System.out.println("\nIs inside: " + result2);
        System.out.println("Expected: 2");

        Point point4 = new Point(0, 0);
        Point point5 = new Point(2, 0);
        Point point6 = new Point(1, Math.sqrt(3));

        int result3 = isPointInsideCircumCircle(point4, point5, point6, new Point(1, 0.5));
        System.out.println("\nIs inside: " + result3);
        System.out.println("Expected: 0");
        int result4 = isPointInsideCircumCircle(point4, point5, point6, new Point(1, 2));
        System.out.println("\nIs inside: " + result4);
        System.out.println("Expected: 2");
    }

    private static int isPointInsideCircumCircle(Point point1, Point point2, Point point3, Point point) {
        Point center = circumCircleCenter(point1, point2, point3);
        double radius = radius(point1, point2, point3);
        return isPointInsideCircle(point, center, radius);
    }

    // Returns 0 if point is inside the circle, 1 if it is on the border or 2 if is outside
    private static int isPointInsideCircle(Point point, Point center, double radius) {
        double xDistance = point.x - center.x;
        double yDistance = point.y - center.y;
        double distanceSquared = xDistance * xDistance + yDistance * yDistance;
        double radiusSquared = radius * radius;

        if (distanceSquared < radiusSquared - EPSILON) {
            return 0; // Inside
        }
        if (Math.abs(distanceSquared - radiusSquared) < EPSILON) {
            return 1; // On the border / circumference
        }
        return 2; // Outside
    }

    private static double radius(double edgeLength1, double edgeLength2, double edgeLength3) {
        double area = triangleArea(edgeLength1, edgeLength2, edgeLength3);
        return (edgeLength1 * edgeLength2 * edgeLength3) / (4 * area);
    }

    private static double radius(Point point1, Point point2, Point point3) {
        return radius(distanceBetweenPoints(point1, point2),
                distanceBetweenPoints(point2, point3),
                distanceBetweenPoints(point3, point1));
    }

    private static double distanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    private static double triangleArea(double edgeLength1, double edgeLength2, double edgeLength3) {
        double semiPerimeter = (edgeLength1 + edgeLength2 + edgeLength3) / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - edgeLength1)
                * (semiPerimeter - edgeLength2)
                * (semiPerimeter - edgeLength3));
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
