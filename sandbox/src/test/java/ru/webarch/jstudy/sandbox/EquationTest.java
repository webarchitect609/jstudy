package ru.webarch.jstudy.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EquationTest {

    @Test
    public void testNoRoots() {
        Equation equation = new Equation(1, 1, 1);
        Assert.assertEquals(equation.getRootCount(), 0);
    }

    @Test
    public void testOneRoot() {
        Equation equation = new Equation(1, 2, 1);
        Assert.assertEquals(equation.getRootCount(), 1);
    }

    @Test
    public void testTwoRoots() {
        Equation equation = new Equation(1, 5, 6);
        Assert.assertEquals(equation.getRootCount(), 2);
    }

}
