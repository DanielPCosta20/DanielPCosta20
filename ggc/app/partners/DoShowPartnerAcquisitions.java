package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.*;
import ggc.core.WarehouseManager;


/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {
  Form _form = new Form();

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    _form.addStringField("id",Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();

    String id = _form.stringField("id");
    Partner p = _receiver.getPartner(id);

    if (p == null){
      throw new UnknownPartnerKeyException(id);
    }

    Collection<Acquisition> list = p.getAllAcquisitions();
    List<Acquisition> _list = (List<Acquisition>) list;

    for(Acquisition x : _list){
      _display.addLine(x.toString());
    }
    _display.display();
  }

}
