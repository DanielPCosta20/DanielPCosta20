package ggc.core;

import java.util.List;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;


/** Façade for access. */
public class WarehouseManager implements Serializable{
  /** Name of file storing current warehouse. */
  private String _filename = "";
  /** The wharehouse itself. */
  private Warehouse _warehouse;

  public WarehouseManager() {
    _warehouse = new Warehouse();
  }

  /**
   * 
   * @return data atual.
   */
  public int getCurrentDate() {
    return _warehouse.getDate();
  }

  /**
   * 
   * @param days
   */
  public void advanceDays(int days){
    _warehouse.advanceDays(days);
  }

  /**
   * 
   * @param id
   * @param name
   * @param address
   * @return funcao para registar um paceiro.
   */
  public boolean registerPartner(String id, String name, String address) {
    return _warehouse.registerPartner(id, name, address);
  }

  /**
   * 
   * @param id
   * @return funcao para obter um parceiro.
   */
  public Partner getPartner(String id) {
	  return _warehouse.getPartner(id);
  }

  /**
   * 
   * @return funcao para obter todos os parceiros.
   */
  public List<Partner> getAllPartners(){
	  return _warehouse.getAllPartners();
  }

  /**
   * 
   * @param id
   * @param quantity
   * @param simder
   * @param price
   * @return funcao para registar um produto simples.
   */
  public boolean registerSimpleProduct(String id,int quantity, boolean simder,double price) {
    return _warehouse.registerSimpleProduct(id, quantity, simder,price);
  }

  /**
   * 
   * @param id
   * @param quantity
   * @param recipe
   * @param price
   * @return funcao para registar um produto agregado.
   */
  public boolean registerAggregateProduct(String id, int quantity, Recipe recipe,double price){
    return _warehouse.registerAggregateProduct(id, quantity,recipe,price);
  }

  /**
   * 
   * @param id
   * @return funcao para obter um produto.
   */
  public Product getProduct(String id) {
    return _warehouse.getProduct(id);
  }

  /**
   * 
   * @return funcao para obter todos os produtos.
   */
  public List<Product> getAllProducts(){
    return _warehouse.getAllProducts();
  }
  
  /**
   * 
   * @param product
   * @param partner
   * @param price
   * @param quantity
   * @return funcao para registar um lote.
   */
  public Batch registerBatch(Product product, Partner partner, double price, int quantity) {
    return _warehouse.registerBatch(product, partner, price, quantity);
  }

  /**
   * 
   * @param id
   * @return funcao para obter um lote.
   */
  public Batch getBatch(String id) {
    return _warehouse.getBatch(id);
  }

  /**
   * 
   * @return funcao para obter todos os lotes.
   */
  public List<Batch> getAllBatches(){
    return _warehouse.getAllBatches();
  }

  /**
   * 
   * @param id
   * @return funcao para obter uma transacao.
   */
  public Transaction getTransaction(int id) {
    return _warehouse.getTransaction(id);
  }

  /**
   * 
   * @return funcao para obter todas as transacoes.
   */
  public List<Transaction> getAllTransactions(){
    return _warehouse.getAllTransactions();
  }

  /**
   * 
   * @param id
   * @return funcao para obter todas as transacoes de um parceiro.
   */
  public List<Transaction> getAllPartnerTransactions(String id){
    return _warehouse.getAllPartnerTransactions(id);
  }

  /**
   * 
   * @param partnerId
   * @param productId
   * @return funcao para ativar ou desativar as notificacoes de um produto.
   */
  public boolean toggleProductNotifications(String partnerId, String productId) {
    return _warehouse.toggleProductNotifications(partnerId, productId);
  }
  
  /**
   * 
   * @param partnerId
   * @param productId
   * @param price
   * @param quantity
   * @return funcao para registar uma aquisicao.
   */
  public int registerAcquisitionTransaction(String partnerId,String productId, double price, int quantity) {
    return _warehouse.registerAcquisitionTransaction(partnerId,productId,price,quantity);
  }

  /**
   * 
   * @param product
   * @param partner
   * @param dataLimit
   * @param quantity
   * @return funcao para registar uma compra.
   */
  public int registerSaleTransaction(Product product, Partner partner, int dataLimit, int quantity) {
    return _warehouse.registerSaleTransaction(partner, dataLimit, product, quantity);
  }

  public void registerBreakdownTransaction(Product product, Partner partner, int quantity) {
     _warehouse.registerBreakdownTransaction(product, partner, quantity);
  }

  /**
   * 
   * @param transactionId
   */
  public void pay(int transactionId) {
    _warehouse.pay(transactionId);
  }

  /**
   * 
   * @return funcao para obter o saldo global.
   */
  public Double getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  /**
   * 
   * @return funcao para obter o saldo contabilistico.
   */
  public Double getContabilisticBalance(){
    return _warehouse.getContabilisticlBalance();
  }
  

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename == null)
      throw new MissingFileAssociationException();
    try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(_filename))) {
      obOut.writeObject(_warehouse);
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }


  /**
   * @@param filename
   * @@throws UnavailableFileException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException, FileNotFoundException, IOException {
    try (ObjectInputStream obIn = new ObjectInputStream(new FileInputStream(filename))) {
      _warehouse = (Warehouse) obIn.readObject();
      _filename = filename;
    }
  }

  /**
   * 
   * @return se o ficheiro tem nome.
   */
  public boolean hasNoFileName(){
    return _filename.equals("");
  }
  
  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile, e);
    }
  }
}