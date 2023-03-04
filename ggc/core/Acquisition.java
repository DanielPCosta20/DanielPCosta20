package ggc.core;

public class Acquisition extends Transaction {

    /** Serial number for serialization. */
    private static final long serialVersionUID = -8682339134116755716L;
    private double _price;
    private int _currentDate;
    private boolean _paid;
    private Product _pro;
    private int _quantity;

    /**
     * 
     * @param id
     * @param date
     * @param partner
     * @param product
     * @param price
     * @param quantity
     */
    protected Acquisition(int id, int date, Partner partner,Product product, double price,int quantity) {
        super(id, partner);
        _currentDate = date;
        _paid = true;
        _pro = product;
        _price = price;
        _quantity = quantity;  
    }

    /**
     * 
     * @return id do produto adquirido.
     */
    public String getProductId() {
        return _pro.getProductId();
    }
    
    /**
     * 
     * @return quantidade do produto adquirido.
     */
    public int getQuantity() {
        return _quantity;
    }

    /**
     * 
     * @return data em que o pagamento foi efetuado = data atual.
     */
    protected int getPaymentDate(){
        return _currentDate;
    }

    @Override
    public int getPrice() {
        return (int) Math.round(_price);
    }

    @Override
    public boolean isPaid() {
        return _paid;
    }

    @Override
    public String getPartnerId() {
        return _partner.getPartnerId();
    }

    @Override
    public String getType() {
        return "COMPRA";
    }
    
    @Override
    public Partner getPartner() {
        return _partner;
    }
    
    @Override
    public String toString() {
        return getType() + "|" + getId() + "|" + getPartnerId() + "|" + getProductId() + "|" + getQuantity() + "|" +
            Math.round(getPrice())*getQuantity() + "|" + getPaymentDate();
    }
}