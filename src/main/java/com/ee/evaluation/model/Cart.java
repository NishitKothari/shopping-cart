package com.ee.evaluation.model;

import com.ee.evaluation.offer.BuyXGetYOffer;
import com.ee.evaluation.offer.Offer;
import com.ee.evaluation.util.BillingUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Cart class
 */
public final class Cart {

  private final Set<CartItem> items = new LinkedHashSet<>();

  private Optional<Offer> offer = Optional.empty();

  /**
   * Adds Item to a Cart if same product is not available in cart. If same product already exist
   * then it modifies quantity of that product.
   *
   * @param cartItem item to be added in cart
   * @return Cart with added item
   */
  public final Cart addItem(final CartItem cartItem) {

    final boolean doesProductExistInCart = items.contains(cartItem);

    if (doesProductExistInCart) {
      final Predicate<CartItem> sameProductPredicate = item -> item.getProduct().equals(cartItem.getProduct());
      items.stream().filter(sameProductPredicate).findFirst().ifPresent(sameItem -> sameItem
              .addQuantity(cartItem.getQuantity()));

    } else {
      items.add(cartItem);
    }

    return this;
  }

  public final Cart applyOffer(final Offer buy2Get1Offer) {
    this.offer = Optional.of(buy2Get1Offer);
    return this;
  }

  /**
   * Returns read only copy of Cart Items.
   * @return cart items
   */
  public Set<CartItem> getCartItems() {
    return Collections.unmodifiableSet(items);
  }

  public Optional<Offer> getOffer() {
    return offer;
  }

  /**
   * Finds number of items for a given product in the cart
   *
   * @param product product to search for.
   * @return number of items of a given product
   */
  public int findQuantityByProduct(final Product product) {
    final Optional<CartItem> cartItem = items.stream().filter(item -> item.getProduct().equals
            (product)).findFirst();

    return cartItem.map(CartItem::getQuantity).orElse(0);
  }



  /**
   * Class to hold Cart Item with Product and quantities. It is assumed that there can not be two
   * CartItem with same product name in the cart. Instead we modify quantity of existing product of
   * the cart.
   */
  public static class CartItem {

    private final Product product;

    private int quantity;

    public CartItem(final Product product, final int quantity) {

      //Validate arguments
      validateProductToBeAdded(product);
      validateQuantity(quantity);

      this.product = product;
      this.quantity = quantity;
    }

    private void validateProductToBeAdded(final Product product) {
      if (product == null)
        throw new IllegalArgumentException("Product can not be Null");
    }

    private void validateQuantity(final int quantity) {
      if (quantity <= 0)
        throw new IllegalArgumentException("Quantity can be only positive integer");
    }

    public Product getProduct() {
      return product;
    }

    public int getQuantity() {
      return quantity;
    }

    void addQuantity(final int quantity) {
      validateQuantity(quantity);
      this.quantity += quantity;
    }

    public BigDecimal calculateCartItemPrice() {
      return BillingUtils.formatToTwoDecimalPoints(this.getProduct().getUnitPrice().multiply(new BigDecimal(this.getQuantity())));
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof CartItem)) return false;
      final CartItem cartItem = (CartItem) o;
      return Objects.equals(getProduct(), cartItem.getProduct());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getProduct());
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("CartItem{");
      sb.append("product=").append(product);
      sb.append(", quantity=").append(quantity);
      sb.append(", cartPrice=").append(calculateCartItemPrice());
      sb.append('}');
      return sb.toString();
    }
  }
}

