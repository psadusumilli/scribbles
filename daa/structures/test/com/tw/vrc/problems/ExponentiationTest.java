package com.tw.vrc.problems;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class ExponentiationTest {

    @Test
    public void shouldWork() {
      assertEquals(8d, new Exponentiation(2d).to(3));
      assertEquals(256d, new Exponentiation(16d).to(2));
      assertEquals(1d, new Exponentiation(2d).to(0));
      assertEquals(2d, new Exponentiation(2d).to(1));
      assertEquals(81d, new Exponentiation(3d).to(4));
      assertEquals(169d, new Exponentiation(13d).to(2));
      assertEquals(625d, new Exponentiation(5d).to(4));
    }
}
