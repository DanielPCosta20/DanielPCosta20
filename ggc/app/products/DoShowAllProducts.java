package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggc.core.Product;
import ggc.core.WarehouseManager;


/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    Collection<Product> list = _receiver.getAllProducts();
    List<Product> _list = (List<Product>) list;

    Comparator<Product> comparator = new Comparator<Product>() {
      public int compare(Product p1, Product p2) {
        return (p1.getProductId()).compareToIgnoreCase(p2.getProductId());
      }
    };
    
    Collections.sort(_list, comparator);
    
    for(Product x : _list){
      _display.addLine(x.toString());
    }
    _display.display();
  }

}