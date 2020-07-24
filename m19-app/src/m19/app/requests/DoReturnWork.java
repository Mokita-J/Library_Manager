package m19.app.requests;

import m19.LibraryManager;

import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import m19.app.exceptions.NoSuchWorkException;
import m19.exceptions.NoSuchWorkIdException;

import m19.app.exceptions.NoSuchUserException;
import m19.exceptions.NoSuchUserIdException;

import m19.app.exceptions.WorkNotBorrowedByUserException;
import m19.exceptions.WorkNotBorrowedException;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

  private Input<Integer> _userID;
  private Input<Integer> _workID;
  private Input<String> _answer;


  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.clear();
      _userID = _form.addIntegerInput(Message.requestUserId());
      _workID = _form.addIntegerInput(Message.requestWorkId());
      _form.parse();
      int fine = _receiver.returnWork(_userID.value(), _workID.value());
      if (fine != 0){
        _display.addLine(Message.showFine(_userID.value(), fine));
        _display.display();
        _form.clear();
        _answer = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
        if(_answer.value().equals("n")){
          _receiver.updateFine(_userID.value(), fine);
        }
        else _receiver.updateUser(_userID.value());
      }
      else _display.display();

    } catch (NoSuchUserIdException e){
      throw new NoSuchUserException(_userID.value());

    } catch (NoSuchWorkIdException e){ 
      throw new NoSuchWorkException(_workID.value());

    } catch (WorkNotBorrowedException e){
      throw new WorkNotBorrowedByUserException(_workID.value(), _userID.value());
    }
  }

}
