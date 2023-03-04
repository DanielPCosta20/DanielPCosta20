package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = -2501764125570375093L;
    private String _method;
    private String _productId;
    private double _price;
    private boolean _notified;
    private boolean _interested;

    /**
     * 
     * @param product
     * @param method
     * @param price
     */
    public Notification(Product product, String method, double price) {
        _method = method;
        _productId = product.getProductId();
        _price = price;
        _notified = false;
        _interested = true;
    }

    /**
     * 
     * @return id do produto associado a notificacao
     */
    public String getProductId() {
        return _productId;
    }

    /**
     * 
     * @return preco associado a notificacao
     */
    public double getPrice() {
        return _price;
    }

    /**
     * 
     * @return metodo para receber as notificacoes.
     */
    public String getMethod() {
        return _method;
    }

    /**
     * 
     * @param m
     */
    public void setMethod(String m) {
         _method = m;
    }

    /**
     * 
     * @return se a notificacao ja foi vista ou nao.
     */
    public boolean isNotified() {
        return _notified;
    }

    /**
     * altera a notificacao para vista.
     */
    public void toggleNotified() {
        _notified = !_notified;
    }

    /**
     * 
     * @return se o parceiro esta interessado na notificacao ou nao.
     */
    public boolean isInterested() {
        return _interested;
    }

    /**
     * altera o parceiro de interessado para nao interessado e vice-versa.
     */
    public void toggleInterested() {
        _interested = !_interested;
    }

    @Override
    public String toString() {
        return getMethod() + "|" + getProductId() + "|" + Math.round(getPrice());
    }
}