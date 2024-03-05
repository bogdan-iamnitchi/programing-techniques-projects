package businesslogic;

import dao.ProductDAO;
import exception.DeletionFailedException;
import exception.UpdateFailedException;
import model.Client;
import model.OrderList;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Description: Clasa de product bussines logic in care sa apeleaza functii utile din DAO, respectiv sa valideaza rezulttaul primit aruncand o exceptie.
 */
public class ProductBLL {

    private ProductDAO productDAO;

    /**
     * Constructor care inițializează instanța de ProductDAO.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Găsește un produs după ID-ul său.
     *
     * @param id ID-ul produsului care trebuie găsit
     * @return produsul cu ID-ul specificat
     * @throws NoSuchElementException dacă produsul cu ID-ul dat nu este găsit
     */
    public Product findProductsById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Găsește toate produsele existente.
     *
     * @return o listă cu toate produsele
     * @throws NoSuchElementException dacă nu sunt găsite produse în sistem
     */
    public List<Product> findAllProducts() {
        List<Product> st = productDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("No products found!");
        }
        return st;
    }

    /**
     * Inserează un produs nou.
     *
     * @param product produsul care trebuie inserat
     * @return produsul inserat cu succes
     * @throws InstantiationException dacă inserarea în tabela de produse nu a reușit
     */
    public Product insertProduct(Product product) throws InstantiationException {
        Product st = productDAO.insert(product);
        if (st == null) {
            throw new InstantiationException("Inserting into product tabel failed!");
        }
        return st;
    }

    /**
     * Actualizează un produs existent.
     *
     * @param product produsul care trebuie actualizat
     * @return produsul actualizat cu succes
     * @throws UpdateFailedException dacă actualizarea în tabela de produse nu a reușit
     */
    public Product updateProduct(Product product) throws UpdateFailedException {
        Product st = productDAO.update(product);
        if (st == null) {
            throw new UpdateFailedException("Updating the product tabel failed!");
        }
        return st;
    }

    /**
     * Șterge un produs în funcție de ID-ul său.
     *
     * @param id ID-ul produsului care trebuie șters
     * @throws DeletionFailedException dacă ștergerea din tabela de produse nu a reușit
     */
    public void deleteProduct(int id) throws DeletionFailedException {
        int st = productDAO.deleteById(id);
        if (st == 0) {
            throw new DeletionFailedException("Deleting in product tabel failed!");
        }
    }

}
