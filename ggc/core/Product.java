package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract public class Product implements Serializable {
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = -9089804567903064714L;
	private double _maxprice;
	private String _id;
	private int _quantity;
	private boolean _simples;
	private List<Batch> _batchs =  new ArrayList<>();
	protected int _productReferencePaymentPeriod;
	private List<Observer> _notificationsList;
	
	/**
	 * 
	 * @param id
	 * @param price
	 * @param quantity
	 * @param simder
	 */
	protected Product(String id,int quantity, boolean simder,double price) {
		_id = id;
        _quantity = quantity;
		_simples = simder;
		_notificationsList = new ArrayList<Observer>();
		_maxprice = price;
	}

	/**
	 * 
	 * @param id
	 * @param simder
	 */
	protected Product(String id, boolean simder){
		_id = id;
		_simples = simder;
		_notificationsList = new ArrayList<Observer>();
	}

	/**
	 * 
	 * @param quantity
	 */
	public void addQuantity(int quantity){
		_quantity += quantity;
	}

	/**
	 * 
	 * @return id do produto.
	 */
	public String getProductId() {
        return _id;
    }

	/**
	 * 
	 * @return preco maximo arredondado.
	 */
	public int getMaxPrice() {
		for (Batch x : _batchs){
			if (x.getPrice() > _maxprice){
				_maxprice = x.getPrice();
			}
		}
        return (int)Math.round(_maxprice);
    }

	/**
	 * 
	 * @return preco minimo arredondado
	 */
	public int getLowerPrice() {
		double _minPrice = getMaxPrice();
		for (Batch x : _batchs){
			if (x.getPrice() < _minPrice){
				_minPrice = x.getPrice();
			}
		}
        return (int)Math.round(_minPrice);
    }

	/**
	 * 
	 * @return o lote com o menor preco ou nada.
	 */
	public Batch getBatchwithlowerprice(){
		for(Batch x : _batchs){
			if (x.getPrice() == getLowerPrice()){
				return x;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param b
	 */
	public void removeBatch(Batch b){
		_batchs.remove(b);
	}

	/**
	 * 
	 * @return quantidade de produto disponivel.
	 */
	public int getStock() {
        return _quantity;
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
	 * @return todos os lotes do produto.
	 */
	public Collection<Batch> getBatchs(){
		return _batchs;
	}

	/**
	 * 
	 * @param obs
	 */
    public void registerObserver(Observer obs){
        _notificationsList.add(obs);
    }

	/**
	 * 
	 * @param obs
	 */
    public void unregisterObserver(Observer obs) {
        _notificationsList.remove(obs);
    }

	/**
	 * 
	 * @param p
	 * @param method
	 * @param price
	 */
    public void notifyObserver(Product p,String method,double price) {
        for (Observer observer : _notificationsList) {
            observer.update(p, method,price);
        }
    }

	/**
	 * 
	 * @param pro
	 * @param method
	 * @param price
	 * @param p
	 */
	public void notifyObserver(Product pro,String method,double price,Partner p) {
        for (Observer observer : _notificationsList) {
			if(!observer.equals(p)){
				observer.update(pro, method,price);
			}
        }
    }

	/**
	 * 
	 * @param p
	 * @return se o registo foi bem sucedido.
	 */
    public boolean toggleNotifications(Partner p) {
        if (_notificationsList.contains(p)) {
            unregisterObserver(p);
            return false;
        }
        else {
            registerObserver(p);
            return true;
        }
    }

	/**
	 * 
	 * @return quantidade do produto.
	 */
	public int getQuantity(){
		return _quantity;
	}

	/**
	 * 
	 * @return todos os observadores da notificacao.
	 */
	public List<Observer> getAllObservers(){
		return _notificationsList;
	}

	/**
	 * 
	 * @return se o produto e simples ou derivado.
	 */
	public boolean getSimples(){
		return _simples;
	}

	/**
	 * 
	 * @param paymentPeriod
	 */
	public void setProductReferencePaymentPeriod(int paymentPeriod) {
		_productReferencePaymentPeriod = paymentPeriod;
    }
	
	/**
	 * 
	 * @return a referencia do periodo de pagamento do produto.
	 */
    public int getReferencePaymentPeriod() {
		return _productReferencePaymentPeriod;
    }
	
	@Override
	public String toString() {
		return getProductId() + "|" + getMaxPrice() + "|" + getStock();
	}
}