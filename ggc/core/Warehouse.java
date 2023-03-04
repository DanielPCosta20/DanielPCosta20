package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 * 
 * @author Bernardo Paulo - 98929
 * @author Daniel Costa - 98930
 * @version Final
 */

public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Parser _parser;
  private Date _date;
  private int _nextTransactionId;
  private Map<String, Product> _products;
  private List<Batch> _batches;
  private Map<String, Partner> _partners;
  private Map<Integer, Transaction> _transactions; 
  private Map<Integer, Acquisition> _acquisitions;
  private Map<Integer, Sale> _sales;
  private Map<Integer, BreakdownSale> _breakdownSales;
  private double _contabilisticBalance;
  private double _availableBalance;
  
  public Warehouse(){
    _parser = new Parser(this);
    _date = new Date();
    _products = new LinkedHashMap<>();
    _batches = new ArrayList<>(); 
    _partners = new LinkedHashMap<>();
    _transactions = new LinkedHashMap<>();
    _acquisitions = new HashMap<>();
    _sales = new HashMap<>();
    _breakdownSales = new HashMap<>();
    _availableBalance = 0.0;
    _contabilisticBalance = 0.0;
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {
    _parser.parseFile(txtfile);
  }

  /**
   *
   * @return a data atual.
   */
  protected int getDate(){
    return _date.getCurrentDate();
  }

  /**
   * 
   * @param numberOfDays
   */
  protected void advanceDays(int numberOfDays){
    _date.advanceDays(numberOfDays);
  }

  /**
   * 
   * @param id
   * @param price
   * @param quantity
   * @param simder
   * @return se o registo foi efetuado com sucesso.
   */
  protected boolean registerSimpleProduct(String id,int quantity, boolean simder,double price) {
    String id_system = id.toUpperCase();
    if (_products.containsKey(id_system)){
      _products.get(id_system).addQuantity(quantity);
      return true; 
    }
    Product p = new SimpleProduct(id,quantity,simder,price);
    _products.put(id_system,p);
    for (Partner x : getAllPartners()){
      p.registerObserver(x);
    }
    return true;
  }

  /**
   * 
   * @param id
   * @param quantity
   * @param recipe
   * @param price
   * @return se o registo foi efetuado com sucesso.
   */
  protected boolean registerAggregateProduct(String id, int quantity, Recipe recipe,double price){
    String id_system = id.toUpperCase();
    if (_products.containsKey(id_system)){
      _products.get(id_system).addQuantity(quantity);
      return true;
    }
    Product p = new AggregateProduct(id ,quantity,recipe,price);
    _products.put(id_system,p);
    for (Partner x : getAllPartners()){
      p.registerObserver(x);
    }
    return true;
  }

  /**
   * 
   * @param id
   * @return o produto ou nada se o produto nao existir.
   */
  protected Product getProduct(String id) {
    String id_system = id.toUpperCase();
    if (_products.containsKey(id_system)) {
      return _products.get(id_system);
    } 
    return null;
  }

  /**
   * 
   * @return lista com todos os produtos.
   */
  protected List<Product> getAllProducts() {
    List<Product> products = new ArrayList<Product>(_products.values());
    return products;
  }

  /**
   * 
   * @param product
   * @param partner
   * @param price
   * @param quantity
   * @return um lote ou nada.
   */
  protected Batch registerBatch(Product product, Partner partner, double price, int quantity){
    Batch e = new Batch(product, partner, price, quantity);
    if (_batches.add(e)){
      return e;
    }
    return null;
  }

  /**
   * 
   * @param id
   * @return um lote ou nada.
   */
  protected Batch getBatch(String id){
    for(Batch x : _batches){
      if (x.getProductID().equals(id)){
        return x;
      }
    }
    return null;
  }

  /**
   * 
   * @return uma lista com todos os lotes.
   */
  protected List<Batch> getAllBatches() {
    List<Batch> batches = new ArrayList<Batch>(_batches);
    return batches;
  }
  
  /**
   * 
   * @param id
   * @param name
   * @param address
   * @return se o resgisto do parceiro foi efetuado com sucesso.
   */
  protected boolean registerPartner(String id, String name, String address) {
    String id_system = id.toUpperCase();
    if (_partners.containsKey(id_system)) 
      return false;
    Partner par = new Partner(id, name, address);
    _partners.put(id_system, par);
    for (Product p : _products.values()) {
      p.registerObserver(par);
    }
    return true;
  }

  /**
   * 
   * @param id
   * @return um parceiro ou nada.
   */
  protected Partner getPartner(String id) {
    String id_system = id.toUpperCase();
    if (_partners.containsKey(id_system)) {
      return _partners.get(id_system);
    } 
    return null;
  }

  /**
   * 
   * @return uma lista com todos os parceiros.
   */
  protected List<Partner> getAllPartners() {
    List<Partner> partners = new ArrayList<Partner>(_partners.values());
    return partners;
  }

  /**
   * 
   * @param partnerId
   * @param productId
   * @return se o registo foi bem sucedido.
   */
  public boolean toggleProductNotifications(String partnerId, String productId) {
    Partner partner = getPartner(partnerId);
    Product product = getProduct(productId);
    partner.toggleInterestedProduct(productId);
    return product.toggleNotifications(partner);
  }

  /**
   * 
   * @param id
   * @return uma transacao ou nada.
   */
  protected Transaction getTransaction(int id)  {
    if (_transactions.containsKey(id)) {
      return _transactions.get(id);
    }
    return null;
  }

  /**
   * 
   * @return uma lista com todas as transacoes.
   */
  protected List<Transaction> getAllTransactions() {
    List<Transaction> transactions = new ArrayList<Transaction>(_transactions.values());
    return transactions;
  }

  /**
   * 
   * @param id
   * @return uma lista com todas as transacoes de um dado parceiro.
   */
  protected List<Transaction> getAllPartnerTransactions(String id) {
    List<Transaction> transactions = new ArrayList<Transaction>();
    Partner p = getPartner(id);
    transactions = p.getAllTransactions();
    return transactions;
  }

  /**
   * 
   * @param partnerId
   * @param productId
   * @param price
   * @param quantity
   * @return o id da proxima transacao a ser criada.
   */
  protected int registerAcquisitionTransaction(String partnerId, String productId, double price ,int quantity) {
    Partner p = getPartner(partnerId);
    Product product = getProduct(productId);

    Acquisition acquisition = new Acquisition(_nextTransactionId, getDate(), p,product, price,quantity);

    _transactions.put(_nextTransactionId, acquisition);
    _acquisitions.put(_nextTransactionId, acquisition);

    p.addTransaction(getTransaction(_nextTransactionId));
    p.addAcquisitions(acquisition);
    p.setOrdersValues(price * quantity);

    if(product.getQuantity() == 0){
      String method = "NEW";
      product.notifyObserver(product, method,price,p);
    }
    else{
      String method = "BARGAIN";
      if(price < product.getLowerPrice()){
        product.notifyObserver(product, method,price);
      }
    }

    Batch batch = registerBatch(product, p, price, quantity);
    product.addBatch(batch);
    
    product.addQuantity(quantity);
    subAvailableBalance(price*quantity);
    subContabisticBalance(price*quantity);
    return _nextTransactionId++;
  }

  /**
   * 
   * @param partner
   * @param dataLimit
   * @param product
   * @param quantity
   * @return o id da proxima transacao a ser criada.
   */
  protected int registerSaleTransaction(Partner partner, int dataLimit , Product product, int quantity) {

    Sale sale = new Sale(_nextTransactionId, product, partner, dataLimit, quantity, getDate());
   

    _transactions.put(_nextTransactionId, sale);
    _sales.put(_nextTransactionId, sale);


    partner.addTransaction(getTransaction(_nextTransactionId));
    partner.addSales(sale);
    partner.setSalesValue(sale.getBaseValue());
    
    
    Batch b = product.getBatchwithlowerprice();
    b.decreaseQuantity(quantity);
    if(b.getQuantity()== 0){
      product.removeBatch(b);

      for(Partner x : getAllPartners()){
        x.removeBatch(b);
      }
      _batches.remove(b);
    }
    
    product.addQuantity(-quantity);
    addContabisticBalance((double)product.getLowerPrice()*quantity);

    return _nextTransactionId++;
  }

  /**
   * 
   * @param product
   * @param partner
   * @param quantity
   * @return o id da proxima transacao a ser criada.
   */
  protected int registerBreakdownTransaction(Product product, Partner partner, int quantity) {

    BreakdownSale breakdownSale = new BreakdownSale(_nextTransactionId, product, partner, quantity, getDate());

    _transactions.put(_nextTransactionId, breakdownSale);
    _breakdownSales.put(_nextTransactionId, breakdownSale);
    breakdownSale.setQuatityAP(quantity);

    partner.addTransaction(getTransaction(_nextTransactionId));
    partner.addBreakdownSales(breakdownSale);

    Batch b = product.getBatchwithlowerprice();
    b.decreaseQuantity(quantity);
    if(b.getQuantity()== 0){
      product.removeBatch(b);
      partner.removeBatch(b);
      _batches.remove(b);
    }
    product.addQuantity(-quantity);

    double priceAgregado = product.getMaxPrice() * quantity;
    double priceComponents = 0.0;


    Recipe recipe = (((AggregateProduct) product).getRecipe());
    breakdownSale.setComponents(recipe.getAllPComponents());

    for( Component x : recipe.getAllPComponents()){
      double price = x.getProduct().getLowerPrice();
      x.getProduct().addQuantity(quantity * x.getQuantity());
      Batch batch = new Batch(x.getProduct(), partner, price, quantity *x.getQuantity()); 
      x.getProduct().addBatch(batch);
      _batches.add(batch);
      double priceComponent = price*quantity*x.getQuantity();
      breakdownSale.addComponentPrice(priceComponent);
      priceComponents += priceComponent;
    }
    
    breakdownSale.setPriceComponents(priceComponents);
    double pricefinal = priceAgregado - priceComponents;

    breakdownSale.setPrice(pricefinal);
    breakdownSale.setBaseValue(pricefinal);

    addContabisticBalance(breakdownSale.getAmountPaid());
    addAvailableBalance(breakdownSale.getAmountPaid());


    return _nextTransactionId++;
  }

  /**
   * 
   * @return o saldo global.
   */
  public Double getAvailableBalance() {
    return _availableBalance;
  }
  
  /**
   * 
   * @param value
   */
  public void addAvailableBalance(Double value) {
    _availableBalance += value;
  }

  /**
   * 
   * @param value
   */
  public void subAvailableBalance(Double value) {
    _availableBalance -= value;
  }

  /**
   * 
   * @return o saldo contabilistico.
   */
  public Double getContabilisticlBalance() {
    return _contabilisticBalance;
  }
  
  /**
   * 
   * @param value
   */
  public void addContabisticBalance(Double value) {
    _contabilisticBalance += value;
  }

  /**
   * 
   * @param value
   */
  public void subContabisticBalance(Double value) {
    _contabilisticBalance -= value;
  }

  /**
   * 
   * @param transactionId
   */
  public void pay(int transactionId) {
    if (!_sales.containsKey(transactionId)) {
      return;
    }
    Sale sale = _sales.get(transactionId);
    if (sale.isPaid()) {
      return;
    }
    else {
      Double cost = sale.getPartner().pay(getDate(), transactionId);
      addAvailableBalance(cost);
    }
  }
}