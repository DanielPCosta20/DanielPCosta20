package ggc.app.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.exception.UnavailableFileException;


/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  private Form _form = new Form();

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    _form.addStringField("filename", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException{
    _form.parse();
    String filename = _form.stringField("filename");
    try {
      _receiver.load(filename);
    }
    catch (ClassNotFoundException | UnavailableFileException | IOException e) {  
      throw new FileOpenFailedException(filename);
    }
  }
}
