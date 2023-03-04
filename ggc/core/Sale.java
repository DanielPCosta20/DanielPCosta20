package ggc.core;

public class Sale extends Transaction {
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = -9089804567903064714L;
    private Product _product;
    private int _quantity;
    private int _limitDate;
    private boolean _paid;
    private double _amountPaid;
    private PaymentPeriod _paymentPeriod;
    private int _payDate;

    /**
     * 
     * @param id
     * @param pro
     * @param partner
     * @param limitDate
     * @param quantity
     * @param currentDate
     */
    Sale(int id, Product pro, Partner partner, int limitDate, int quantity, int currentDate) {
        super(id, partner);
        _limitDate = limitDate;
        _product = pro;
        _quantity = quantity;
        _paid = false;
        int cost = quantity * pro.getLowerPrice();
        _baseValue = Double.valueOf(cost);
        _amountPaid = _baseValue;
        _data = currentDate;
    }

    /**
     * 
     * @return o id do produto que esta a ser comprado.
     */
    public String getProductId() {
        return _product.getProductId();
    }
    
    /**
     * 
     * @return quantidade do produto.
     */
    public int getQuantity() {
        return _quantity;
    }

    /**
     * 
     * @return o valor final a pagar.
     */
    public double getAmountPaid() {
        return _amountPaid;
    }
    
    /**
     * 
     * @param value
     * @return o valor final a pagar atualizado.
     */
    public double setAmoutPaid(Double value){
        _amountPaid = value;
        return _amountPaid;
    }

    /**
     * 
     * @return nada ou a data em que a venda foi paga para a visualizacao.
     */
    public String stringPayment() {
        if (!isPaid())
            return "";
        return "|" + getPayDate();
    }

    /**
     * 
     * @return a data em que a venda foi paga.
     */
    public int getPayDate() {
        return _payDate;
    }
    
    /**
     * 
     * @param date
     * @return a data de pagamento alterada.
     */
    public int setPaymentDate(int date) { //muda a data para a data em que foi paga
        return _payDate = date;
    }

    /**
     * 
     * @return a data limite para pagamento.
     */
    public int getLimitDate() {
        return _limitDate;
    }

    /**
     * 
     * @return se a venda ja foi paga ou nao.
     */
    public boolean setPay() {
        _paid = true;
        return _paid;
    }

    /**
     * 
     * @return o produto que esta a ser vendido.
     */
    public Product getProduct() {
        return _product;
    }
    
    /**
     * 
     * @param currentDate
     * calcula e define o intervalo de pagamento que vai influenciar o preco final da venda.
     */
    public void setPaymentPeriod(int currentDate) { 
        int productReferencePaymentPeriod = getProduct().getReferencePaymentPeriod();
        if (_limitDate - currentDate >= productReferencePaymentPeriod) {
            _paymentPeriod = PaymentPeriod.P1;
        }
        else if ((0 <= _limitDate - currentDate) && (_limitDate - currentDate < productReferencePaymentPeriod)) {
            _paymentPeriod = PaymentPeriod.P2;
        }
        else if ((0 < currentDate - _limitDate) && (currentDate - _limitDate <= productReferencePaymentPeriod)) {
            _paymentPeriod = PaymentPeriod.P3;
        }
        else if (currentDate - _limitDate > productReferencePaymentPeriod) {
            _paymentPeriod = PaymentPeriod.P4;
        }
    }

    /**
     * 
     * @return o periodo de pagamento.
     */
    public PaymentPeriod getPaymentPeriod() { //obtem o tempo que demorou
        return _paymentPeriod;
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
        return "VENDA";
    }
    
    @Override
    public Partner getPartner() {
        return _partner;
    }

    @Override
    public String toString() {
        return getType() + "|" + getId() + "|" + getPartnerId() + "|" + getProductId() + "|" + getQuantity() + "|" +
            Math.round(getBaseValue()) + "|" + Math.round(getAmountPaid()) + "|" + getLimitDate() +  stringPayment();
    }
}
