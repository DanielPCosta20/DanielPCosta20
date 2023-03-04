package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    Collection<Batch> list = _receiver.getAllBatches();
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
    
    for(Batch x : _list){
      _display.addLine(x.toString());
    }
    _display.display();
  }
}