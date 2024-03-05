package model;

/**
 * @Description: Clasa in care se face maparea corespunzatore tabelului 'orderlist' din baza de date.
 */

public class OrderList {

    private int id;
    private int idproduct;
    private int quantity;
    private float price;
    private int idorder;

    public OrderList(){
        this(-1,-1,0,0.0f,-1);
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip OrderList
     * @param id id-ul unic pt orderlist
     * @param idproduct id_ul produsului
     * @param quantity cantitatea dorita
     * @param price pretul/buc * cantitatea
     * @param idorder id-ul comenzii
     */
    public OrderList(int id, int idproduct, int quantity, float price, int idorder) {
        super();
        this.id = id;
        this.idproduct = idproduct;
        this.quantity = quantity;
        this.price = price;
        this.idorder = idorder;
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip OrderList
     * @param idproduct id_ul produsului
     * @param quantity cantitatea dorita
     * @param price pretul/buc * cantitatea
     * @param idorder id-ul comenzii
     */
    public OrderList(int idproduct, int quantity, float price,  int idorder) {
        super();
        this.idproduct = idproduct;
        this.quantity = quantity;
        this.price = price;
        this.idorder = idorder;
    }

    public int getId() {
        return id;
    }
    public void setId(int idorderlist) {
        this.id = idorderlist;
    }
    public int getIdproduct() {
        return idproduct;
    }
    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getIdorder() {
        return idorder;
    }
    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }

    @Override
    public String toString() {
        return "OrderList [id=" + id + ", idproduct=" + idproduct + ", quantity=" + quantity + ", price=" + price + ", idorder=" + idorder + "]";
    }

}
