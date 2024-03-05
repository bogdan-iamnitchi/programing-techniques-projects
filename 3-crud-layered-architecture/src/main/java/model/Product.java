package model;

/**
 * @Description: Clasa in care se face maparea corespunzatore tabelului 'product' din baza de date.
 */
public class Product {

    private int id;
    private String name;
    private int quantity;
    private float price;

    public Product(){
        this(-1, "", 0, 0.0f);
    }
    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Product
     * @param id id-ul produsului
     * @param name numele produsului
     * @param quantity cantitatea de pe stoc
     * @param price valoarea
     */
    public Product(int id, String name, int quantity, float price) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Product
     * @param name numele produsului
     * @param quantity cantitatea de pe stoc
     * @param price valoarea
     */
    public Product(String name, int quantity, float price) {
        super();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int idproduct) {
        this.id = idproduct;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
    }
}
