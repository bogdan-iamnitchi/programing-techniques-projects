package businesslogic;

import dao.OrderListDAO;
import exception.DeletionFailedException;
import exception.UpdateFailedException;
import model.OrderList;
import model.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Description: Clasa de orderlist bussines logic in care sa apeleaza functii utile din DAO, respectiv sa valideaza rezulttaul primit aruncand o exceptie.
 */
public class OrderListBLL {

    private OrderListDAO orderListDAO;

    /**
     * Constructor care inițializează instanța de OrderListDAO.
     */
    public OrderListBLL() {
        orderListDAO = new OrderListDAO();
    }

    /**
     * Caută și returnează lista de comenzi cu ID-ul dat.
     *
     * @param id ID-ul listei de comenzi căutate
     * @return Lista de comenzi cu ID-ul dat
     * @throws NoSuchElementException dacă lista de comenzi nu a fost găsită în baza de date
     */
    public OrderList findOrderListById(int id) {
        OrderList st = orderListDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The orderlist with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Returnează toate listele de comenzi din baza de date.
     *
     * @return Lista de comenzi
     * @throws NoSuchElementException dacă nu există nicio listă de comenzi în baza de date
     */
    public List<OrderList> findAllOrderList() {
        List<OrderList> st = orderListDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("No orderlist found!");
        }
        return st;
    }

    /**
     * Inserează o nouă listă de comenzi în baza de date.
     *
     * @param orderList Lista de comenzi care urmează să fie inserată
     * @return Lista de comenzi inserată
     * @throws InstantiationException dacă inserarea în tabela de liste de comenzi a eșuat
     */
    public OrderList insertOrderList(OrderList orderList) throws InstantiationException {
        OrderList st = orderListDAO.insert(orderList);
        if (st == null) {
            throw new InstantiationException("Inserting into orderlist tabel failed!");
        }
        return st;
    }

    /**
     * Actualizează o listă de comenzi în baza de date.
     *
     * @param orderList Lista de comenzi care urmează să fie actualizată
     * @return Lista de comenzi actualizată
     * @throws UpdateFailedException dacă actualizarea în tabela de liste de comenzi a eșuat
     */
    public OrderList updateOrderList(OrderList orderList) throws UpdateFailedException {
        OrderList st = orderListDAO.update(orderList);
        if (st == null) {
            throw new UpdateFailedException("Updating the orderlist tabel failed!");
        }
        return st;
    }

    /**
     * Șterge o listă de comenzi din baza de date.
     *
     * @param id ID-ul listei de comenzi care urmează să fie ștearsă
     * @throws DeletionFailedException dacă ștergerea în tabela de liste de comenzi a esuat
     **/
    public void deleteOrderList(int id) throws DeletionFailedException {
        int st = orderListDAO.deleteById(id);
        if (st == 0) {
            throw new DeletionFailedException("Deleting in orderlist tabel failed!");
        }
    }

    /**
     * Șterge toate produsele dintr-o listă de comenzi.
     *
     * @param id ID-ul listei de comenzi din care se vor șterge produsele
     * @throws DeletionFailedException dacă nu există niciun produs în tabela OrderList de șters sau dacă ștergerea a eșuat
     */
    public void deleteByIDOrderOrderList(int id) throws DeletionFailedException {
        int st = orderListDAO.deleteByIdOrder(id);
        if (st == 0) {
            throw new DeletionFailedException("No product in OrderList tabel tot delete!");
        }
        else if(st < 0){
            throw new DeletionFailedException("Deleting in orderlist tabel failed!");
        }
    }

}
