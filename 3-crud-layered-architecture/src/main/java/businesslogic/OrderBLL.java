package businesslogic;

import dao.OrderDAO;
import exception.DeletionFailedException;
import exception.UpdateFailedException;
import model.Client;
import model.Orders;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Description: Clasa de order bussines logic in care sa apeleaza functii utile din DAO, respectiv sa valideaza rezulttaul primit aruncand o exceptie.
 */
public class OrderBLL {

    private OrderDAO orderDAO;

    /**
     * Constructor care inițializează instanța de OrderDAO.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * Caută și returnează comanda cu ID-ul dat.
     * @param id ID-ul comenzii căutate
     * @return Comanda cu ID-ul dat
     * @throws NoSuchElementException dacă comanda nu a fost găsită în baza de date
     */
    public Orders findOrderById(int id) {
        Orders st = orderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The orders with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Caută și returnează valoarea maximă a ID-urilor comenzilor.
     *
     * @return Valoarea maximă a ID-urilor comenzilor
     * @throws NoSuchElementException dacă interogarea nu returnează rezultate valide
     */
    public int findMaxIDOrder() {
        int st = orderDAO.findMaxID();
        if (st == -1) {
            throw new NoSuchElementException("Something went wrong!");
        }
        return st;
    }

    /**
     * Returnează toate comenzile din baza de date.
     *
     * @return Lista de comenzi
     * @throws NoSuchElementException dacă nu există nicio comandă în baza de date
     */
    public List<Orders> findAllOrders() {
        List<Orders> st = orderDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("No orders found!");
        }
        return st;
    }

    /**
     * Inserează o comandă nouă în baza de date.
     *
     * @param order Comanda care urmează să fie inserată
     * @return Comanda inserată
     * @throws InstantiationException dacă inserarea în tabela de comenzi a eșuat
     */
    public Orders insertOrder(Orders order) throws InstantiationException {
        Orders st = orderDAO.insert(order);
        if (st == null) {
            throw new InstantiationException("Inserting into orders tabel failed!");
        }
        return st;
    }

    /**
     * Actualizează o comandă în baza de date.
     *
     * @param order Comanda care urmează să fie actualizată
     * @return Comanda actualizată
     * @throws UpdateFailedException dacă actualizarea în tabela de comenzi a eșuat
     */
    public Orders updateOrder(Orders order) throws UpdateFailedException {
        Orders st = orderDAO.update(order);
        if (st == null) {
            throw new UpdateFailedException("Updating the orders tabel failed!");
        }
        return st;
    }

    /**
     * Șterge o comandă din baza de date.
     * @param id ID-ul comenzii care urmează să fie ștearsă
     * @throws DeletionFailedException dacă ștergerea în tabela de comenzi a eșuat
     */
    public void deleteOrder(int id) throws DeletionFailedException {
        int st = orderDAO.deleteById(id);
        if (st == 0) {
            throw new DeletionFailedException("Deleting in orders tabel failed!");
        }
    }

    /**
     * Șterge toate comenzile din baza de date.
     *
     * @throws DeletionFailedException dacă ștergerea tuturor comenzilor din tabela de comenzi a eșuat
     */
    public void deleteAllOrder() throws DeletionFailedException {
        int st = orderDAO.deleteAll();
        if (st < 0) {
            throw new DeletionFailedException("Deleting all orders failed!");
        }
    }

}
