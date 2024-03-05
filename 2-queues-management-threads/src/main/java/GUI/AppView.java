package GUI;

import Controller.Controller;
import Model.Client;
import Model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AppView extends JFrame{
    private JPanel mainPanel;
    private JLabel titleBottomLabel;
    private JPanel topPanel;
    private JPanel contentPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JList<Object> clientsList;
    private JScrollPane clientsScrollPane;
    private JList<Object> resultList;
    private JScrollPane resultScrollPane;
    private JTextField minArrivalTextField;
    private JTextField maxArrivalTextField;
    private JPanel timePanel;
    private JPanel leftTopPanel;
    private JTextField nrClientsTextField;
    private JTextField nrQueueTextField;
    private JLabel clientsLabel;
    private JLabel queuesLabel;
    private JComboBox<String> strategyComboBox;
    private JPanel strategyTimePanel;
    private JButton generateButton;
    private JButton resetButton;
    private JButton startButton;
    private JPanel leftBottomPanel;
    private JPanel buttonsPanel;
    private JTextField minServiceTextField;
    private JTextField maxServiceTextField;
    private JTextField maxSimTimeTextField;
    private JScrollPane simScrollPanel;
    private JList<Object> simList;

    private final ArrayList<SimulationEntry> simScreenArray;

    DefaultListModel<Object> clientsListModel;
    DefaultListModel<Object> simScreenListModel;
    DefaultListModel<Object> resultsListModel;

    public AppView(){
        super("Queues Management - TP Project");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        clientsListModel = new DefaultListModel<>();
        simScreenListModel = new DefaultListModel<>();
        resultsListModel = new DefaultListModel<>();
        clientsList.setModel(clientsListModel);
        simList.setModel(simScreenListModel);
        resultList.setModel(resultsListModel);

        simScreenArray = new ArrayList<>();

        simList.addListSelectionListener(e -> {
            int poz = simList.getSelectedIndex();
            if (poz >= 0 && poz < 6*simScreenArray.size()) {
                refreshClientsList(simScreenArray.get((poz % 2 == 0 ? poz/6 : poz/6+1)).clients());
            }
        });

    }
    //--------------------------------------------------------------------------------------------------------SHOW_ERROR
    public void showError(String errMessage) {
        UIManager.put("OptionPane.minimumSize",new Dimension(200,200));
        //JOptionPane.showMessageDialog(this, errMessage);

        JLabel label = new JLabel(errMessage);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        JOptionPane.showMessageDialog(this,label,"ERROR",JOptionPane.WARNING_MESSAGE);
    }
    //---------------------------------------------------------------------------------------------------------CLEAR
    public void clearSimResult(){
        simScreenArray.clear();
        simScreenListModel.removeAllElements();
        resultsListModel.removeAllElements();
    }

    //---------------------------------------------------------------------------------------------------------CLIENTS
    public void refreshClientsList(List<Client> clients) {
        clientsListModel.removeAllElements();
        for(Client client : clients) {
            clientsListModel.addElement(client);
        }
    }

    //---------------------------------------------------------------------------------------------------------SIM_SCREEN
    public void refreshSimList(int time, List<Server> servers, List<Client> clients) {
        refreshClientsList(clients);
        simScreenListModel.removeAllElements();

        simScreenArray.add(new SimulationEntry(time, servers, clients));
        for(SimulationEntry entry : simScreenArray) {
            simScreenListModel.addElement("-------------------------------------------------------------");
            simScreenListModel.addElement("Time " + entry.time() + ": " + entry.clients());
            int cnt = 0;
            for(Server server : entry.servers()) {
                simScreenListModel.addElement("Queue " + (++cnt) + ": " + server);
            }
            simScreenListModel.addElement("-------------------------------------------------------------");
        }
        simList.ensureIndexIsVisible(simScreenListModel.size()-1);
    }

    //---------------------------------------------------------------------------------------------------------RESULTS
    public void refreshResultsList(String mesaj, String peakHours, double avgWaitValue, double avgServiceValue) {
        resultsListModel.removeAllElements();
        resultsListModel.addElement(mesaj);
        resultsListModel.addElement("Simulation completed succesfully!");
        resultsListModel.addElement("----------------------------------------------------------------------------");
        resultsListModel.addElement("Peak hours: " + peakHours);
        resultsListModel.addElement("Average waiting time: " + avgWaitValue + " sec");
        resultsListModel.addElement("Average service time: " + avgServiceValue + " sec");
        Controller.setGenerated(false);
    }
    //---------------------------------------------------------------------------------------------------------GETERE
    public String getNrClients() {
        return nrClientsTextField.getText();
    }
    public String getNrQueue() {
        return nrQueueTextField.getText();
    }
    public String getMinArrival() {
        return minArrivalTextField.getText();
    }
    public String getMaxArrival() {
        return maxArrivalTextField.getText();
    }
    public String getMinService() {
        return minServiceTextField.getText();
    }
    public String getMaxService() {
        return maxServiceTextField.getText();
    }
    public String getMaxSimulation() {
        return maxSimTimeTextField.getText();
    }
    public int getStrategy() {
        return strategyComboBox.getSelectedIndex();
    }

    //------------------------------------------------------------------------------------------------------LISTENER
    public void addGenerateListener(ActionListener action) {
        generateButton.addActionListener(action);
    }
    public void addResetListener(ActionListener action) {
        resetButton.addActionListener(action);
    }
    public void addStartListener(ActionListener action) {
        startButton.addActionListener(action);
    }

}
