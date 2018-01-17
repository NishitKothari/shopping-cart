package com.ee.evaluation.offer;

import com.ee.evaluation.model.Cart;
import com.ee.evaluation.model.Product;

import java.math.BigDecimal;
import java.util.Set;

public class Offer {

  private final int x;
  private final int y;
  private final Product product;

  public Offer(final int x, final int y, final Product product) {
    this.x = x;
    this.y = y;
    this.product = product;
  }

  public BigDecimal discountedPrice(final Set<Cart.CartItem> cartItems){
    for(final Cart.CartItem cartItem:cartItems){
      if(cartItem.getProduct().equals(product)){
        final int itemsQuantityToNotChargeFor = cartItem.getQuantity() == 2 ? 0 :cartItem.getQuantity()/2;
        return product.getUnitPrice().multiply(new BigDecimal(itemsQuantityToNotChargeFor));
      }
    }
    return BigDecimal.ZERO;
  }

}
