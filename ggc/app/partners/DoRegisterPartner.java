package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  Form _form = new Form();

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    _form.addStringField("id",Message.requestPartnerKey());
    _form.addStringField("name",Message.requestPartnerName());
    _form.addStringField("adress",Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String id = _form.stringField("id");
    String name = _form.stringField("name");
    String adress = _form.stringField("adress");

    if (!( _receiver.registerPartner(id, name, adress)))
      throw new DuplicatePartnerKeyException(id);
  }

}