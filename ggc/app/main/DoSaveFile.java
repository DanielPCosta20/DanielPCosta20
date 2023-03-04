package ggc.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;

import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {

    try {
      if(!(_receiver.hasNoFileName())){
        _receiver.save();
      }
      else{
        Form _form = new Form();
        _form.addStringField("filename", Message.newSaveAs());
        _form.parse();
        String _filename = _form.stringField("filename");
        _receiver.saveAs(_filename);
      }
    }catch(IOException e){
      System.err.println(e);
    }
    catch(MissingFileAssociationException e){
      System.err.println(e);
    }
  }
}
