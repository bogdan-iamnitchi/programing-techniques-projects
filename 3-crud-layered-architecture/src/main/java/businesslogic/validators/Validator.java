package businesslogic.validators;

/**
 * @Description: Interfata care descrie coportamentul unei clase de validare.
 */
public interface Validator<T> {

	public void validate(T t) throws IllegalArgumentException;
}
