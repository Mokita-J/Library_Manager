package m19.app.works;

import m19.LibraryManager;
import m19.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * 4.3.3. Perform search according to miscellaneous criteria.
 */
public class DoPerformSearch extends Command<LibraryManager> {

  private Input<String> _term;

  /**
   * @param receiver
   */
  public DoPerformSearch(LibraryManager receiver) {
    super(Label.PERFORM_SEARCH, receiver);
    _term = _form.addStringInput(Message.requestSearchTerm());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    for (Work work : _receiver.performSearch(_term.toString())){
        _display.addLine(work.toString());
    }
    _display.display();
  }
  
}
