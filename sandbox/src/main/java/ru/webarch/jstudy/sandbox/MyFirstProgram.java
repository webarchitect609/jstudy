package ru.webarch.jstudy.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {

    hello("yours");
    hello("ours");
    hello("theirs");

    Square sq = new Square(5);
    System.out.println("Площадь квадрата со стороной " + sq.side + " равна " + sq.area());


    Rectangle rc = new Rectangle(4, 9);
    System.out.println("Площадь прямоугольника со сторонами " + rc.width + " и " + rc.height + " равна " + rc.area());


  }

  public static void hello(String whos) {
    System.out.println("The World is " + whos + "!");
  }


}