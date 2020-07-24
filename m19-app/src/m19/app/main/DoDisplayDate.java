package m19.app.main;

import m19.LibraryManager;
import pt.tecnico.po.ui.Command;


/**
 * 4.1.2. Display the current date.
 */
public class DoDisplayDate extends Command<LibraryManager> {

  protected LibraryManager _libraryManager;

  /**
   * @param receiver
   */
  public DoDisplayDate(LibraryManager receiver) {
    super(Label.DISPLAY_DATE, receiver);
    _libraryManager = receiver;
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _display.addLine(Message.currentDate(_receiver.getDay()));
    _display.display();
    
  }
  
}
