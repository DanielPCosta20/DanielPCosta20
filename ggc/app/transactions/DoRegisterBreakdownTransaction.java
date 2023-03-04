package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.WarehouseManager;


/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {
  Form _form = new Form();

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    _form.addStringField("partnerId", Message.requestPartnerKey());
    _form.addStringField("productId", Message.requestProductKey());
    _form.addIntegerField("quantidade", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();

    String partnerId = _form.stringField("partnerId");
    int quantity = _form.integerField("quantidade");
    String productId = _form.stringField("productId");

    Product product = _receiver.getProduct(productId);
    Partner partner = _receiver.getPartner(partnerId);

    if (partner == null){
      throw new UnknownPartnerKeyException(partnerId);
    }

    if (product == null){
      throw new UnknownProductKeyException(productId);
    }

    if (product.getQuantity() < quantity){
      throw new UnavailableProductException(productId, quantity, product.getQuantity());
    }

    if(!(product.getSimples())){
      _receiver.registerBreakdownTransaction(product, partner, quantity);
    }
  }
}