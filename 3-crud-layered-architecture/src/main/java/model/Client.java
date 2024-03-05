package model;

/**
 * @Description: Clasa in care se face maparea corespunzatore tabelului 'client' din baza de date.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    public Client() {
        this(-1,"","","",0);
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Client
     * @param id id-ul clientului
     * @param name numele clientului
     * @param address adresa clientului
     * @param email emailul clientului
     * @param age varsta clientului
     */
    public Client(int id, String name, String address, String email, int age) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * Constructorul clasei. Aici se initializeaza variabilele instanta ale obiectului de tip Client
     * @param name numele clientului
     * @param address adresa clientului
     * @param email emailul clientului
     * @param age varsta clientului
     */
    public Client(String name, String address, String email, int age) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int idclient) {
        this.id = idclient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age + "]";
    }

}
