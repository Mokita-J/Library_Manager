package m19.app.works;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchWorkException;
import m19.exceptions.NoSuchWorkIdException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;



/**
 * 4.3.1. Display work.
 */
public class DoDisplayWork extends Command<LibraryManager> {

  private Input<Integer> _id;

  /**
   * @param receiver
   */
  public DoDisplayWork(LibraryManager receiver) {
    super(Label.SHOW_WORK, receiver);
    _id = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _display.addLine(_receiver.getWork(_id.value()));
      _display.display();
    } catch (NoSuchWorkIdException e){
      throw new NoSuchWorkException(_id.value());
    }    
  }
  
}
