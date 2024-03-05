package exception;

/**
 * Clasa de tipul Exception, creata special pt excepti de timpul Insert Failure
 */
public class InsertionFailedException extends Exception{
    /**
     * Constructorul clasei
     * @param msg mesajul pe care vrei sa il afisezi atunci cand se ajunge la aceasta exceptie
     */
    public InsertionFailedException(String msg) { super(msg); }
}
