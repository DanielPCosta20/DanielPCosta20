package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.WarehouseManager;


/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  Form _form = new Form();

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    _form.addStringField("partnerId",Message.requestPartnerKey());
    _form.addStringField("productId",Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String partnerId = _form.stringField("partnerId");
    String productId = _form.stringField("productId");
    
    Partner partner = _receiver.getPartner(partnerId);
    Product product = _receiver.getProduct(productId);

    if(partner == null){
      throw new UnknownPartnerKeyException(partnerId);
    }

    if (product == null){
      throw new UnknownProductKeyException(productId);
    }

    _receiver.toggleProductNotifications(partnerId, productId);


  }

}
