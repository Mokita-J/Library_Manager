package m19;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
//import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import java.text.Collator;
import m19.exceptions.RuleException;
import m19.exceptions.WorkNotBorrowedException;
import m19.exceptions.BadEntrySpecificationException;
import m19.exceptions.NoSuchUserIdException;
import m19.exceptions.NoSuchWorkIdException;
import m19.exceptions.RegistrationFailedException;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201901101348L;


  private String _filename = "";
  private int _day = 0;
  private int _userID = 0; /**Keeps count of user's ID */
  private int _workID = 0; /**Keeps count of work's ID */
  /** Treemap to save information about users. Key = user ID. */
  private TreeMap<Integer, User> users = new TreeMap<>();

  /** Treemap to save information about works. Key = book ISBN or DVD IGAC. */
  private TreeMap<Integer, Work> works  = new TreeMap<>();


  /**
   * Library's constructor.
   * @param filename Name of the file.
   */
  public Library(String filename){
    _filename = filename;
  }

  /**
   * Read the text input file at the beginning of the program and populates the
   * instances of the various possible types (books, DVDs, users).
   * 
   * @param filename name of the file to load.
   * @throws BadEntrySpecificationException
   * @throws IOException
   */
  void importFile(String filename) throws BadEntrySpecificationException, IOException {
    int lineno = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
      String s;
      while ((s = reader.readLine()) != null){
        String line = new String(s.getBytes(), "UTF-8");
        lineno++;
        String[] split = line.split(":");
        if(split[0].equals("BOOK"))
          parseBook(split, line);
        
        else if(split[0].equals("DVD"))
          parseDVD(split, line);
        
        else if(split[0].equals("USER"))
          parseUser(split, line);
      }
      reader.close();
    }
  }
  

  /**
   * Analizes an array of strings and creates a Book.
   * @param arg Array of strings
   * @param line
   * @throws BadEntrySpecificationException
   */
  private void parseBook(String[] arg, String line) throws BadEntrySpecificationException{
    if (arg.length!=7){
      throw new BadEntrySpecificationException("Wrong number of components in line:" + line);
    }
    Book book = new Book(_workID, arg[1], arg[2], Integer.parseInt(arg[3]),
                Genre.valueOf(arg[4]), Integer.parseInt(arg[5]), Integer.parseInt(arg[6]), "Livro" );
    works.put(_workID, book);
    _workID++;
  }

  /**
   * Analizes an array of strings and creates a DVD.
   * @param arg Array of strings
   * @param line
   * @throws BadEntrySpecificationException
   */
  private void parseDVD(String[] arg, String line) throws BadEntrySpecificationException{
    if (arg.length!=7){
      throw new BadEntrySpecificationException("Wrong number of components in line:" + line);
    }
    DVD dvd = new DVD(_workID, arg[1], arg[2], Integer.parseInt(arg[3]),
              Genre.valueOf(arg[4]), Integer.parseInt(arg[5]), Integer.parseInt(arg[6]), "DVD");
    works.put(_workID, dvd);
    _workID++;
  }

  /**
   * Analizes an array of strings and creates a User.
   * @param arg Array of strings
   * @param line
   * @throws BadEntrySpecificationException
   */
  private void parseUser(String[] arg, String line) throws BadEntrySpecificationException{
    if(arg.length!=3){
      throw new BadEntrySpecificationException("Wrong number of components in line:" + line);
    }
    try{
      addUser(arg[1], arg[2]);
    }
    catch(RegistrationFailedException e){
      throw new BadEntrySpecificationException("Wrong number of components in line:" + line);
    }
  }

  /**
   * @return Library's day.
   */
  public int getDay(){
    return _day;
  }

  /**
   * Updates library's day and user's state.
   * @param n Number of day that have passed.
   */
  public void increaseDay(int n){
    if (n > 0){
      _day = _day + n;
    }
    
   for(Map.Entry<Integer, User> entry : users.entrySet()){   
     User user = entry.getValue();    
     user.updateState(_day);                         
   }
  }

  /**
   * @return Get current user ID.
   */
  public int getUserID(){
    return _userID;
  }

  /**
   * @return Get current work ID.
   */
  public int getWorkID(){
    return _workID;
  }

  /**
   * Adds User to the Library
   * @param name User's name.
   * @param email User's email.
   */
  public void addUser(String name, String email) throws RegistrationFailedException{
    if(name.isEmpty() || email.isEmpty()){
      throw new RegistrationFailedException(name, email);
    }
    User user = new User(_userID, name, email);
    users.put(_userID, user);
    _userID ++;
  }

  /**
   * Search user by key.
   * @param key User ID.
   * @return User.
   */
  public String getUser(int key) throws NoSuchUserIdException {
    if (key > _userID -1 || key < 0){
      throw new NoSuchUserIdException(key);
    }
    return users.get(key).toString();
  }

  /**
   * Search work by key.
   * @param key Work's ISBN(book) or IGAC(DVD).
   * @return Work.
   */
  public String getWork(int key) throws NoSuchWorkIdException{
    if (key > _workID -1 || key < 0){
      throw new NoSuchWorkIdException(key);
    }
    return works.get(key).toString();
  }

  /**
   * @return sorted list of users
   */
  public List<User> getUsers(){
    List <User> _users = new LinkedList<User>();
    _users.addAll(users.values());
    Collator collator = Collator.getInstance(new Locale("pt"));
    if(!_users.isEmpty()){
      Collections.sort(_users, new Comparator<User>(){
        @Override
        public int compare(User user1, User user2){
          return collator.compare(user1.getName(), user2.getName()); 
        }
      });
    }
    return _users;
  }

  /**
   * @return list of works
   */
  public List<Work> getWorks(){
    List<Work> _works = new LinkedList<Work>();
    _works.addAll(works.values());
    return _works;
  }

    /**
   * @param term
   * @return list of works which contains the search term.
   */
  public List<Work> performSearch(String term){
    List<Work> _works = new LinkedList<Work>();
    for(Work work: getWorks()){
      String test= work.getName().toLowerCase() + " " + work.getTitle().toLowerCase();
      if (test.contains(term.toLowerCase()))
        _works.add(work);
    }
    return _works;
  }
  
  /**
   * User borrows a work
   * @param userKey
   * @param workKey
   * @return Number of days until the supposed return of the work
   * @throws NoSuchUserIdException
   * @throws NoSuchWorkIdException
   * @throws RuleException
   */
  public int borrowWork(int userKey, int workKey) throws NoSuchUserIdException, NoSuchWorkIdException, RuleException{
    if (userKey > _userID -1 || userKey < 0){
      throw new NoSuchUserIdException(userKey);
    }
    if (workKey > _workID -1 || workKey < 0){
      throw new NoSuchWorkIdException(workKey);
    }
    User user = users.get(userKey);
    Work work = works.get(workKey);
    RuleBook rules = new RuleBook();

    int ret = rules.checkAll(user, work);
    if(ret != 0 && ret != 3)
      throw new RuleException(userKey, workKey, ret);
    if(ret == 3)
      return 0;
    int availableDays = returnDay(userKey, workKey);
    user.borrowWork(work, availableDays);
    work.setCopiesAvalailable(-1);

    return availableDays;
  }

  /**
   * Calculates the return date of a work a user has borrowed.
   * @param userKey
   * @param workKey
   * @return 
   */
  public int returnDay(int userKey, int workKey){
    Work work = works.get(workKey);
    User user = users.get(userKey);
    
    return user.getBehaviour().returnDay(work) + _day;
  }

  /**
   * Returns a work to the library.
   * @param userKey
   * @param workKey
   * @return fine(if user returned on time fine will be zero).
   * @throws NoSuchUserIdException
   * @throws NoSuchWorkIdException
   * @throws WorkNotBorrowedException
   */
  public int returnWork(int userKey, int workKey) throws NoSuchUserIdException, NoSuchWorkIdException, WorkNotBorrowedException{
    if (userKey > _userID -1 || userKey < 0){
      throw new NoSuchUserIdException(userKey);
    }
    if (workKey > _workID -1 || workKey < 0){
      throw new NoSuchWorkIdException(workKey);
    }

    User user = users.get(userKey);
    Work work = works.get(workKey);

    if(user.requestedWork(work)){
      int fine = user.getWorkDebt(work, _day);
      user.returnWork(work,_day);
      work.setCopiesAvalailable(1);
      work.notifyUsersReturn();
      return fine;

    } else {
      throw new WorkNotBorrowedException(workKey, userKey);
    }
  }

  /**
   * @return true if there are no users
   */
  public boolean noUsers(){
    return users.isEmpty();
  }

  /**
   * @return true if there are no books
   */
  public boolean noWorks(){
    return works.isEmpty();
  }

  /**
   * @return User's information.
   */
  public String printUser(User user){
    return user.toString();
  }

  /**
   * @return Work's information.
   */
  public String printWork(Work work){
    return work.toString();
  }

  /**
   * Changes library's filename.
   * @param filename
   */
  public void setFilename(String filename){
    _filename = filename;
  }

  /**
   * Adds notifications to a user.
   * @param userID
   * @param workID Work the user will be "observing".
   */
  public void returnNotificationOn(int userID, int workID){
    User user = users.get(userID);
    Work work = works.get(workID);
    work.addObserverReturn(user);
  }

  /**
   * @param userID
   * @return User's notifications.
   * @throws NoSuchUserIdException
   */
  public String showNotifications(int userID) throws NoSuchUserIdException{
    if (userID > _userID -1 || userID < 0){
      throw new NoSuchUserIdException(userID);
    }
    User user = users.get(userID);
    String notifications = user.showNotifications();
    user.cleanNotifications();
    return notifications;
  }

  /**
   * User pays fine
   */
  public boolean payFine(int userID) throws NoSuchUserIdException{
    if (userID > _userID -1 || userID < 0){
      throw new NoSuchUserIdException(userID);
    }
    User user = users.get(userID);
    if (user.getState().equals(State.SUSPENSO))
      user.setDebt(0);
    return user.getState().equals(State.SUSPENSO);
  }

  /**
   * Update's fine.
   * @param userKey
   * @param fine
   */
  public void updateFine(int userKey, int fine){
    users.get(userKey).setDebt(fine);
  }

  /**
   * Updates user's state.
   * @param userID
   */
  public void updateUser(int userID){
    User user = users.get(userID);
    user.updateState(_day);
  }
}
