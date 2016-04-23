package ru.webarch.jstudy.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EquationTest {

    @Test
    public void testSquareNoRoots() {
        Equation equation = new Equation(1, 1, 1);
        Assert.assertEquals(equation.getRootCount(), 0);
    }

    @Test
    public void testSquareOneRoot() {
        Equation equation = new Equation(1, 2, 1);
        Assert.assertEquals(equation.getRootCount(), 1);
    }

    @Test
    public void testSquareTwoRoots() {
        Equation equation = new Equation(1, 5, 6);
        Assert.assertEquals(equation.getRootCount(), 2);
    }

    @Test
    public void testLinearOneRoot() {
        Equation equation = new Equation(0, 5, 6);
        Assert.assertEquals(equation.getRootCount(), 1);
    }

    @Test
    public void testNoRoots() {
        Equation equation = new Equation(0, 0, 6);
        Assert.assertEquals(equation.getRootCount(), 0);
    }

    @Test
    public void testInfinityRoots() {
        Equation equation = new Equation(0, 0, 0);
        Assert.assertEquals(equation.getRootCount(), -1);
    }

}
