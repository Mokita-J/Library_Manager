package m19.app.users;

import m19.LibraryManager;
import m19.User;
import m19.app.exceptions.UserRegistrationFailedException;
import m19.exceptions.RegistrationFailedException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;


/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {

  private Input<String> _name;
  private Input<String> _email;

  /**
   * @param receiver
   */
  public DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    _name = _form.addStringInput(Message.requestUserName());
    _email = _form.addStringInput(Message.requestUserEMail());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {
      _form.parse();
      _receiver.addUser(_name.value(), _email.value());
      _display.addLine(Message.userRegistrationSuccessful(_receiver.getUserID()-1));
      _display.display();

    } catch (RegistrationFailedException e){
      throw new UserRegistrationFailedException(_name.value(), _email.value());
    }
  }

}
