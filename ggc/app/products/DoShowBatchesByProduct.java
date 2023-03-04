package ggc.app.products;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.*;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {
  Form _form = new Form();

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    _form.addStringField("ProductId", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();

    String ProductID = _form.stringField("ProductId");
  
    Product p = _receiver.getProduct(ProductID);

    if (p == null){
      throw new UnknownProductKeyException(ProductID);
    }

    Collection<Batch> list = p.getBatchs();
    List<Batch> _list = (List<Batch>) list;


    Comparator<Batch> comparator = new Comparator<Batch>() {
      public int compare(Batch b1, Batch b2) {
        if (b1.getProductID().equals(b2.getProductID())){
          if (b1.getPartnerID().equals(b2.getPartnerID())){
            if (b1.getPrice()> b2.getPrice()){
              return 1;
            }
            if (b1.getPrice() == b2.getPrice()){
              if (b1.getQuantity()> b2.getQuantity()){
                return 1;
              }return -1;
            }
            return -1;
          }
          return (b1.getPartnerID()).compareToIgnoreCase(b2.getPartnerID()); 
        }
        return (b1.getProductID()).compareToIgnoreCase(b2.getProductID());
      } 
    };
    
    Collections.sort(_list, comparator);

    
    for(Batch x : list){
      _display.addLine(x.toString());
    }
    _display.display();
    }
}


