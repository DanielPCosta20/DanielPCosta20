package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ggc.core.Partner;
import ggc.core.WarehouseManager;

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {

  DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
  }

  @Override
  public void execute() throws CommandException {
    Collection<Partner> list = _receiver.getAllPartners();
    List<Partner> _list = (List<Partner>) list;

    Comparator<Partner> comparator = new Comparator<Partner>() {
      public int compare(Partner p1, Partner p2) {
        return (p1.getPartnerId()).compareToIgnoreCase(p2.getPartnerId());
      }
    };
    
    Collections.sort(_list, comparator);
    
    for(Partner x : _list){
      _display.addLine(x.toString());
    }
    _display.display();
  }

}