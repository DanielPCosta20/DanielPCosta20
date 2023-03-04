package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Collection;
import java.util.List;

import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.*;


/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _form.addStringField("partnerId",Message.requestPartnerKey());
    _form.addIntegerField("datalimit", Message.requestPaymentDeadline());
    _form.addStringField("productId", Message.requestProductKey());
    _form.addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    String partnerId = _form.stringField("partnerId");
    String productId = _form.stringField("productId");
    Integer dataLimit = _form.integerField("datalimit");
    Integer quantity = _form.integerField("quantity");

    Partner p = _receiver.getPartner(partnerId);
    if (p == null)
      throw new UnknownPartnerKeyException(partnerId);

    Product pro = _receiver.getProduct(productId);
    
    if (pro == null)
      throw new UnknownProductKeyException(productId);

    if(!pro.getSimples()){
      int neededAmount = quantity - pro.getQuantity();
      Recipe recipe = ((AggregateProduct) pro).getRecipe();

      Collection<Component> list = recipe.getAllPComponents();
      List<Component> _list = (List<Component>) list;

      for (Component x: _list){
        if (x.getProduct().getQuantity() < neededAmount * x.getQuantity()){
          throw new UnavailableProductException(x.getProduct().getProductId(), neededAmount*x.getQuantity(), x.getProduct().getQuantity());
        }
      }
    }


    if (quantity > pro.getStock())
      throw new UnavailableProductException(productId, quantity, pro.getStock());


    _receiver.registerSaleTransaction(pro, p, dataLimit, quantity);
  }
}