package exception;

/**
 * Clasa de tipul Exception, creata special pt excepti de timpul Update Failure
 */
public class UpdateFailedException extends Exception{
    /**
     * Constructorul clasei
     * @param msg mesajul pe care vrei sa il afisezi atunci cand se ajunge la aceasta exceptie
     */
    public UpdateFailedException(String msg)
    {
        super(msg);
    }
}