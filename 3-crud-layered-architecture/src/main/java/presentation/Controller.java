package presentation;

import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.OrderListBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.OrderList;
import model.Orders;
import model.Product;
import start.SolveOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clasa Controller gestionează interacțiunea dintre interfața grafică (View) și logica de business (BLL).
 * Aici sunt implementate metodele pentru tratarea evenimentelor și operațiilor necesare în aplicație.
 */
public class Controller{

    private View screen;

    /**
     * Constructorul clasei Controller.
     *
     * @param screen Referință către obiectul de tip View asociat controlerului.
     */
    public Controller(View screen){
        this.screen = screen;

        solveClientsButtons();
        solveProductsButtons();
        solveMarketButtons();
    }
    //-------------------------------------------------------------MARKET
    /**
     * Actualizează tabela de clienți în interfața de tip Market cu informațiile din baza de date.
     */
    private void reloadClientsMarketTable(){
        ClientBLL clientBll = new ClientBLL();
        List<Client> newClients = null;

        try {
            newClients = clientBll.findAllClients();
        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        screen.removeAllTabelClientsMArket();
        for (Client client : newClients) {
            screen.addTabelClientsMarket(SolveOutput.retrieveProperties(client));
        }
    }

    /**
     * Actualizează tabela de produse în interfața de tip Market cu informațiile din baza de date.
     */
    private void reloadProductsMarketTable(){
        ProductBLL productBLL = new ProductBLL();
        List<Product> products = null;

        try {
            products = productBLL.findAllProducts();
        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        screen.removeAllTabelProductsMarket();
        for (Product product : products) {
            screen.addTabelProductsMarket(SolveOutput.retrieveProperties(product));
        }
    }

    /**
     * Adaugă o nouă comandă în tabela de comenzi.
     */
    private void addOrderTable(){
        OrderBLL ordersBLL = new OrderBLL();
        Orders newOrder = null;

        try {
            Calendar c = Calendar.getInstance();

            int idclient =  screen.getSavedClient().getId();
            float price = screen.getTotalPrice();
            Date date = c.getTime();

            newOrder = ordersBLL.insertOrder(new Orders(idclient, price, date));
            //screen.showSuccess("Order recived succesfully!");

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
        }
    }

    /**
     * Actualizează o comandă existentă în tabela de comenzi.
     */
    private void updateOrderTable(){
        OrderBLL ordersBLL = new OrderBLL();
        Orders newOrder = null;

        try {
            Calendar c = Calendar.getInstance();

            int id = screen.getOrderID();
            int idclient =  screen.getSavedClient().getId();
            float price = screen.getTotalPrice();
            Date date = c.getTime();

            newOrder = ordersBLL.updateOrder(new Orders(id, idclient, price, date));
            screen.showSuccess("Order recived succesfully!");

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
        }
    }

    /**
     * Șterge o comandă din tabela de comenzi.
     */
    private void deleteOrderTable(){
        OrderBLL ordersBLL = new OrderBLL();
        try {
            ordersBLL.deleteOrder(screen.getOrderID());
            screen.showSuccess("Order canceled succesfully!");

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
        }
    }

    /**
     * Găsește ID-ul maxim al comenzii din tabela de comenzi.
     *
     * @return ID-ul maxim al comenzii
     */
    private int findMaxIDOrderTable(){
        OrderBLL ordersBLL = new OrderBLL();
        try {
            return  ordersBLL.findMaxIDOrder();

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return 0;
        }
    }

    /**
     * Adaugă un nou produs în tabela de produse a unei comenzi.
     *
     * @param product Produsul de adăugat în comandă
     */
    private void addOrderListTable(Product product){
        OrderListBLL ordersListBLL = new OrderListBLL();
        OrderList newOrderList = null;

        try {
            int idpoduct =  product.getId();
            int quantity =  product.getQuantity();
            float price = product.getPrice();
            int idorder = screen.getOrderID();

            newOrderList = ordersListBLL.insertOrderList(new OrderList(idpoduct, quantity, price, idorder));

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
        }
    }

    /**
     * Șterge produsele unei comenzi din tabela de produse a comenzii.
     */
    private void deleteByIDOrderOrderListTable(){
        OrderListBLL ordersListBLL = new OrderListBLL();
        try {
            int idorder = screen.getOrderID();
            ordersListBLL.deleteByIDOrderOrderList(idorder);
            screen.showSuccess("Products from order list removed succesfully!");

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
        }

    }

    /**
     * Gestionează acțiunile și evenimentele din interfața de tip Market.
     */
    private void solveMarketButtons(){
        reloadClientsMarketTable();
        reloadProductsMarketTable();

        screen.cancelMarketButtonListener( e -> {
            screen.cancelClient();
            deleteOrderTable();
        });
        screen.selectMarketButtonListener( e -> {
            screen.selectClient();
            if(screen.getIsSelected()){
                addOrderTable();
                screen.setOrderID(findMaxIDOrderTable());
            }else {
                screen.showError("Insert a client first!");
            }
        });
        screen.deleteMarketButtonListener( e -> {
            screen.clearProducts();
            deleteByIDOrderOrderListTable();
        });
        screen.addproductMarketButtonListener(e -> {
            if(screen.getIsSelected()){
                screen.saveProduct();
                addOrderListTable(screen.getSelectedProduct());
            }
            else {
                screen.showError("Insert a client first!");
            }

        });
        screen.printMarketButtonListener( e -> {
            if(screen.getIsSelected()){
                screen.printBill();
            }
            else {
                screen.showError("Insert a client first!");
            }
        });
        screen.sendMarketButtonListener( e -> {
            if(screen.getIsSelected()){
                updateOrderTable();
                screen.cancelClient();

            }else {
                screen.showError("Insert a client first!");
            }
        });
    }


    //-------------------------------------------------------------CLIENTS
    /**
     * Actualizează tabela de clienți în interfața grafică.
     */
    private void reloadClientsTable(){
        ClientBLL clientBll = new ClientBLL();
        List<Client> newClients = null;

        try {
            newClients = clientBll.findAllClients();
        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        screen.removeAllTabelClients();
        for (Client client : newClients) {
            screen.addTabelClients(SolveOutput.retrieveProperties(client));
        }
        screen.refreshResultsListClients("Reload completed succesfuly... " + newClients.size() + " returned!");
    }

    /**
     * Adauga un client nou in tabela de clienti
     */
    private void addClientsTable(){
        ClientBLL clientBll = new ClientBLL();
        Client newClient = null;

        try {
            String name = "'" + screen.getNameTextClients() + "'";
            String address =  "'" +screen.getAddresTextClients() + "'";
            String email =  "'" +screen.getEmailTextClients() + "'";
            int age= Integer.parseInt(screen.getAgeTextClients());
            int id;
            if(!Objects.equals(screen.getIDTextClients(), "")) {
                id = Integer.parseInt(screen.getIDTextClients());
                newClient = clientBll.insertClient(new Client(id, name, address, email, age));
            }
            else {
                newClient = clientBll.insertClient(new Client(name, address, email, age));
            }

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadClientsTable();
        screen.refreshResultsListClients("Insertion completed succesfuly!");
    }

    /**
     * Actualizează un client existent în tabela de clienți.
     */
    private void updateClientsTable(){
        ClientBLL clientBll = new ClientBLL();
        Client newClient = null;
        try {
            int id = Integer.parseInt(screen.getIDTextClients());
            String name = "'" + screen.getNameTextClients() + "'";
            String address =  "'" +screen.getAddresTextClients() + "'";
            String email =  "'" +screen.getEmailTextClients() + "'";
            int age= Integer.parseInt(screen.getAgeTextClients());

            newClient = clientBll.updateClient(new Client(id, name, address, email, age));

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadClientsTable();
        screen.refreshResultsListClients("Update completed succesfuly!");
    }

    /**
     * Șterge un client din tabela de clienți.
     */
    private void deleteClientsTable(){
        ClientBLL clientBll = new ClientBLL();
        try {
            int id = Integer.parseInt(screen.getIDTextClients());
            clientBll.deleteClient(id);

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadClientsTable();
        screen.refreshResultsListClients("Delete completed succesfuly!");
    }

    /**
     * Gestionează acțiunile și evenimentele din interfața de tip Client.
     */
    public void solveClientsButtons(){
        reloadClientsTable();

        screen.reloadButtonClientsListener( e -> {
            reloadClientsTable();
        });

        screen.addButtonClientsListener( e -> {
            addClientsTable();
        });

        screen.updateButtonClientsListener( e -> {
            updateClientsTable();
        });

        screen.deleteButtonClientsListener( e -> {
            deleteClientsTable();
        });

        screen.clearButtonClientsListener( e -> {
            screen.clearInputClients();
        });
    }

    //-------------------------------------------------------------PRODUCTS
    /**
     * Actualizează tabela de produse în interfața grafică.
     */
    private void reloadProductsTable(){
        ProductBLL productBLL = new ProductBLL();
        List<Product> products = null;

        try {
            products = productBLL.findAllProducts();
        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        screen.removeAllTabelProducts();
        for (Product product : products) {
            screen.addTabelProducts(SolveOutput.retrieveProperties(product));
        }
        screen.refreshResultsListProducts("Reload completed succesfuly... " + products.size() + " returned!");
    }

    /**
     * Adaugă un nou produs în tabela de produse.
     */
    private void addProductsTable(){
        ProductBLL productBLL = new ProductBLL();
        Product newProduct = null;
        System.out.println("Aici");
        try {
            String name = "'" + screen.getNameTextProducts() + "'";
            int quantity =  Integer.parseInt(screen.getQuantityTextProducts());
            float price =  Float.parseFloat(screen.getPriceTextProducts());
            System.out.println(screen.getPriceTextProducts());

            int id;
            if(!Objects.equals(screen.getIDTextProducts(), "")) {
                id = Integer.parseInt(screen.getIDTextProducts());
                newProduct = productBLL.insertProduct(new Product(id, name, quantity, price));
            }
            else {
                newProduct = productBLL.insertProduct(new Product(name, quantity, price));
            }
        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadProductsTable();
        screen.refreshResultsListProducts("Insertion completed succesfuly!");
    }

    /**
     * Actualizează un produs existent în tabela de produse.
     */
    private void updateProductsTable(){
        ProductBLL productBLL = new ProductBLL();
        Product newProduct = null;

        try {
            int id = Integer.parseInt(screen.getIDTextProducts());
            String name = "'" + screen.getNameTextProducts() + "'";
            int quantity =  Integer.parseInt(screen.getQuantityTextProducts());
            float price =  Float.parseFloat(screen.getPriceTextProducts());

            newProduct = productBLL.updateProduct(new Product(id, name, quantity, price));

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadProductsTable();
        screen.refreshResultsListProducts("Update completed succesfuly!");
    }

    /**
     * Șterge un produs din tabela de produse.
     */
    private void deleteProductsTable(){
        ProductBLL productBLL = new ProductBLL();
        try {
            int id = Integer.parseInt(screen.getIDTextProducts());
            productBLL.deleteProduct(id);

        } catch (Exception ex) {
            screen.showError(ex.getMessage());
            return;
        }
        reloadProductsTable();
        screen.refreshResultsListProducts("Delete completed succesfuly!");
    }

    /**
     * Gestionează acțiunile și evenimentele din interfața de tip Product.
     */
    public void solveProductsButtons(){
        reloadProductsTable();

        screen.reloadButtonProductsListener( e -> {
            reloadProductsTable();
        });

        screen.addButtonProductsListener( e -> {
            addProductsTable();
        });

        screen.updateButtonProductsListener( e -> {
            updateProductsTable();
        });

        screen.deleteButtonProductsListener( e -> {
            deleteProductsTable();
        });

        screen.clearButtonProductsListener( e -> {
            screen.clearInputProducts();
        });
    }

}