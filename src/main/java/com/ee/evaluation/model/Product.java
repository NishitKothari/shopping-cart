package com.ee.evaluation.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class which represents Product entity of Shopping cart.
 * product name is assumed to be unique across application.
 */
public final class Product {

  private final String name;
  private final BigDecimal unitPrice;
  private final BigDecimal taxRate;

  public Product(final String name, final BigDecimal unitPrice, final BigDecimal taxRate) {
    this.name = name;
    this.unitPrice = unitPrice;
    this.taxRate = taxRate;
  }

  /**
   * Returns Product name.
   * @return product name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns unit price of product.
   * @return unit price of product
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /**
   * Returns Tax Rate of a product.
   * @return tax rate
   */
  public BigDecimal getTaxRate() {
    return taxRate;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;
    final Product product = (Product) o;
    return Objects.equals(getName(), product.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Product{");
    sb.append("name='").append(name).append('\'');
    sb.append(", unitPrice=").append(unitPrice);
    sb.append(", taxRate=").append(taxRate);
    sb.append('}');
    return sb.toString();
  }
}
