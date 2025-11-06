package edu.trincoll.patterns.creational.factory;

public class Point {
    private final double x;
    private final double y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point ofCartesian(double x, double y) {
        return new Point(x, y);
    }

    public static Point ofPolar(double radius, double angle) {
        return new Point(radius * Math.cos(angle), radius * Math.sin(angle));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + "}";
    }
}
