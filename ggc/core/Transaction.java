package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

     /** Serial number for serialization. */
    private static final long serialVersionUID = -4786659994593930406L;
    private int _id;
    protected double _baseValue;
    protected int _data;
    protected Partner _partner;
    protected double _price;

    /**
     * 
     * @param id
     * @param partner
     * @param data
     */
    Transaction(int id, Partner partner, int data) {
        _id = id;
        _data = data;
        _partner = partner;
    }

    /**
     * 
     * @param id
     * @param partner
     */
    Transaction(int id, Partner partner) {
        _id = id;
        _partner = partner;
    }

    /**
     * 
     * @return id da transacao.
     */
    public int getId() {
        return _id;
    }

    /**
     * 
     * @return data da transacao.
     */
    public int getData() {
        return _data;
    }

    /**
     * 
     * @return valor inicial a pagar.
     */
    public int getBaseValue() {
        return (int)Math.round(_baseValue);
    } 

    /**
     * 
     * @return o preco da transacao.
     */
    public int getPrice() {
        return (int) Math.round(_price);
    }

    public void setBaseValue(double b){
        _baseValue = b;
    }

    public abstract Partner getPartner();
    public abstract boolean isPaid();
    public abstract String getPartnerId();
    public abstract String getType();

    @Override
    public abstract String toString();
    
}