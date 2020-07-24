package m19.exceptions;

public class RegistrationFailedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    /** Bad user's name. */
    private String _name;
  
    /** Bad user's email. */
    private String _email;

    /**
    * @param name
    * @param email
    */
    public RegistrationFailedException(String name, String email) {
        _name = name;
        _email = email;
    }

    /** @return id */
    public String getName() {
        return _name;
    }

    /** @return id */
    public String getEmail() {
        return _email;
    }

}