package chapter7.section2.section2;

public class Exercise10 {

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Line {
        Point pointA;
        Point pointB;
    }

    private static class Vector {
        public double x;
        public double y;

        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Vector(Point point1, Point point2) {
            x = point2.x - point1.x;
            y = point2.y - point1.y;
        }
    }

    private static Point computeReflectionPoint(Line line, Point point) {
        Point closestPointToLine = closestPointToLine(point, line.pointA, line.pointB);
        Vector pointToClosestPointVector = new Vector(point, closestPointToLine);
        Point translatedPoint1 = translatePoint(point, pointToClosestPointVector);
        return translatePoint(translatedPoint1, pointToClosestPointVector);
    }

    private static Point closestPointToLine(Point point, Point linePoint1, Point linePoint2) {
        Vector point1ToPointVector = new Vector(linePoint1, point);
        Vector point1ToPoint2Vector = new Vector(linePoint1, linePoint2);

        double valueToScale = dotProduct(point1ToPointVector, point1ToPoint2Vector)
                / normalizedSquare(point1ToPoint2Vector);
        Vector scaledVector = scale(point1ToPoint2Vector, valueToScale);
        return translatePoint(linePoint1, scaledVector);
    }

    private static double dotProduct(Vector vector1, Vector vector2) {
        return vector1.x * vector2.x + vector1.y * vector2.y;
    }

    private static double normalizedSquare(Vector vector) {
        return vector.x * vector.x + vector.y * vector.y;
    }

    private static Vector scale(Vector vector, double size) {
        return new Vector(vector.x * size, vector.y * size);
    }

    private static Point translatePoint(Point point, Vector vector) {
        return new Point(point.x + vector.x, point.y + vector.y);
    }
}
