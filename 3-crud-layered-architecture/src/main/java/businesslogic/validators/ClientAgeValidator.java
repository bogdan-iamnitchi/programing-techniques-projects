package businesslogic.validators;

import model.Client;

/**
 * @Description: Clasa care implementeaza validarea corespunzatoare varstei minime suficiente pentru a face o comanda.
 */
public class ClientAgeValidator implements Validator<Client> {
	private static final int MIN_AGE = 18;

	public void validate(Client t) throws IllegalArgumentException{

		if (t.getAge() < MIN_AGE) {
			throw new IllegalArgumentException("Age limit is not respected!");
		}

	}

}
