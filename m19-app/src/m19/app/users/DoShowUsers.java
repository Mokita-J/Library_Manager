package m19.app.users;

import m19.LibraryManager;
import m19.User;
import pt.tecnico.po.ui.Command;


/**
 * 4.2.4. Show all users.
 */
public class DoShowUsers extends Command<LibraryManager> {

  protected LibraryManager _libraryManager;

  /**
   * @param receiver
   */
  public DoShowUsers(LibraryManager receiver) {
    super(Label.SHOW_USERS, receiver);
    _libraryManager = receiver;
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    for(User user : _receiver.getUsers()){
      _display.addLine(_receiver.printUser(user));
    }
    _display.display();
  }
  
}
