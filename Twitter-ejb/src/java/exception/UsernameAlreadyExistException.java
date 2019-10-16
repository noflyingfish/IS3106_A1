package exception;

public class UsernameAlreadyExistException extends Exception {

    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String s) {
        super(s);
    }
}
