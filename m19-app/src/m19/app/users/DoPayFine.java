package m19.app.users;

import m19.LibraryManager;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import m19.app.exceptions.NoSuchUserException;
import m19.exceptions.NoSuchUserIdException;

import m19.app.exceptions.UserIsActiveException;

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {

  private Input<Integer> _userID;

  /**
   * @param receiver
   */
  public DoPayFine(LibraryManager receiver) {
    super(Label.PAY_FINE, receiver);
    _userID = _form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      if(_receiver.payFine(_userID.value())){
        _receiver.updateUser(_userID.value());
      }
      else
      throw new UserIsActiveException(_userID.value());

    }
    catch (NoSuchUserIdException e){
      throw new NoSuchUserException(_userID.value());
    }

  }
}
