package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.*;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  Form _form = new Form();

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
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
    _display.addLine(p.toString());

    for (Notification notification: p.getNotifications()) {
      if ((!notification.isNotified()) && notification.isInterested()){
        _display.addLine(notification.toString());
        notification.toggleNotified();
      }
    }
    _display.display();
  }
}