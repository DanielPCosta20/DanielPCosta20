package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.InvalidDateException;



/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {


  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("numberOfDays", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    Integer numberOfDays = integerField("numberOfDays");
    if (numberOfDays < 0) {
      throw new InvalidDateException(numberOfDays);
    }
    _receiver.advanceDays(numberOfDays);
  }

}
