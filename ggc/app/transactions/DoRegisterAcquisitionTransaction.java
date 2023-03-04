package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


import java.util.HashSet;
import java.util.Set;

import ggc.app.exception.*;
import ggc.core.*;


/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerId",Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addIntegerField("price", Message.requestPrice());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");
    Integer price = integerField("price");
    Integer quantity = integerField("quantity");

    Partner p = _receiver.getPartner(partnerId);
    if (p == null){
      throw new UnknownPartnerKeyException(partnerId);
    }

    Product pro = _receiver.getProduct(productId);

    if (pro == null){

      _form.addStringField("recipe", Message.requestAddRecipe());
      _form.parse();
      String recipe = _form.stringField("recipe");
      if (recipe.equals("N") || recipe.equals("n")){
        _receiver.registerSimpleProduct(productId,0, true,price);
        pro = _receiver.getProduct(productId);
      }
      else{
        Form _form1 = new Form();
        _form1.addIntegerField("NdeComponentes", Message.requestNumberOfComponents());
        _form1.addRealField("alpha", Message.requestAlpha());
        _form1.parse();
        int _nDeComponentes = _form1.integerField("NdeComponentes");
        Double alpha = _form1.realField("alpha");
        
        int i = 0;
        Set<Component> Componentes = new HashSet<>();

        while (i < _nDeComponentes){
          Form _form2 = new Form();
          _form2.addStringField("productsIds", Message.requestProductKey());
          _form2.addIntegerField("quantities", Message.requestAmount());
          _form2.parse();

          int _quantity = _form2.integerField("quantities");
          String _product = _form2.stringField("productsIds");
          Product product = _receiver.getProduct(_product);
          if (product == null){
            throw new UnknownProductKeyException(_product);
          }
          Component comp = new Component(_quantity,product);
          Componentes.add(comp);

          i++;
        }
        Recipe _recipe = new Recipe(alpha, Componentes);
        _receiver.registerAggregateProduct(productId,quantity, _recipe,price);
        pro = _receiver.getProduct(productId);
        price = price * (int)Math.round(alpha);
      }
    }  
    _receiver.registerAcquisitionTransaction(partnerId,productId,price,quantity);
      
  }
}
