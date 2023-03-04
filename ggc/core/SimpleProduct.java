package ggc.core;

public class SimpleProduct extends Product {

    /**
     * 
     * @param id
     * @param quantity
     * @param simder
     * @param price
     */
    public SimpleProduct(String id, int quantity, boolean simder,double price){
        super(id,quantity, simder,price);
        _productReferencePaymentPeriod = 5;
    }
}