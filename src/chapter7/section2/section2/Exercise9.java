package chapter7.section2.section2;

// The special cases are a vertical and a horizontal line
public class Exercise9 {

    private static class Point {
        double x;
        double y;

        public Point() {}

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Line {
        double a;
        double b;
        double c;

        public Line(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    private static final double EPSILON = 1E-9;

    private static Point closestPoint(Line line, Point point) {
        Point closestPoint = new Point();

        if (Math.abs(line.b) < EPSILON) {
            // Vertical line
            closestPoint.x = -line.c;
            closestPoint.y = point.y;
            return closestPoint;
        }
        if (Math.abs(line.a) < EPSILON) {
            // Horizontal line
            closestPoint.x = point.x;
            closestPoint.y = -line.c;
            return closestPoint;
        }

        Line perpendicularLine = pointSlopeToLine(point, 1.0 / line.a);
        return intersection(line, perpendicularLine);
    }

    private static Line pointSlopeToLine(Point point, double slope) {
        double a = -slope;
        double b = 1.0;
        double c = -((a * point.x) + (b * point.y));
        return new Line(a, b, c);
    }

    private static Point intersection(Line line1, Line line2) {
        if (areLinesParallel(line1, line2)) {
            return null;
        }

        double pointX = (line2.b * line1.c - line1.b * line2.c) / (line2.a * line1.b - line1.a * line2.b);
        double pointY;

        if (Math.abs(line1.b) > EPSILON) {
            pointY = -(line1.a * pointX + line1.c);
        } else {
            pointY = -(line2.a * pointX + line2.c);
        }
        return new Point(pointX, pointY);
    }

    private static boolean areLinesParallel(Line line1, Line line2) {
        return Math.abs(line1.a - line2.a) < EPSILON
                && Math.abs(line1.b - line2.b) < EPSILON;
    }
}
