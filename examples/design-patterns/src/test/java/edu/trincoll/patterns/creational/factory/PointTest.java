package edu.trincoll.patterns.creational.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

@DisplayName("Point Factory Method Tests")
class PointTest {

    private static final double TOLERANCE = 1e-9;

    @Test
    @DisplayName("Create point from Cartesian coordinates")
    void createPointFromCartesian() {
        Point p = Point.ofCartesian(3.0, 4.0);
        assertThat(p.getX()).isEqualTo(3.0, withPrecision(TOLERANCE));
        assertThat(p.getY()).isEqualTo(4.0, withPrecision(TOLERANCE));
    }

    @Test
    @DisplayName("Create point from Polar coordinates (radius 1, angle 0)")
    void createPointFromPolarAngle0() {
        Point p = Point.ofPolar(1.0, 0.0);
        assertThat(p.getX()).isEqualTo(1.0, withPrecision(TOLERANCE));
        assertThat(p.getY()).isEqualTo(0.0, withPrecision(TOLERANCE));
    }

    @Test
    @DisplayName("Create point from Polar coordinates (radius 1, angle PI/2)")
    void createPointFromPolarAnglePiOver2() {
        Point p = Point.ofPolar(1.0, Math.PI / 2);
        assertThat(p.getX()).isEqualTo(0.0, withPrecision(TOLERANCE));
        assertThat(p.getY()).isEqualTo(1.0, withPrecision(TOLERANCE));
    }

    @Test
    @DisplayName("Create point from Polar coordinates (radius 5, angle PI/4)")
    void createPointFromPolar() {
        double radius = 5.0;
        double angle = Math.PI / 4;
        Point p = Point.ofPolar(radius, angle);
        assertThat(p.getX()).isEqualTo(radius * Math.cos(angle), withPrecision(TOLERANCE));
        assertThat(p.getY()).isEqualTo(radius * Math.sin(angle), withPrecision(TOLERANCE));
    }

    @Test
    @DisplayName("toString returns correct format")
    void pointToString() {
        Point p = Point.ofCartesian(1.23, 4.56);
        assertThat(p.toString()).isEqualTo("Point{x=1.23, y=4.56}");
    }
}
