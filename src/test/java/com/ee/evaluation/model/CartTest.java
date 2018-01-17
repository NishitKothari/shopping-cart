package com.ee.evaluation.model;

import com.ee.evaluation.bill.BillGenerator;
import com.ee.evaluation.offer.Offer;
import com.ee.evaluation.util.BillingUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class CartTest {


  private static final BigDecimal PRICE_224_94 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(224.94));
  
  private static final BigDecimal PRICE_359_91 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(359.91));

  private static final BigDecimal PRICE_314_96 = BillingUtils.formatToTwoDecimalPoints(new BigDecimal(314.96));
  
  private static final BigDecimal TAX_RATE = new BigDecimal(0.125);

  private final Product DOVE_SOAP = new Product("Dove Soap", new BigDecimal(39.99));
  private final Product AXE_DEO = new Product("Axe Deo", new BigDecimal(99.99));

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
    final Product product1 = new Product("Sample_Product_Name", new BigDecimal(1));
    final Product product2 = new Product("Sample_Product_Name", new BigDecimal(10));

    Assert.assertEquals(product1,product2);
  }

  @Test
  public void productWithSameNameInCartItemShouldBeEqual(){
    final Product product = new Product("Sample_Product_Name", new BigDecimal(10));

    Assert.assertEquals(new Cart.CartItem(product,5),new Cart.CartItem(product,3));
  }

  @Test
  public void testCartPriceAtCheckoutOfFiveDoveSoapsEachOf39dot99() {
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 5));

    //Then
    Assert.assertEquals(BillGenerator.of(cart).generateTotal(TAX_RATE), PRICE_224_94);
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 5);
  }

  @Test
  public void testCartPriceWhenSameProductIsAddedTwice() {
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 5)).addItem(new Cart.CartItem(DOVE_SOAP, 3));

    //Then
    Assert.assertEquals(BillGenerator.of(cart).generateTotal(TAX_RATE), PRICE_359_91);
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 8);
  }

  @Test
  public void testGeneratingBillIncludeTax(){
    //Given
    Cart cart = new Cart();

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 2)).addItem(new Cart.CartItem(AXE_DEO, 2));

    //Then
    Assert.assertEquals(PRICE_314_96,BillGenerator.of(cart).generateTotal(TAX_RATE));
    Assert.assertEquals(cart.findQuantityByProduct(DOVE_SOAP), 2);
    Assert.assertEquals(cart.findQuantityByProduct(AXE_DEO), 2);
  }

  @Test
  public void testProductCartPriceWithBuyXGetYOffer(){
    //Given
    Cart cart = new Cart();

    int xValue = 2;
    int yValue = 1;

    final Offer buy2Get1Offer = new Offer(xValue, yValue, DOVE_SOAP);

    //When
    cart.addItem(new Cart.CartItem(DOVE_SOAP, 3)).addItem(new Cart.CartItem(AXE_DEO, 2)).applyOffer(buy2Get1Offer);

    //Then
    Assert.assertEquals(BillingUtils.formatToTwoDecimalPoints(new BigDecimal(314.96)),BillGenerator.of(cart).generateTotal(TAX_RATE));
  }
}
