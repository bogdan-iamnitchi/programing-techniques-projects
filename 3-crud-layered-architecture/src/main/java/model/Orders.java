package model;

import java.util.Date;

/**
 * @Description: Clasa in care se face maparea corespunzatore tabelului 'order' din baza de date.
 */
public class Orders {

    private int id;
    private int idclient;
    private float price;
    private Date date;

    public Orders(){
        this(-1, -1, 0.0f, new Date());
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Order
     * @param id id-ul unic pt comanda
     * @param idclient id_ul clientului
     * @param price pretul total de achitat
     * @param date data la care s-a efectuat comanda
     */
    public Orders(int id, int idclient, float price, Date date) {
        super();
        this.id = id;
        this.idclient = idclient;
        this.price = price;
        this.date = date;
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Order
     * @param idclient id_ul clientului
     * @param price pretul total de achitat
     * @param date data la care s-a efectuat comanda
     */
    public Orders(int idclient, float price, Date date) {
        super();
        this.idclient = idclient;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setId(int idorder) {
        this.id = idorder;
    }
    public int getIdclient() {
        return idclient;
    }
    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", idclient=" + idclient + ", totalPrice=" + price + ", Date=" + date + "]";
    }

}
