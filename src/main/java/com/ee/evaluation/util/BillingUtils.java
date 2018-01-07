package com.ee.evaluation.util;

import java.math.BigDecimal;

public class BillingUtils {

  public static BigDecimal formatToTwoDecimalPoints(final BigDecimal input){
    return input.setScale(2,BigDecimal.ROUND_HALF_UP);
  }
}
