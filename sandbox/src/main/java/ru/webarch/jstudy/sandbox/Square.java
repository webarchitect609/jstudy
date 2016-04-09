package ru.webarch.jstudy.sandbox;

/**
 * Created by gripinskiy on 09.04.16.
 */
public class Square {

  /**
   * Сторона квадрата
   */
  public double side;

  /**
   * Создать квадрат
   *
   * @param side
   */
  public Square(double side) {
    this.side = side;
  }

  public double area() {
    return this.side * this.side;
  }
}
