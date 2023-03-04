package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.*;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _form.addIntegerField("id", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();

    int id = _form.integerField("id");
    Transaction t = _receiver.getTransaction(id);

    if (t == null)
      throw new UnknownTransactionKeyException(id);

    _display.addLine(t.toString());
    _display.display();
  }

}
