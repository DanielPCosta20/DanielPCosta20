package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Partner implements Serializable,Observer {

    /** Serial number for serialization. */
    private static final long serialVersionUID = -8682339134116755716L;
    private String _id;
    private String _name;
    private String _address;
    private boolean _interested;
    private StatuteOfPartner _statute;
    private int _points;
    private int _ordersValue;
    private int _salesValue;
    private int _salesPaid;
    private ArrayList<Notification> _notifications;
    private ArrayList<Transaction> _transactions;
    private ArrayList<Acquisition> _acquisitions;
    private ArrayList<BreakdownSale> _breakdownSales;
    private ArrayList<Sale> _sales;
    private ArrayList<Batch> _batchs;

    /**
     * 
     * @param id
     * @param name
     * @param address
     */
    protected Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _statute = new Normal();
        _points = 0;
        _ordersValue = 0;
        _salesValue = 0;
        _salesPaid = 0;
        _interested = true;
        _notifications = new ArrayList<>();
        _transactions = new ArrayList<>();
        _acquisitions = new ArrayList<>();
        _sales = new ArrayList<>();
        _breakdownSales = new ArrayList<>();
        _batchs = new ArrayList<>();
    }

    /**
     * 
     * @return id do parceiro.
     */
    public String getPartnerId() {
        return _id;
    }

    /**
     * 
     * @return nome do parceiro.
     */
    public String getName() {
        return _name;
    }

    /**
     * 
     * @return morada do parceiro.
     */
    public String getAddress() {
        return _address;
    }

    /**
     * 
     * @return estatuto do parceiro.
     */
    public StatuteOfPartner getStatuteOfPartner() {
        return _statute;
    }

    /**
     * 
     * @param statute
     */
    public void setStatus (StatuteOfPartner statute) {
        _statute = statute;
    }

    /**
     * 
     * @return pontos do parceiro.
     */
    public int getPoints() {
        return _points;
    }

    /**
     * 
     * @return se o parceiro esta interessado ou nao.
     */
    public boolean isInterested() {
        return _interested;
    }

    /**
     * altera o estado de interessado do parceiro.
     */
    public void toggleInterested() {
        _interested = !_interested;
    }

    /**
     * 
     * @param productId
     * @return se o parceiro esta interessado ou nao na notificacao.
     */
    public boolean isInterestedProduct(String productId){
        for(Notification x : _notifications){
            if (x.getProductId().equals(productId)){
                return x.isInterested();
            }
        }
        return false; 
    }

    /**
     * 
     * @param productId
     */
    public void toggleInterestedProduct(String productId){
        for (Notification x : _notifications){
            if (x.getProductId().equals(productId)){
                x.toggleInterested();
            }
        }
    }

    /**
     * 
     * @return o valor das aquisicoes do parceiro.
     */
    public int getOrdersValue() {
        return _ordersValue;
    }

    /**
     * 
     * @param value
     * @return o valor das ordens alterado.
     */
    public int setOrdersValues(Double value) {
        _ordersValue += value;
        return (int) Math.round(_ordersValue);
    }

    /**
     * 
     * @return o valor das vendas do parceiro.
     */
    public int getSalesValue() {
        return _salesValue;
    }

    /**
     * 
     * @param value
     * @return o valor das vendas alterado.
     */
    public double setSalesValue(double value) {
        _salesValue += value;
        return _salesValue;
    }

    /**
     * 
     * @return o valor das vendas pagas do parceiro.
     */
    public int getSalesPaid() {
        return _salesPaid;
    }

    /**
     * 
     * @param paidValue
     * @return o valor das vendas pagas alterado.
     */
    public int setSalesPaid(double paidValue) {
        _salesPaid += paidValue;
        return _salesPaid;
    }

    /**
     * 
     * @return todas as transacoes do parceiro.
     */
    public List<Transaction> getAllTransactions(){
        return _transactions;
    }

    /**
     * 
     * @param t
     */
    public void addTransaction(Transaction t){
        _transactions.add(t);
    }

    /**
     * 
     * @return todas as aquisicoes do parceiro.
     */
    public List<Acquisition> getAllAcquisitions(){
        return _acquisitions;
    }

    /**
     * 
     * @param t
     */
    public void addAcquisitions(Acquisition t){
        _acquisitions.add(t);
    }

    /**
     * 
     * @return todas as vendas do parceiro.
     */
    public ArrayList<Sale> getAllSales(){
        return _sales;
    }


    /**
     * 
     * @return todas as desagregaçoes do parceiro.
     */
    public ArrayList<BreakdownSale> getAllBreakdownSales(){
        return _breakdownSales;
    }

    /**
     * 
     * @param t
     */
    public void addSales(Sale t){
        _sales.add(t);
    }

    /**
     * 
     * @param b
     */
    public void addBreakdownSales(BreakdownSale b) {
        _breakdownSales.add(b);
    }

    /**
     * 
     * @return todas as notificacoes do parceiro.
     */
    public ArrayList<Notification> getNotifications() {
        return _notifications;
    }

    /**
     * 
     * @param p
     * @param method
     * @param price
     * @return a notificacao criada.
     */
    public Notification createNotification( Product p, String method,double price) {
        Notification n = new Notification(p, method,price);
        return n;
    }

    /**
     * 
     * @param batch
     */
    public void addBatch(Batch batch){
        _batchs.add(batch);
    }
    
    /**
     * 
     * @return todos os lotes associados ao parceiro.
     */
    public Collection<Batch> getBatchs(){
        return _batchs;
    }
    
    /**
     * 
     * @param b
     */
    public void removeBatch(Batch b){
        _batchs.remove(b);
    }

    public Sale getSaleById(int transactionId){
        for(Sale x:_sales){
            if (x.getId() == transactionId){
                return x;
            }
        }
        return null;
    }

    /**
     * 
     * @param currentDate
     * @param transactionId
     * @return novo valor da venda.
     */
    Double updateSalePrice(int currentDate, int transactionId) {
        Sale sale = getSaleById(transactionId);
        if (sale.isPaid()) {
            return sale.getAmountPaid();
        }
        sale.setPaymentPeriod(currentDate);
        Double newPrice = _statute.updateCostSale(currentDate, sale.getLimitDate(), sale.getPaymentPeriod(), sale.getBaseValue());
        sale.setAmoutPaid(newPrice);
        sale.setPaymentDate(currentDate);
        return newPrice;
    }
    
    /**
     * 
     * @param currentDate
     * @param transactionId
     * @return o valor que foi pago.
     */
    Double pay(int currentDate, int transactionId) {
        Double paidValue = updateSalePrice(currentDate, transactionId);
        setSalesPaid(paidValue);
        Sale sale = getSaleById(transactionId);
        sale.setPay();
        
        //setPoints(updatePoints(_points, currentDate, sale.getLimitDate(), paidValue));
        updatePoints(_points, currentDate, sale.getLimitDate(), paidValue);
        StatuteOfPartner newStatute = updateStatus(_statute, currentDate, sale.getLimitDate(), _points);
        
        if (_statute instanceof Selection && newStatute instanceof Normal) {
            _points = (int) Math.round(0.1*_points);
        }
        else if (_statute instanceof Elite && newStatute instanceof Selection) {
            _points = (int) Math.round(0.1*_points);
        }
        setStatus(newStatute);
        return paidValue;
    }
    
    /**
     * 
     * @param points
     * @param currentDate
     * @param limitDate
     * @param paidValue
     * @return os pontos atualizados.
     */
    public int updatePoints(int points, int currentDate, int limitDate, Double paidValue) {
        int interval = limitDate - currentDate;;
        if (interval >= 0) {  // paid before the limit date
            return _points += (int) Math.round(paidValue)*10;
        }
        else{
            return _points += points;
        }
    }
    
    /**
     * 
     * @param partnerStatus
     * @param currentDate
     * @param limitDate
     * @param points
     * @return o estatuto do parceiro.
     */
    public StatuteOfPartner updateStatus(StatuteOfPartner partnerStatus, int currentDate, int limitDate, double points) {
        int interval = limitDate - currentDate;;
        if (interval >= 0) {
            if (points > 25000) {
                return new Elite();
            }
            else if (points > 2000) {
                return new Selection();
            }
        }
        else{
            if (partnerStatus instanceof Elite && interval < -15) {
                return new Selection();
            }
            else if (partnerStatus instanceof Selection && interval < -2) {
                return new Normal();
            }
        }
        return partnerStatus;
    }
    
    
    @Override
    public void update(Product product,String method,double price){
        Notification e = new Notification(product, method,price);
        _notifications.add(e);
    }

    public String toString() {
        return getPartnerId() + "|" + getName() + "|" + getAddress() + "|" + getStatuteOfPartner() + "|" 
        + getPoints() + "|" + getOrdersValue() + "|" + (int)Math.round(getSalesValue()) + "|" + getSalesPaid();
    }
}