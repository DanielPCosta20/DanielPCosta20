package ggc.app.lookups;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggc.core.*;


/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    _form.addRealField("PriceLimit", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();

    Double PriceLimit = _form.realField("PriceLimit");

    Collection<Batch> list = _receiver.getAllBatches();
    List<Batch> _list = (List<Batch>) list;
    List<Batch> _lista = new ArrayList<>();

    for (Batch x:_list){
      if (x.getPrice() < PriceLimit && x.getQuantity() != 0 ){
        _lista.add(x);
      }
    }

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
    
    Collections.sort(_lista, comparator);

    for(Batch x : _lista){
      _display.addLine(x.toString());
    }
    _display.display();

  }

}