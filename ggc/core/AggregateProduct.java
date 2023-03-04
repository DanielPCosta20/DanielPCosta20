package ggc.core;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    /**
     * 
     * @param id
     * @param quantity
     * @param recipe
     * @param price
     */
    public AggregateProduct(String id,int quantity,Recipe recipe,double price){
        super(id, quantity ,false,price);
        _recipe = recipe; 
        _productReferencePaymentPeriod = 3;
    }

    /**
     * 
     * @return receita associada ao produto agregado.
     */
    public Recipe getRecipe(){
        return _recipe;
    }

    public String toString(){
        return getProductId() + "|" + getMaxPrice() + "|" + getStock() + "|"+_recipe.toString();
    }
}