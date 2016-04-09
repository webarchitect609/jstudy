package ru.webarch.jstudy.sandbox;

/**
 * Created by gripinskiy on 09.04.16.
 */
public class Point {
  double x;
  double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Возвращает расстояние до другой точки
   *
   * @param z Другая точка
   * @return Расстояние
   */
  public double distance(Point z) {

    return Math.sqrt(Math.pow(z.x - this.x, 2) + Math.pow(z.y - this.y, 2));

  }

}
