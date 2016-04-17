package ru.webarch.jstudy.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gripinskiy on 16.04.16.
 */
public class PointTest {

    /**
     * Расстояние до самой себя
     */
    @Test
    public void selfDistanceTest() {
        Point point = new Point(145, 93);
        Assert.assertEquals(point.distance(point), 0.0);
    }

    /**
     * Простые и очевидные расстояния между точками
     */
    @Test
    public void SimpleDistanceTest() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 0);
        Point c = new Point(0, 1);

        Assert.assertEquals(a.distance(b), 1.0);
        Assert.assertEquals(a.distance(c), 1.0);
        Assert.assertEquals(b.distance(c), Math.sqrt(2));

        //Ну и ещё раз перепроверим по собственным расчётам
        Assert.assertEquals(b.distance(c), calcDistance(b.x, b.y, c.x, c.y));

    }

    /**
     * Сложные расстояния: вычисляем по формуле сами, чтобы проверить Point
     */
    @Test
    public void realDistanceTest() {

        double ax = -7345.3467;
        double ay = 7.154;

        double bx = 15.001;
        double by = 0.135;

        Point pointA = new Point(ax, ay);
        Point pointB = new Point(bx, by);

        double expectedDistance = calcDistance(ax, ay, bx, by);

        Assert.assertEquals(
                pointA.distance(pointB),
                expectedDistance
        );

        Assert.assertEquals(
                pointB.distance(pointA),
                expectedDistance
        );
    }

    /**
     * "Ручное" вычисление расстояния, чтобы перепроверить {@link Point}
     * @implNote Вспомогательная функция, а не тест
     * @param ax координата Ax
     * @param ay координата Ay
     * @param bx координата Bx
     * @param by координата By
     * @return Расстояние между точками
     */
    private static double calcDistance(double ax, double ay, double bx, double by) {
        return Math.sqrt(Math.pow(by - ay, 2) + Math.pow(bx - ax, 2));
    }

}
