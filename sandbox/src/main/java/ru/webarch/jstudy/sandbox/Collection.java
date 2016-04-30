package ru.webarch.jstudy.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collection {

    public static void main(String[] args) {

        String[] arrayOfLangs = {"Java", "C#", "Python", "PHP"};
//
//        for (int i = 0; i < arrayOfLangs.length; i++) {
//            System.out.println("Я хочу выучить " + arrayOfLangs[i]);
//        }

//        List<String> listOfLangs = new ArrayList<String>();
//        listOfLangs.add("Java");
//        listOfLangs.add("PHP");

        List<String> listOfLangs = Arrays.asList(arrayOfLangs);

//        for (int i = 0; i < listOfLangs.size(); i++) {
//            System.out.println("Я хочу выучить " + listOfLangs.get(i));
//        }

        for (String lang : listOfLangs) {
            System.out.println("Я хочу выучить " + lang);
        }

    }

}
