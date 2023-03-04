package ggc.app.lookups;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.*;


/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    _form.addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();

    String partnerId = _form.stringField("partnerId");
    Partner partner = _receiver.getPartner(partnerId);
    
    if (partner == null) {
      throw new UnknownPartnerKeyException(partnerId);
    }

    for (Sale s : partner.getAllSales()) {
      if (s.isPaid()) {
        _display.addLine(s.toString());
      }
    }
    _display.display();
  }

}
