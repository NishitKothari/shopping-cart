package com.ee.evaluation.offer;

import com.ee.evaluation.model.Cart;

import java.math.BigDecimal;
import java.util.Set;

public interface Offer {

  BigDecimal discountedPrice(Set<Cart.CartItem> cartItems);
}
