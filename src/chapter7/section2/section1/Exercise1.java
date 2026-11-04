package chapter7.section2.section1;

import java.util.Arrays;
import java.util.Comparator;

public class Exercise1 {

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(0, 4);
        Point point3 = new Point(-12, 2);
        Point point4 = new Point(-1, -8);
        Point[] points = { point1, point2, point3, point4 };

        sortPoints(points);
        for (Point point : points) {
            System.out.println(point.x + " " + point.y);
        }
    }

    private static void sortPoints(Point[] points) {
        int pivotIndex = getPivotIndex(points);
        Point aux = points[0];
        points[0] = points[pivotIndex];
        points[pivotIndex] = aux;

        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point point1, Point point2) {
                return ccw(points[0], point1, point2);
            }
        });
    }

    private static int getPivotIndex(Point[] points) {
        Point pivot = points[0];
        int pivotIndex = 0;

        for (int i = 1; i < points.length; i++) {
            if (points[i].y < pivot.y
                    || (points[i].y == pivot.y && points[i].x > pivot.x)) {
                pivot = points[i];
                pivotIndex = i;
            }
        }
        return pivotIndex;
    }

    private static int ccw(Point point0, Point point1, Point point2) {
        long dx1 = point1.x - (long) point0.x;
        long dx2 = point2.x - (long) point0.x;
        long dy1 = point1.y - (long) point0.y;
        long dy2 = point2.y - (long) point0.y;
        long crossProduct = dx1 * dy2 - dy1 * dx2;

        if (crossProduct > 0) {
            return -1;
        }
        if (crossProduct < 0) {
            return 1;
        }

        long distance1 = dx1 * dx1 + dy1 * dy1;
        long distance2 = dx2 * dx2 + dy2 * dy2;
        return Long.compare(distance1, distance2);
    }
}
