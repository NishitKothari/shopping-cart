package com.ee.evaluation.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class BillingUtilsTest {

  @Test
  public void testFormatToTwoDecimalPlace() {
    Assert.assertEquals(new BigDecimal(0.57).setScale(2, BigDecimal.ROUND_HALF_UP), BillingUtils
            .formatToTwoDecimalPoints(new BigDecimal(0.567)));
    Assert.assertEquals(new BigDecimal(0.56).setScale(2, BigDecimal.ROUND_HALF_UP), BillingUtils
            .formatToTwoDecimalPoints(new BigDecimal(0.564)));
  }
}
