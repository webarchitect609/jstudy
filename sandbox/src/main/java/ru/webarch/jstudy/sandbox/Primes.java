package ru.webarch.jstudy.sandbox;


public class Primes {

    /**
     * Является ли простым числом
     *
     * @param number Число для проверки
     * @return true для простого числа
     */
    public static boolean isPrime(int number) {

        for (int divider = 2; divider < number; divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrime(long number) {

        for (long divider = 2; divider < number; divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeViaWhile(int number) {

        int divider = 2;

        while (divider++ < number && number % divider != 0) {}

        return divider == number;
    }

    public static boolean isPrimeFast(int number) {

        for (int divider = 2; divider < (int)Math.sqrt(number); divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeFast(long number) {

        for (long divider = 2; divider < (long)Math.sqrt(number); divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }

}
