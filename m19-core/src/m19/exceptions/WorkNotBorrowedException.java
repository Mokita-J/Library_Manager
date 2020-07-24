package m19.exceptions;

public class WorkNotBorrowedException extends Exception {
  
    /** Serial number for serialization. */
    static final long serialVersionUID = 200510291601L;
  
    /** Bad user id. */
    private int _idUser;
  
    /** Bad work id. */
    private int _idWork;
  
    /**
     * @param idWork
     * @param idUser
     */
    public WorkNotBorrowedException(int idWork, int idUser) {
      _idWork = idWork;
      _idUser = idUser;
    }

}