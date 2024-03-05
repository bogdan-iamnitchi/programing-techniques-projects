package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class View extends JFrame{
    private JPanel mainPanel;
    private JButton productsButton;
    private JTabbedPane tabbedPane;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonsPanel;
    private JPanel titlePane;
    private JButton marketButton;
    private JButton clientsButton;
    private JButton homeButton;
    private JPanel homePanel;
    private JPanel marketPanel;
    private JPanel clientsPanel;
    private JScrollPane scrollPaneClients;

    private JPanel clientsLeftPanel;
    private JTextField idTextClients;
    private JTextField nameTextClients;
    private JTextField addresTextClients;
    private JTextField emailTextClients;
    private JTextField ageTextClients;
    private JButton addButtonClients;
    private JButton updateButtonClients;
    private JButton clearButtonClients;
    private JButton deleteButtonClients;
    private JPanel productsPanel;
    private JScrollPane scrollPaneProducts;

    private JTable clientsTable;
    DefaultTableModel modelClients ;
    private JTable productsTable;
    DefaultTableModel modelProducts;
    private JScrollPane listScrollPaneProducts;
    private JList<Object> listProducts;
    private JPanel productsLeftPanel;
    private JButton addButtonProducts;
    private JButton updateButtonProducts;
    private JButton clearButtonProducts;
    private JButton deleteButtonProducts;
    private JTextField idTextProducts;
    private JTextField nameTextProducts;
    private JTextField quantityTextProducts;
    private JTextField priceTextProducts;
    private JScrollPane listScrolPaneClients;
    private JList<Object> listClients;
    private JButton reloadButtonProducts;
    private JButton reloadButtonClients;
    private JTable productsMarketTable;
    private DefaultTableModel modelProductsMarket;
    private JTable clientsMarketTable;
    private DefaultTableModel modelClientsMarket;
    private JPanel marketLeftPanel;
    private JPanel marketRightPanel;
    private JTextArea bill;
    private JTextField nameTextClientsMarket;
    private JTextField addressTextClientsMarket;
    private JTextField nameTextProductsMarket;
    private JTextField quantityTextProductsMarket;
    private JTextField priceTextProductsMarket;
    private JButton cancelMarketButton;
    private JButton selectMarketButton;
    private JButton deleteMarketButton;
    private JButton saveMarketButton;
    private JButton printMarketButton;
    private JButton sendMarketButton;
    private JLabel nameLabelClientsMarket;
    private JLabel addressLabelClientsMarket;
    private JLabel titleLabelClientsMarket;
    private JScrollPane scrollPaneClientsMarket;
    private JScrollPane scrollPaneProductsMarket;
    private ArrayList<Vector<String>> clientsTableArray;
    private ArrayList<Vector<String>> productsTableArray;
    private ArrayList<Vector<String>> clientsMarketTableArray;
    private ArrayList<Vector<String>> productsMarketTableArray;

    private DefaultListModel<Object> clientsListModel;
    private DefaultListModel<Object> productsListModel;
    private ListSelectionModel clientsTableSelectionModel;
    private ListSelectionModel productsTableSelectionModel;
    private ListSelectionModel clientsMarketTableSelectionModel;
    private ListSelectionModel productsMarketTableSelectionModel;

    Client selectedClient;
    Product selectedProduct;
    float totalPrice = 0.0f;

    public View() {
        super("Orders Management - TP Project");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        savedProducts = new ArrayList<>();
        clientsTableArray = new ArrayList<>();
        productsTableArray = new ArrayList<>();
        clientsMarketTableArray = new ArrayList<>();
        productsMarketTableArray = new ArrayList<>();

        clientsListModel = new DefaultListModel<>();
        productsListModel = new DefaultListModel<>();
        listClients.setModel(clientsListModel);
        listProducts.setModel(productsListModel);

        clientsTableSelectionModel = createSelectionClients();
        productsTableSelectionModel = createSelectionProducts();
        clientsMarketTableSelectionModel = createSelectionClientsMarket();
        productsMarketTableSelectionModel = createSelectionProductsMarket();

        fixTable(scrollPaneClients, clientsTable);
        fixTable(scrollPaneProducts, productsTable);
        fixTable(scrollPaneClientsMarket, clientsMarketTable);
        fixTable(scrollPaneProductsMarket, productsMarketTable);

        tabbedPane.setSelectedIndex(1);
        hideTabs();
        solveTabsButtons();
    }

    private ListSelectionModel createSelectionClients(){
        ListSelectionModel listSelectionModel = clientsTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(!clientsTableSelectionModel.isSelectionEmpty()) {
                    int selectedRow = listSelectionModel.getMinSelectionIndex();
                    System.out.println(selectedRow);
                    idTextClients.setText(clientsTableArray.get(selectedRow).get(0));
                    nameTextClients.setText(clientsTableArray.get(selectedRow).get(1));
                    addresTextClients.setText(clientsTableArray.get(selectedRow).get(2));
                    emailTextClients.setText(clientsTableArray.get(selectedRow).get(3));
                    ageTextClients.setText(clientsTableArray.get(selectedRow).get(4));
                }
            }
        });
        return listSelectionModel;
    }
    private ListSelectionModel createSelectionProducts(){
        ListSelectionModel listSelectionModel = productsTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(!productsTableSelectionModel.isSelectionEmpty()) {
                    int selectedRow = listSelectionModel.getMinSelectionIndex();
                    idTextProducts.setText(productsTableArray.get(selectedRow).get(0));
                    nameTextProducts.setText(productsTableArray.get(selectedRow).get(1));
                    quantityTextProducts.setText(productsTableArray.get(selectedRow).get(2));
                    priceTextProducts.setText(productsTableArray.get(selectedRow).get(3));
                }
            }
        });
        return listSelectionModel;
    }
    private ListSelectionModel createSelectionClientsMarket(){
        ListSelectionModel listSelectionModel = clientsMarketTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(!clientsMarketTableSelectionModel.isSelectionEmpty()) {
                    int selectedRow = listSelectionModel.getMinSelectionIndex();

                    try {
                        int id = Integer.parseInt(clientsTableArray.get(selectedRow).get(0));
                        String name = clientsTableArray.get(selectedRow).get(1);
                        String address = clientsTableArray.get(selectedRow).get(2);
                        String email = clientsTableArray.get(selectedRow).get(3);
                        int age = Integer.parseInt(clientsTableArray.get(selectedRow).get(4));

                        selectedClient = new Client(id, name, address, email, age);
                        nameTextClientsMarket.setText(clientsTableArray.get(selectedRow).get(1));
                        addressTextClientsMarket.setText(clientsTableArray.get(selectedRow).get(2));
                    }catch (Exception e){
                        showError("Oops ... Wrong Input!");
                    }
                }
            }
        });
        return listSelectionModel;
    }
    private ListSelectionModel createSelectionProductsMarket(){
        ListSelectionModel listSelectionModel = productsMarketTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(!productsMarketTableSelectionModel.isSelectionEmpty()) {
                    int selectedRow = listSelectionModel.getMinSelectionIndex();

                    JLabel label = new JLabel("Plese enter the number of selected item: ");
                    label.setFont(new Font("Arial", Font.BOLD, 18));
                    String quanttityText =  JOptionPane.showInputDialog(null, label,"INPUT",JOptionPane.INFORMATION_MESSAGE);
                    String priceText = productsTableArray.get(selectedRow).get(3);
                    int cnt=-1;
                    float oldPrice=0.0f, newPrice=0.0f;
                    try {
                        cnt = Integer.parseInt(quanttityText);
                        oldPrice = Float.parseFloat(priceText);
                        newPrice = Math.round(cnt * oldPrice * 100.0f) / 100.0f;

                        int id = Integer.parseInt(productsTableArray.get(selectedRow).get(0));
                        selectedProduct = new Product(id, productsTableArray.get(selectedRow).get(1), cnt, newPrice);
                        nameTextProductsMarket.setText(productsTableArray.get(selectedRow).get(1));
                        quantityTextProductsMarket.setText(quanttityText);
                        priceTextProductsMarket.setText(Float.toString(newPrice));
                    }
                    catch (Exception e){
                        showError("Oops ... Wrong Input!");
                    }
                }
            }
        });
        return listSelectionModel;
    }
    private void hideTabs(){
        tabbedPane.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI(){
            protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex){}
        });
        Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        insets.top = -1;
        UIManager.put("TabbedPane.contentBorderInsets", insets);
    }
    private void solveTabsButtons(){
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tabbedPane.setSelectedIndex(0);
            }
        });
        marketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tabbedPane.setSelectedIndex(2);
            }
        });
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tabbedPane.setSelectedIndex(3);
            }
        });
    }


    //-----------------------------------------------------------------------------------------------------------------------RESULTS
    public void refreshResultsListClients(String mesaj) {
        clientsListModel.removeAllElements();
        clientsListModel.addElement("----------------------------------------------------------------------------");
        clientsListModel.addElement(mesaj);
        clientsListModel.addElement("----------------------------------------------------------------------------");
    }
    public void refreshResultsListProducts(String mesaj) {
        productsListModel.removeAllElements();
        productsListModel.addElement("----------------------------------------------------------------------------");
        productsListModel.addElement(mesaj);
        productsListModel.addElement("----------------------------------------------------------------------------");
    }
    public void clearInputClients(){
        idTextClients.setText("");
        nameTextClients.setText("");
        addresTextClients.setText("");
        emailTextClients.setText("");
        ageTextClients.setText("");

        clientsListModel.removeAllElements();
        removeAllTabelClients();
    }
    public void clearInputProducts(){
        idTextProducts.setText("");
        nameTextProducts.setText("");
        quantityTextProducts.setText("");
        priceTextProducts.setText("");

        productsListModel.removeAllElements();
        removeAllTabelProducts();
    }

    //----------------------------------------------------------------------------------------------------------------------SELECT/CANCEL

    private Client savedClient;
    private List<Product> savedProducts;
    private boolean isSelected = false;
    private int orderID = 1;

    public void selectClient(){
        savedClient = new Client(selectedClient.getId(), selectedClient.getName(), selectedClient.getAddress(), selectedClient.getEmail(), selectedClient.getAge());
        if(Objects.equals(nameTextClientsMarket, "") && Objects.equals(addressTextClientsMarket, "")) {
            isSelected = false;
        }
        else {
            isSelected = true;
            titleLabelClientsMarket.setEnabled(false);
            nameLabelClientsMarket.setEnabled(false);
            addressLabelClientsMarket.setEnabled(false);
            nameTextClientsMarket.setEnabled(false);
            addressTextClientsMarket.setEnabled(false);
            refreshBill();
        }
    }
    public void cancelClient(){
        savedClient = new Client();
        savedProducts.clear();
        isSelected = false;
        totalPrice = 0;

        titleLabelClientsMarket.setEnabled(true);
        nameLabelClientsMarket.setEnabled(true);
        addressLabelClientsMarket.setEnabled(true);
        nameTextClientsMarket.setEnabled(true);
        nameTextClientsMarket.setText("");
        addressTextClientsMarket.setEnabled(true);
        addressTextClientsMarket.setText("");

        nameTextProductsMarket.setText("");
        quantityTextProductsMarket.setText("");
        priceTextProductsMarket.setText("");
        clearBill();
    }

    public void saveProduct() {
        savedProducts.add(selectedProduct);
        totalPrice += selectedProduct.getPrice();
        totalPrice = Math.round(totalPrice * 1.09*100.0f)/100.0f;
        refreshBill();
    }
    public void clearProducts() {
        totalPrice = 0;
        savedProducts.clear();
        refreshBill();
        nameTextProductsMarket.setText("");
        quantityTextProductsMarket.setText("");
        priceTextProductsMarket.setText("");
    }
    //---------------------------------------------------------------------------------------------------------------------BILL
    public void refreshBill(){
        bill.setText("The Bogdan Marketplace Hub\n");
        bill.setText(bill.getText() + "Colombo, Srilanka, 589/ King Road\n");
        bill.setText(bill.getText() + "+9411 123456789\n");
        bill.setText(bill.getText() + "--------------------------------------------------------------------------------------\n");
        bill.setText(bill.getText() + "Nume client: " + savedClient.getName() + "\n");
        bill.setText(bill.getText() + "Addresa de facturare: " + savedClient.getAddress() + "\n");
        bill.setText(bill.getText() + "--------------------------------------------------------------------------------------\n");
        bill.setText(bill.getText() + "Iteam \t                                  Qty \t           Price  \n");
        bill.setText(bill.getText() + "--------------------------------------------------------------------------------------\n");

        for (Product product : savedProducts) {

            String name = product.getName();
            int qt = product.getQuantity();
            float prc = product.getPrice();

            bill.setText(bill.getText() + name+"\t         "+qt+"\t         "+prc+"\n");

        }
        bill.setText(bill.getText() + "--------------------------------------------------------------------------------------\n");
        bill.setText(bill.getText() + "Tax :\t"+ "9%"+"\n");
        bill.setText(bill.getText() + "TOTAL :\t"+ totalPrice +"\n");
        bill.setText(bill.getText() + "===============================================\n");
        bill.setText(bill.getText() +"                             Thanks For Your Business!"+"\n");
        bill.setText(bill.getText() + "-------------------------------------------------------------------------------------\n");
        bill.setText(bill.getText() +"                                   Software by Bogdan"+"\n");
    }
    public void clearBill(){
        bill.setText("");
    }
    public void printBill(){
        bill.setForeground(new Color(0,0,0));
        try{
            bill.print();
        } catch (Exception e) {
            showError(e.getMessage());
        } finally {
            bill.setForeground(new Color(230,230,230));
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------DIALOG
    public void showError(String errMessage) {
        UIManager.put("OptionPane.minimumSize",new Dimension(200,200));
        //JOptionPane.showMessageDialog(this, errMessage);

        JLabel label = new JLabel(errMessage);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        JOptionPane.showMessageDialog(this,label,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    public void showSuccess(String succesMessage) {
        UIManager.put("OptionPane.minimumSize",new Dimension(200,200));
        //JOptionPane.showMessageDialog(this, errMessage);

        JLabel label = new JLabel(succesMessage);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        JOptionPane.showMessageDialog(this,label,"SUCCESS",JOptionPane.INFORMATION_MESSAGE);
    }

    //--------------------------------------------------------------------------------------------------------------------------ADD/REMOVE
    public void addTabelClients(Vector<String> row) {
        modelClients.addRow(row);
        clientsTableArray.add(row);
    }
    public void removeAllTabelClients() {
        for (int row = clientsTable.getRowCount() - 1; row >= 0; row--) {
            modelClients.removeRow(row);
        }
        clientsTableArray.clear();
    }

    public void addTabelProducts(Vector<String> row) {
        modelProducts.addRow(row);
        productsTableArray.add(row);
    }
    public void removeAllTabelProducts() {
        for (int row = productsTable.getRowCount() - 1; row >= 0; row--) {
            modelProducts.removeRow(row);
        }
        productsTableArray.clear();
    }

    public void addTabelClientsMarket(Vector<String> row) {
        modelClientsMarket.addRow(row);
        clientsMarketTableArray.add(row);
    }
    public void removeAllTabelClientsMArket() {
        for (int row = clientsMarketTable.getRowCount() - 1; row >= 0; row--) {
            modelClientsMarket.removeRow(row);
        }
        clientsMarketTableArray.clear();
    }

    public void addTabelProductsMarket(Vector<String> row) {
        modelProductsMarket.addRow(row);
        productsMarketTableArray.add(row);
    }
    public void removeAllTabelProductsMarket() {
        for (int row = productsMarketTable.getRowCount() - 1; row >= 0; row--) {
            modelProductsMarket.removeRow(row);
        }
        productsMarketTableArray.clear();
    }

    //--------------------------------------------------------------------------------------CUSTOM_TABLES
    private void createUIComponents() {
        // TODO: place custom component creation code here

        clientsTable = createClientsTable();
        productsTable = createProductsTable();

        clientsMarketTable = createClientsMarketTable();
        productsMarketTable = createProductsMArketTable();

    }
    private JTable createClientsTable(){
        String[] headers = {"ID", "NAME", "ADDRESS", "EMAIL", "AGE"};
        modelClients = new DefaultTableModel(0, 5);
        modelClients.setColumnIdentifiers(headers);
        JTable table = new JTable(modelClients);

        TableDarkHeader header = new TableDarkHeader();
        TableDarkCell cell = new TableDarkCell();
        table.getTableHeader().setDefaultRenderer(header);
        table.setDefaultRenderer(Object.class, cell);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);     //ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);     //Name
        table.getColumnModel().getColumn(2).setPreferredWidth(300);     //Address
        table.getColumnModel().getColumn(3).setPreferredWidth(300);     //Email
        table.getColumnModel().getColumn(4).setPreferredWidth(50);     //Age

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        return table;
    }
    private JTable createProductsTable(){
        String[] headers = {"ID", "NAME", "QUANTITY", "PRICE"};
        modelProducts = new DefaultTableModel(0, 4);
        modelProducts.setColumnIdentifiers(headers);
        JTable table = new JTable(modelProducts);

        TableDarkHeader header = new TableDarkHeader();
        TableDarkCell cell = new TableDarkCell();
        table.getTableHeader().setDefaultRenderer(header);
        table.setDefaultRenderer(Object.class, cell);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);     //ID
        table.getColumnModel().getColumn(1).setPreferredWidth(300);     //Name
        table.getColumnModel().getColumn(2).setPreferredWidth(100);     //Quantity
        table.getColumnModel().getColumn(3).setPreferredWidth(100);     //price

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
    private JTable createClientsMarketTable(){
        String[] headers = {"ID", "NAME", "ADDRESS", "EMAIL", "AGE"};
        modelClientsMarket = new DefaultTableModel(0, 5);
        modelClientsMarket.setColumnIdentifiers(headers);
        JTable table = new JTable(modelClientsMarket);

        TableDarkHeader header = new TableDarkHeader();
        TableDarkCell cell = new TableDarkCell();
        table.getTableHeader().setDefaultRenderer(header);
        table.setDefaultRenderer(Object.class, cell);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);     //ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);     //Name
        table.getColumnModel().getColumn(2).setPreferredWidth(300);     //Address
        table.getColumnModel().getColumn(3).setPreferredWidth(300);     //Email
        table.getColumnModel().getColumn(4).setPreferredWidth(50);     //Age

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        return table;
    }
    private JTable createProductsMArketTable(){
        String[] headers = {"ID", "NAME", "QUANTITY", "PRICE"};
        modelProductsMarket = new DefaultTableModel(0, 4);
        modelProductsMarket.setColumnIdentifiers(headers);
        JTable table = new JTable(modelProductsMarket);

        TableDarkHeader header = new TableDarkHeader();
        TableDarkCell cell = new TableDarkCell();
        table.getTableHeader().setDefaultRenderer(header);
        table.setDefaultRenderer(Object.class, cell);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);     //ID
        table.getColumnModel().getColumn(1).setPreferredWidth(300);     //Name
        table.getColumnModel().getColumn(2).setPreferredWidth(100);     //Quantity
        table.getColumnModel().getColumn(3).setPreferredWidth(100);     //price

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
    private class TableDarkHeader extends DefaultTableCellRenderer {

        private Map<Integer, Integer> alignment = new HashMap<>();
        public void setAlignment(int column, int align) {
            alignment.put(column, align);
        }
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            com.setBackground(new Color(41, 48, 53));
            com.setForeground(new Color(230, 230, 230));
            com.setFont(com.getFont().deriveFont(Font.BOLD, 14));
            if (alignment.containsKey(i1)) {
                setHorizontalAlignment(alignment.get(i1));
            } else {
                setHorizontalAlignment(JLabel.CENTER);
            }
            return com;
        }
    }
    private class TableDarkCell extends DefaultTableCellRenderer {

        private Map<Integer, Integer> alignment = new HashMap<>();
        public void setAlignment(int column, int align) {
            alignment.put(column, align);
        }
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int column) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, row, column);
            if (jtable.isCellSelected(row, column) ) {
                if (row % 2 == 0) {
                    com.setBackground(new Color(33, 103, 153));
                } else {
                    com.setBackground(new Color(29, 86, 127));
                }
            } else {
                if (row % 2 == 0) {
                    com.setBackground(new Color(57, 65, 71));
                } else {
                    com.setBackground(new Color(48, 54, 60));
                }
            }
            com.setForeground(new Color(200, 200, 200));
            setBorder(new EmptyBorder(0, 5, 0, 5));
            if (alignment.containsKey(column)) {
                setHorizontalAlignment(alignment.get(column));
            } else {
                setHorizontalAlignment(JLabel.CENTER);
            }
            return com;
        }
    }
    public void fixTable(JScrollPane scroll, JTable table) {
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.getViewport().setBackground(new Color(41, 48, 53));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));

        table.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        table.getTableHeader().setResizingAllowed(false);
    }

    //----------------------------------------------------------------------------------------------------------------------GET/SET
    public String getIDTextClients() {
        return idTextClients.getText();
    }
    public String getNameTextClients() {
        return nameTextClients.getText();
    }
    public String getAddresTextClients() {
        return addresTextClients.getText();
    }
    public String getEmailTextClients() {
        return emailTextClients.getText();
    }
    public String getAgeTextClients() {
        return ageTextClients.getText();
    }
    public String getIDTextProducts() {
        return idTextProducts.getText();
    }
    public String getNameTextProducts() {
        return nameTextProducts.getText();
    }
    public String getQuantityTextProducts() {
        return quantityTextProducts.getText();
    }
    public String getPriceTextProducts() {
        return priceTextProducts.getText();
    }
    public JTable getClientsTable() {
        return clientsTable;
    }
    public JTable getProductsTable() {
        return productsTable;
    }
    public DefaultTableModel getModelClients() {
        return modelClients;
    }
    public DefaultTableModel getModelProducts() {
        return modelProducts;
    }
    public Client getSavedClient() {
        return savedClient;
    }
    public float getTotalPrice(){
        return totalPrice;
    }
    public int getOrderID(){
        return orderID;
    }
    public void setOrderID(int value){
        orderID=value;
    }
    public Product getSelectedProduct(){
        return selectedProduct;
    }
    public boolean getIsSelected(){
        return isSelected;
    }

    //------------------------------------------------------------------------------------------------------------------------LISTENERS
    public void addButtonClientsListener(ActionListener action) {
        addButtonClients.addActionListener(action);
    }
    public void updateButtonClientsListener(ActionListener action) {
        updateButtonClients.addActionListener(action);
    }
    public void deleteButtonClientsListener(ActionListener action) {
        deleteButtonClients.addActionListener(action);
    }
    public void clearButtonClientsListener(ActionListener action) {
        clearButtonClients.addActionListener(action);
    }
    public void reloadButtonClientsListener(ActionListener action) {
        reloadButtonClients.addActionListener(action);
    }

    public void addButtonProductsListener(ActionListener action) {
        addButtonProducts.addActionListener(action);
    }
    public void updateButtonProductsListener(ActionListener action) {
        updateButtonProducts.addActionListener(action);
    }
    public void deleteButtonProductsListener(ActionListener action) {
        deleteButtonProducts.addActionListener(action);
    }
    public void clearButtonProductsListener(ActionListener action) {
        clearButtonProducts.addActionListener(action);
    }
    public void reloadButtonProductsListener(ActionListener action) {
        reloadButtonProducts.addActionListener(action);
    }

    public void cancelMarketButtonListener(ActionListener action) {
        cancelMarketButton.addActionListener(action);
    }
    public void selectMarketButtonListener(ActionListener action) {
        selectMarketButton.addActionListener(action);
    }
    public void deleteMarketButtonListener(ActionListener action) {
        deleteMarketButton.addActionListener(action);
    }
    public void addproductMarketButtonListener(ActionListener action) {
        saveMarketButton.addActionListener(action);
    }
    public void printMarketButtonListener(ActionListener action) {
        printMarketButton.addActionListener(action);
    }
    public void sendMarketButtonListener(ActionListener action) {
        sendMarketButton.addActionListener(action);
    }

}


