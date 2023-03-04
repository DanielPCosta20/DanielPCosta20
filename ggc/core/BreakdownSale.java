package ggc.core;

import java.util.ArrayList;
import java.util.List;

public class BreakdownSale extends Transaction {

    private static final long serialVersionUID = -8682339134116755716L;
    private boolean _paid;
    private Product _pro;
    private int _quantity;
    private List<Component> list;
    private List<Double> _componentsPrices;
    private int _quantityAP; 
    private double _priceComponents;

    BreakdownSale(int id, Product product, Partner partner, int quantity, int data) {
        super(id, partner, data);
        _pro = product;
        _paid = true;
        _price = 0;
        _quantity = quantity;
        _componentsPrices = new ArrayList<>();
    }

    public void addComponentPrice(double b){
        _componentsPrices.add(b);
    }

    public void setQuatityAP(int quantity){
        _quantityAP = quantity;
    }

    public int getQuantityAP(){
        return _quantityAP;
    }

    public void setPriceComponents(double price){
        _priceComponents = price;
    }

    public double getPriceComponents(){
        return _priceComponents;
    }

    
    public String getProductId() {
        return _pro.getProductId();
    }

    public void setPrice(double b){
        if(b>0){
        _price = b;
        }
        else{
            _price = 0;
        }
    }

    public void setComponents(List<Component> b){
        list = b; 
    }

    public int getQuantity() {
        return _quantity;
    }

    @Override
    public Partner getPartner() {
        return _partner;
    }

    @Override
    public boolean isPaid() {
        return false;
    }

    @Override
    public String getPartnerId() {
        return _partner.getPartnerId();
    }

    @Override
    public String getType() {
        return "DESAGREGAÇÃO";
    }

    @Override
    public String toString() {
        String d = getType() + "|" + getId() + "|" + getPartnerId() + "|" + getProductId() + "|" + getQuantity() + "|" + Math.round(getBaseValue() ) + "|" + Math.round(getAmountPaid()) + "|" + getPayDate() + "|" ;
        int f = 0;
        for(Component x : list){
            d += x.getProduct().getProductId() +":" + Math.round(x.getQuantity()*getQuantityAP())  + ":" + Math.round(_componentsPrices.get(f))  + "#";
            f++;
        }
        return d.substring(0,d.length()-1);
    }

    public int getPayDate() {
        return _data;
    }

    public double getAmountPaid() {
        return _price;
    }
    
}