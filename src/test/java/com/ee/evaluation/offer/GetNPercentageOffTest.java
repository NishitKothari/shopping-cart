package com.ee.evaluation.offer;

import com.ee.evaluation.model.Cart;
import com.ee.evaluation.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RunWith(JUnit4.class)
public class GetNPercentageOffTest {

  private final Product DOVE_SOAP = new Product("Dove Soap", new BigDecimal(39.99));

  @Test
  public void testIsNotEligibleForOffer(){
    final Set<Cart.CartItem> cartItems = new HashSet<>();
    cartItems.add(new Cart.CartItem(DOVE_SOAP,10));

    GetNPercentageOff getNPercentageOff = new GetNPercentageOff(new BigDecimal(20),new BigDecimal(500));
    Assert.assertFalse(getNPercentageOff.isEligibleForOffer(cartItems));
  }

  @Test
  public void testIsEligibleForOffer(){
    final Set<Cart.CartItem> cartItems = new HashSet<>();
    cartItems.add(new Cart.CartItem(DOVE_SOAP,13));

    GetNPercentageOff getNPercentageOff = new GetNPercentageOff(new BigDecimal(20),new BigDecimal(500));
    Assert.assertTrue(getNPercentageOff.isEligibleForOffer(cartItems));
  }

}
