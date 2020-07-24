package m19.exceptions;

public class RuleException extends Exception{

    /** Serial number for serialization. */
  static final long serialVersionUID = 200510291601L;

  /** User id. */
  int _idUser;

  /** Work id. */
  int _idWork;

  /** Index of failed rule. */
  int _ruleIndex = -1;

  /**
   * @param idUser
   * @param idWork
   * @param ruleIndex
   */
  public RuleException(int idUser, int idWork, int ruleIndex) {
    _idUser = idUser;
    _idWork = idWork;
    _ruleIndex = ruleIndex;
  }

    /**
   * @return index of failed rule
   */
  public int getRuleIndex() {
    return _ruleIndex;
  }

  /**
   * @return work id
   */
  public int getWork() {
    return _idWork;
  }

  /**
   * @return user id
   */
  public int getUser() {
    return _idUser;
  }

}