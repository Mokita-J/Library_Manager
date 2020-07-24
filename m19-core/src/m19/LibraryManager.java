package m19;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.List;

import java.lang.ClassNotFoundException;

import m19.exceptions.MissingFileAssociationException;
import m19.exceptions.FailedToOpenFileException;
import m19.exceptions.ImportFileException;
import m19.exceptions.BadEntrySpecificationException;
import m19.exceptions.NoSuchUserIdException;
import m19.exceptions.NoSuchWorkIdException;
import m19.exceptions.RegistrationFailedException;
import m19.exceptions.RuleException;
import m19.exceptions.WorkNotBorrowedException;


/**
 * The façade class.
 */
public class LibraryManager {

  private Library _library;
  private String _filename = "";

  /**
   * Library manager's constructor.
   */
  public LibraryManager(){
    _filename = "";
    _library = new Library(_filename);
  }

  public LibraryManager(String filename){
    _filename = filename;
    _library = new Library(_filename);

  }

  /**
   * @throws MissingFileAssociationException
   * @throws IOException
   */
  public void save() throws MissingFileAssociationException, IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
    oos.writeObject(_library);
    oos.close();
  }

  /**
   * @param filename name of the file to load.
   * @throws MissingFileAssociationException
   * @throws IOException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
    if (filename.isEmpty()){
      throw new MissingFileAssociationException();
    }
    _filename = filename;
    save();
  }

  /**
   * @param filename name of the file to load.
   * @throws FailedToOpenFileException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void load(String filename) throws FailedToOpenFileException, IOException, ClassNotFoundException {
    try{
      ObjectInputStream dis = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      _library = (Library) dis.readObject();
      dis.close(); 
      _filename = filename;
      _library.setFilename(filename);
    } catch (IOException| ClassNotFoundException e){
      throw new FailedToOpenFileException(filename);
    }
  }

  /**
   * @param datafile data in the file that will be imported.
   * @throws ImportFileException
   */
  public void importFile(String datafile) throws ImportFileException{
      try{
        _library.importFile(datafile);
      }
      catch(IOException|BadEntrySpecificationException e){
        throw new ImportFileException(e);
      }
  }


  /**
   * @return Library's date.
   */
  public void increaseDay(int n){
    _library.increaseDay(n);;
  }

  /**
   * @return Library's day.
   */
  public int getDay(){
    return _library.getDay();
  }

  /**
   * @return Biggest user id in the library.
   */
  public int getUserID(){
    return _library.getUserID();
  }

  /**
   * @return Biggest work id in the library.
   */
  public int getWorkID(){
    return _library.getWorkID();
  }

  /**
   * Adds user to the library
   * @param name User's name.
   * @param email User's email.
   */
  public void addUser(String name, String email) throws RegistrationFailedException{
    _library.addUser(name, email);
  }

  /**
   * @param key User's ID.
   * @return User based on it's ID.
   */
  public String getUser(int key) throws NoSuchUserIdException{
    return _library.getUser(key);
  }

  /**
   * @param key Work's ID.
   * @return Work based on it's ID.
   */
  public String getWork(int key) throws NoSuchWorkIdException{
    return _library.getWork(key);
  }

  /**
   * @return list of users.
   */
  public List<User> getUsers(){
    return _library.getUsers();
  }

  /**
   * @return list of works.
   */
  public List<Work> getWorks(){
    return _library.getWorks();
  }

  /**
   * User borrows a work.
   * @param userKey
   * @param workKey
   * @return Return date.
   * @throws NoSuchUserIdException
   * @throws NoSuchWorkIdException
   * @throws RuleException
   */
  public int borrowWork(int userKey, int workKey) throws NoSuchUserIdException, NoSuchWorkIdException, RuleException{
    return _library.borrowWork(userKey, workKey);
  }

  /**
   * Calculates return date of a work.
   * @param userKey
   * @param workKey
   * @return Return date.
   * @throws NoSuchUserIdException
   * @throws NoSuchWorkIdException
   * @throws WorkNotBorrowedException
   */
  public int returnWork(int userKey, int workKey) throws NoSuchUserIdException, NoSuchWorkIdException, WorkNotBorrowedException{
    return _library.returnWork(userKey, workKey);

  }

  /**
   * @return True if library has no users, false otherwise.
   */
  public boolean noUsers(){
    return _library.noUsers();
  }

  /**
   * @return True if library has no works, false otherwise.
   */
  public boolean noWorks(){
    return _library.noWorks();
  }

  /**
   * @return Library's file name.
   */
  public String getFilename(){
    return _filename;
  }

  /**
   * @param user
   * @return User's information.
   */
  public String printUser(User user){
    return _library.printUser(user);
  }

  /**
   * @param work
   * @return Work's information.
   */
  public String printWork(Work work){
    return _library.printWork(work);
  }

  /**
   * @param term
   * @return list of works which contains the search term.
   */
  public List<Work> performSearch(String term){
    return _library.performSearch(term);
  }

  /**
   * Adds notifications to a user.
   * @param userID
   * @param workID Work the user is "observing".
   */
  public void returnNotificationOn(int userID, int workID){
    _library.returnNotificationOn(userID, workID);
  }

  /**
   * @param userID
   * @return User's notifications.
   * @throws NoSuchUserIdException
   */
  public String showNotifications(int userID)  throws NoSuchUserIdException{
   return _library.showNotifications(userID);
  }

  /**
   * User pays fine.
   * @param userID
   * @return
   * @throws NoSuchUserIdException
   */
  public boolean payFine(int userID)throws NoSuchUserIdException{
    return _library.payFine(userID);
  }

  /**
   * Updates user's fine.
   */
  public void updateFine(int userKey, int fine){
    _library.updateFine(userKey, fine);
  }

  /**
   * Update's user's state.
   * @param userID
   */
  public void updateUser(int userID){
    _library.updateUser(userID);
  }
}
