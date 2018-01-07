package com.ee.evaluation.model;

import com.ee.evaluation.bill.BillGenerator;
import com.ee.evaluation.model.Cart;
import com.ee.evaluation.model.Product;
import com.ee.evaluation.util.BillingUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class CartTest {

  private static final BigDecimal PRICE_199_95 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(199.95));

  private static final BigDecimal PRICE_319_92 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(319.92));

  private static final BigDecimal PRICE_314_96 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(314.96));

  private static final BigDecimal PRICE_35_00 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(35.00));

  private static final BigDecimal TAX_RATE = new BigDecimal(0.125);

  private final Product DOVE_SOAP = new Product("Dove Soap", new BigDecimal(39.99), TAX_RATE);
  private final Product AXE_DEO = new Product("Axe Deo", new BigDecimal(99.99), TAX_RATE);

  @Test(expected = IllegalArgumentException.class)
  public void addingNullProductToCartShouldThrowException(){

    new Cart().addItem(new Cart.CartItem(null,10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addingZeroQuantityToCartShouldThrowException(){

    new Cart().addItem(new Cart.CartItem(DOVE_SOAP,0));
  }

  @Test
  public void productWithSameNameShouldBeEqual(){
    final Product product1 = new Product("Sample_Product_Name", new BigDecimal(1), TAX_RATE);
    final Product product2 = new Product("Sample_Product_Name", new BigDecimal(10), TAX_RATE);

    Assert.assertEquals(product1,product2);
  }

  @Test
  public void productWithSameNameInCartItemShouldBeEqual(){
    final Product product = new Product("Sample_Product_Name", new BigDecimal(10), TAX_RATE);

    Assert.assertEquals(new Cart.CartItem(product,5),new Cart.CartItem(product,3));
  }

  @Test
  public void testCartPriceAtCheckoutOfFiveDoveSoapsEachOf39dot99() {
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 5));

    //Then
    Assert.assertEquals(BillGenerator.of(cart).generateCartItemsTotal(), PRICE_199_95);
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 5);
  }

  @Test
  public void testCartPriceWhenSameProductIsAddedTwice() {
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 5)).addItem(new Cart.CartItem(DOVE_SOAP, 3));

    //Then
    Assert.assertEquals(BillGenerator.of(cart).generateCartItemsTotal(), PRICE_319_92);
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 8);
  }

  @Test
  public void testGeneratingBillIncludeTax(){
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 2)).addItem(new Cart.CartItem(AXE_DEO, 2));

    //Then
    Assert.assertEquals(PRICE_35_00,BillGenerator.of(cart).generateTax());
    Assert.assertEquals(PRICE_314_96,BillGenerator.of(cart).generateTotalPayablePrice());
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 2);
    Assert.assertEquals(cart.findQuantityByProduct(AXE_DEO), 2);
  }
}
