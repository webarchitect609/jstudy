package ru.webarch.jstudy.sandbox;

/**
 * Created by gripinskiy on 09.04.16.
 */
public class Rectangle {

  /**
   * Ширина прямоугольника
   */
  public double width;

  /**
   * Высота прямоугольника
   */
  public double height;

  /**
   * Создаёт прямоугольник
   *
   * @param width
   * @param height
   */
  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
  }

  public double area() {
    return this.height * this.width;
  }
}
