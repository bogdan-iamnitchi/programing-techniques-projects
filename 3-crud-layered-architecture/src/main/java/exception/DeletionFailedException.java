package exception;

/**
 * Clasa de tipul Exception, creata special pt excepti de timpul Delete Failure
 */
public class DeletionFailedException extends Exception{
    /**
     * Constructorul clasei
     * @param msg mesajul pe care vrei sa il afisezi atunci cand se ajunge la aceasta exceptie
     */
    public DeletionFailedException(String msg)
    {
        super(msg);
    }
}