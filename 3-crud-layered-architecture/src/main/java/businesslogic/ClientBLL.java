package businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import businesslogic.validators.EmailValidator;
import businesslogic.validators.ClientAgeValidator;
import businesslogic.validators.Validator;
import dao.ClientDAO;
import exception.DeletionFailedException;
import exception.InsertionFailedException;
import exception.UpdateFailedException;
import model.Client;
import model.Orders;

/**
 * @Description: Clasa de client bussines logic in care sa apeleaza functii utile din DAO, respectiv sa valideaza rezulttaul primit aruncand o exceptie.
 */
public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Constructor care inițializează instanța de ClientDAO și lista de validatori.
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Validează datele unui client, apelând metodele corespunzătoare din ClientBLL. Verifică, de asemenea, dacă inserarea are loc cu succes,
     * iar în caz contrar aruncă o excepție de tipul Exception.
     * @param client clientul care urmează să fie validat
     * @throws Exception dacă validarea nu reușește
     */
    public void validateInput(Client client) throws Exception {
        for(Validator<Client> v : validators) {
            try {
                v.validate(client);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * Caută un client în baza de date după ID-ul său, apelând metoda corespunzătoare din ClientDAO. Verifică, de asemenea, dacă căutarea are loc cu succes,
     * iar în caz contrar aruncă o excepție de tipul NoSuchElementException.
     * @param id ID-ul clientului dorit
     * @return clientul cu ID-ul specificat
     * @throws NoSuchElementException dacă clientul cu ID-ul dat nu este găsit
     */
    public Client findClientsById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Găsește toți clienții existenți în sistem.
     * @return o listă cu toți clienții
     * @throws NoSuchElementException dacă nu sunt găsiți clienți în sistem
     */
    public List<Client> findAllClients() {
        List<Client> st = clientDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("No clients found!");
        }
        return st;
    }

    /**
     * Inserează un client nou. Validează, de asemenea, datele clientului și verifică dacă inserarea are loc cu succes,
     * iar în caz contrar aruncă o excepție de tipul InsertionFailedException.
     * @param client clientul care urmează să fie inserat
     * @return clientul inserat cu succes
     * @throws InsertionFailedException dacă inserarea în tabela de clienți nu a reușit
     */
    public Client insertClient(Client client) throws Exception{
        try {
            validateInput(client);
        } catch (Exception e) {
            throw new InsertionFailedException(e.getMessage());
        }
        Client st = clientDAO.insert(client);
        if (st == null) {
            throw new InsertionFailedException("Inserting into orders tabel failed!");
        }
        return st;
    }

    /**
     * Actualizează un client existent. Validează, de asemenea, datele clientului și verifică dacă actualizarea are loc cu succes,
     * iar în caz contrar aruncă o excepție de tipul UpdateFailedException.
     * @param client obiectul clientului modificat
     * @return clientul actualizat cu succes
     * @throws UpdateFailedException dacă actualizarea în tabela de clienți nu a reușit
     */
    public Client updateClient(Client client) throws Exception {
        try {
            validateInput(client);
        } catch (Exception e) {
            throw new UpdateFailedException(e.getMessage());
        }
        Client st = clientDAO.update(client);
        if (st == null) {
            throw new UpdateFailedException("Updating the clients tabel failed!");
        }
        return st;
    }

    /**
     * Șterge un client în funcție de ID-ul său. Odată cu ștergerea clientului, se vor șterge și toate comenzile acestuia din Orders.
     * Verifică, de asemenea, dacă ștergerea are loc cu succes, iar în caz contrar aruncă o excepție de tipul DeletionFailedException.
     * @param id ID-ul clientului dorit
     * @throws DeletionFailedException dacă ștergerea din tabela de clienți nu a reușit
     */
    public void deleteClient(int id) throws DeletionFailedException {
        int st = clientDAO.deleteById(id);
        if (st == 0) {
            throw new DeletionFailedException("Deleting in clients tabel failed!");
        }
    }

}
