package m19.app.requests;

import m19.LibraryManager;

import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import m19.app.exceptions.NoSuchWorkException;
import m19.exceptions.NoSuchWorkIdException;

import m19.app.exceptions.NoSuchUserException;
import m19.exceptions.NoSuchUserIdException;

import m19.app.exceptions.RuleFailedException;
import m19.exceptions.RuleException;

/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

  private Input<Integer> _userID;
  private Input<Integer> _workID;
  private Input<String> _answer;

  /**
   * @param receiver
   */
  public DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    
    try {
      _form.clear();
      _userID = _form.addIntegerInput(Message.requestUserId());
      _workID = _form.addIntegerInput(Message.requestWorkId());
      _form.parse();
      int dueDate = _receiver.borrowWork(_userID.value(), _workID.value());

      if (dueDate != 0){
        _display.addLine(Message.workReturnDay(_workID.value(), dueDate));
        _display.display();
      } else {
        _form.clear();
        _display.display();
        _answer = _form.addStringInput(Message.requestReturnNotificationPreference());
        _form.parse();
        
        if(_answer.value().equals("s")){
          _receiver.returnNotificationOn(_userID.value(), _workID.value());
        }
        else if(_answer.value().equals("n")){
          return;
        }
      }

    } catch (NoSuchUserIdException e){
      throw new NoSuchUserException(_userID.value());

    } catch (NoSuchWorkIdException e){ 
      throw new NoSuchWorkException(_workID.value());

    } catch (RuleException e){
      throw new RuleFailedException(_userID.value(), _workID.value(), e.getRuleIndex());
    }

  }

}
