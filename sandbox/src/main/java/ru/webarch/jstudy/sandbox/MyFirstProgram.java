package ru.webarch.jstudy.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {

    hello("World");
    hello("Water");
    hello("Fire");

    double s = 5;
    System.out.println("Площадь квадрата со стороной " + s + " равна " + area(s));

    double a = 4, b = 9;
    System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " равна " + area(a, b));


  }

  public static void hello(String world) {
    System.out.println("The " + world + " is yours!");
  }


  public static double area(double side) {
    return side * side;
  }

  public static double area(double a, double b) {
    return a * b;
  }

}