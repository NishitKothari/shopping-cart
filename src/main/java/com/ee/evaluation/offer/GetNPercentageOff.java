package com.ee.evaluation.offer;

import com.ee.evaluation.model.Cart;
import com.ee.evaluation.util.BillingUtils;

import java.math.BigDecimal;
import java.util.Set;

public class GetNPercentageOff implements Offer {


  private final BigDecimal percentage;

  private final BigDecimal totalCartPriceEligibility;

  public GetNPercentageOff(final BigDecimal percentage, final BigDecimal totalCartPriceEligibility) {

    this.percentage = percentage;
    this.totalCartPriceEligibility = totalCartPriceEligibility;
  }

  @Override
  public BigDecimal discountedPrice(final Set<Cart.CartItem> cartItems) {
    if(isEligibleForOffer(cartItems)){
      return BillingUtils.formatToTwoDecimalPoints(getCartTotal(cartItems).multiply(percentage));
    }
    return BigDecimal.ZERO;
  }

  boolean isEligibleForOffer(final Set<Cart.CartItem> cartItems){
    BigDecimal totalCartPrice = getCartTotal(cartItems);
     return totalCartPrice.compareTo(totalCartPriceEligibility) > 0;
  }

  private BigDecimal getCartTotal(final Set<Cart.CartItem> cartItems) {
    BigDecimal totalCartPrice = BigDecimal.ZERO;

    for (final Cart.CartItem cartItem : cartItems) {
      totalCartPrice = totalCartPrice.add(cartItem.calculateCartItemPrice());
    }
    return totalCartPrice;
  }
}
