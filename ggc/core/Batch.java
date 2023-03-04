package ggc.core;

import java.io.Serializable;

public class Batch implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = -8682339134116755716L;
    private Product _product;
    private Partner _partner;
    private double _price;
    private int _quantity;
    
    /**
     * 
     * @param product
     * @param partner
     * @param price
     * @param quantity
     */
    protected Batch(Product product, Partner partner, double price, int quantity) {
        _product = product;
        _partner = partner;
        _price = price;
        _quantity = quantity;
    }
    
    /**
     * 
     * @return id do produto.
     */
    public String getProductID() {
        return _product.getProductId();
    }

    /**
     * 
     * @return id do parceiro.
     */
    public String getPartnerID() {
        return _partner.getPartnerId();
    }

    /**
     * 
     * @return preco maximo arredondado.
     */
    public int getPrice() {
        return (int)Math.round(_price);
    }

    /**
     * 
     * @return quantidade disponivel.
     */
    public int getQuantity() {
        return _quantity;
    }

    /**
     * 
     * @param i
     */
    public void decreaseQuantity(int i){
        _quantity -= i;
    }

    @Override
    public String toString() {
        return getProductID() + "|" + getPartnerID() + "|" + getPrice() + "|" + getQuantity();
    }
}