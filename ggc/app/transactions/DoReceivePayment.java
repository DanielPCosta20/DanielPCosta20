package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.Transaction;
import ggc.core.WarehouseManager;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    _form.addIntegerField("transactionId", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    Integer transactionId = _form.integerField("transactionId");
    Transaction t = _receiver.getTransaction(transactionId);
    if (t == null)
      throw new UnknownTransactionKeyException(transactionId);
    
    _receiver.pay(transactionId);
  }

}
