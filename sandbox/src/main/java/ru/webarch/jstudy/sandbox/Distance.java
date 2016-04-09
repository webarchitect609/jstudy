package ru.webarch.jstudy.sandbox;

/**
 * Created by gripinskiy on 09.04.16.
 */
public class Distance {


  public static void main(String[] args) {

    Point M = new Point(3.1, 1.2);
    Point N = new Point(-4, -2.5);

    String distanceStatement = "Расстояние между M(" + M.x + ", " + M.y + ") и N(" + N.x + ", " + N.y + ") равно ";

    //Расстояние между N и M через функцию
    System.out.println(distanceStatement + distance(M, N) + " (функция)");

    //Расстояние между N и M через метод
    System.out.println(distanceStatement + M.distance(N) + " (метод)");

    //Расстояние от точки до самой себя через метод (должно быть 0)
    System.out.println("Расстояние от точки M(" + M.x + ", " + M.y + ") до самой себя " + M.distance(M) + " (метод)");

  }

  /**
   * Возвращает расстояние между точками a и b
   *
   * @param a
   * @param b
   * @return
   */
  public static double distance(Point a, Point b) {

    return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));

  }

}
