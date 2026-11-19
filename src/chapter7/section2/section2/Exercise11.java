package chapter7.section2.section2;

public class Exercise11 {

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class LineSegment {
        Point start;
        Point end;
        double slope;
        double yIntercept;

        public LineSegment(Point start, Point end) {
            this.start = start;
            this.end = end;

            if (start.x == end.x) { // Vertical line
                slope = Double.POSITIVE_INFINITY;
                yIntercept = Double.POSITIVE_INFINITY;
            } else {
                slope = (end.y - start.y) / (end.x - start.x);
                yIntercept = end.y - slope * end.x;
            }
        }

        public boolean isVertical() {
            return slope == Double.POSITIVE_INFINITY;
        }

        public double getYIntersectionFromXIntersection(double x) {
            if (isVertical()) {
                return Double.POSITIVE_INFINITY;
            }
            return slope * x + yIntercept;
        }
    }

    private static Point intersection(Point start1, Point end1, Point start2, Point end2) {
        LineSegment line1 = new LineSegment(start1, end1);
        LineSegment line2 = new LineSegment(start2, end2);

        // Parallel lines only intercept if they have the same y-intercept (are the same line) and if the start or
        // the end of one line is within the other line.
        if (line1.slope == line2.slope) { // Parallel lines
            if (line1.yIntercept != line2.yIntercept) return null;

            if (isBetween(start1, start2, end1)) return start2;
            if (isBetween(start1, end2, end1)) return end2;
            if (isBetween(start2, start1, end2)) return start1;
            if (isBetween(start2, end1, end2)) return end1;
            return null;
        }

        // Compute intersection point for infinite lines
        double xIntersection;
        if (line1.isVertical() || line2.isVertical()) {
            // A vertical line is not described by the y = m * x + b equation, it is described by the x = x1 equation
            xIntersection = line1.isVertical() ? line1.start.x : line2.start.x;
        } else { // Set y = m * x + b equations equal and solve for x (where m is the slope and b is the y-intercept)
            xIntersection = (line2.yIntercept - line1.yIntercept) / (line1.slope - line2.slope);
        }
        // Compute y intersection point using a non-vertical line
        double yIntersection = line1.isVertical() ? line2.getYIntersectionFromXIntersection(xIntersection) :
                line1.getYIntersectionFromXIntersection(xIntersection);
        Point intersection = new Point(xIntersection, yIntersection);

        // Check if intersection point is within both line segments
        if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
            return intersection;
        }
        return null;
    }

    // Checks if middle is between start and end
    private static boolean isBetween(double start, double middle, double end) {
        if (start > end) {
            return end <= middle && middle <= start;
        } else {
            return start <= middle && middle <= end;
        }
    }

    private static boolean isBetween(Point start, Point middle, Point end) {
        return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
    }

    public static void main() {
        Point start1 = new Point(0, 0);
        Point end1 = new Point(10, 0);
        Point start2 = new Point(7, 1);
        Point end2 = new Point(7, 7.01);
        Point start3 = new Point(7, 1);
        Point end3 = new Point(7, -1);

        boolean lineSegment1And2Intersect = intersection(start1, end1, start2, end2) != null;
        System.out.println("Line segment 1 and 2: " + lineSegment1And2Intersect);
        System.out.println("Expected: false");

        boolean lineSegment1And3Intersect = intersection(start1, end1, start3, end3) != null;
        System.out.println("\nLine segment 1 and 3: " + lineSegment1And3Intersect);
        System.out.println("Expected: true");
    }
}
