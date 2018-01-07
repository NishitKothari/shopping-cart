package com.ee.evaluation.bill;

import com.ee.evaluation.model.Cart;
import com.ee.evaluation.util.BillingUtils;

import java.math.BigDecimal;

public class BillGenerator {

  private final Cart cart;

  public static BillGenerator of(final Cart cart) {
    return new BillGenerator(cart);
  }

  private BillGenerator(final Cart cart) {
    this.cart = cart;
  }

  /**
   * Returns Total Tax amount for Cart.
   * @return tax amount
   */
  public BigDecimal generateTax() {

    BigDecimal totalTaxAmount = BigDecimal.ZERO;

    for (final Cart.CartItem cartItem : cart.getCartItems()) {
      final BigDecimal cartItemPrice = cartItem.calculateCartItemPrice();
      totalTaxAmount = totalTaxAmount.add(cartItemPrice.multiply(cartItem.getProduct().getTaxRate()));
    }
    return BillingUtils.formatToTwoDecimalPoints(totalTaxAmount);
  }

  /**
   * Returns total of cart Items without Tax.
   *
   * @return total price of cart items with two precision and roundingMode being ROUND_HALF_UP
   */
  public final BigDecimal generateCartItemsTotal() {
    BigDecimal totalCartPrice = BigDecimal.ZERO;

    for (final Cart.CartItem cartItem : cart.getCartItems()) {
      totalCartPrice = totalCartPrice.add(cartItem.calculateCartItemPrice());
    }
    return totalCartPrice;
  }

  /**
   * Generates total payable amount at checkout.
   * @return total payable amount at checkout
   */
  public final BigDecimal generateTotalPayablePrice() {
    return generateTax().add(generateCartItemsTotal());
  }
}
