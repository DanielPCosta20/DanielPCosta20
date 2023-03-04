package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.*;


/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  Form _form = new Form();

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    _form.addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String partnerId = _form.stringField("partnerId");
    Partner p = _receiver.getPartner(partnerId);

    if (p == null){
      throw new UnknownPartnerKeyException(partnerId);
    }

    Collection<Sale> list1 = p.getAllSales();
    List<Sale> _list1 = (List<Sale>) list1;

    Collection<BreakdownSale> list2 = p.getAllBreakdownSales();
    List<BreakdownSale> _list2 = (List<BreakdownSale>) list2;

    List<Transaction> _list = new ArrayList<>();

    _list.addAll(_list1);
    _list.addAll(_list2);

    Comparator<Transaction> comparator = new Comparator<Transaction>() {
      public int compare(Transaction t1, Transaction t2){
        if (t1.getId()>t2.getId()){
          return 1;
        }
        else{
          return -1;
        }
      }
    };

    Collections.sort(_list, comparator);
  
    for(Transaction x : _list){
      _display.addLine(x.toString());
    }
    _display.display();

  }


}