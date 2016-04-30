package ru.webarch.jstudy.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTest {

    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeFastLong() {
        Assert.assertTrue(Primes.isPrimeFast((long) Integer.MAX_VALUE));
    }

    @Test
    public void testNonPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    }


    @Test(enabled = false)
    public void testPrimeLong() {
        long number = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(number));
    }

    @Test(enabled = false)
    public void testNonPrimeLong() {
        long number = Integer.MAX_VALUE - 2;
        Assert.assertFalse(Primes.isPrime(number));
    }


    @Test(enabled = true)
    public void testPrimeViaWhile() {
        Assert.assertTrue(Primes.isPrimeViaWhile(Integer.MAX_VALUE));
    }

    @Test(enabled = true)
    public void testNonPrimeViaWhile() {
        Assert.assertFalse(Primes.isPrimeViaWhile(Integer.MAX_VALUE - 2));
    }

}
