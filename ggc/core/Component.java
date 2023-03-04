package ggc.core;

import java.io.Serializable;

public class Component implements Serializable {
	
	/** Serial number for serialization. */
    private static final long serialVersionUID = -8682339134116755716L;
	private int _quantity;
	private Product _product;

	/**
	 * 
	 * @param quantidade
	 * @param product
	 */
	public Component(int quantidade, Product product) {
		_quantity = quantidade;
		_product = product;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity){
		_quantity = quantity;
	}

	/**
	 * 
	 * @return quantidade existente da componente.
	 */
	public int getQuantity(){
		return _quantity;
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
	 * @return produto associado a componente.
	 */
	public Product getProduct(){
		return _product;
	}

	public String toString(){
		return "" + getProduct().getProductId() + ":" + getQuantity() + "#";
	}
}
