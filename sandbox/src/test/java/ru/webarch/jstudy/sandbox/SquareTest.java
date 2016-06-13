package ru.webarch.jstudy.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gripinskiy on 16.04.16.
 */
public class SquareTest {

    @Test
    public void testArea() {

        Square sq = new Square(4);
        Assert.assertEquals(sq.area(), 16.90);

    }
}
