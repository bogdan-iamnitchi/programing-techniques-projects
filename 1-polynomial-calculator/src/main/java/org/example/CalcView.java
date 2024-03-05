package org.example;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class HistoryEntry {

    public String operatie;
    public Polynomial p1;
    public Polynomial p2;

    public HistoryEntry(String operatie, Polynomial p1, Polynomial p2) {
        this.operatie = operatie;
        this.p1 = p1;
        this.p2 = p2;
    }

}

public class CalcView extends JFrame{
    private JPanel panelTop;
    private JPanel panelRight;
    private JList<Object> listHistory;
    private JTextField pol1Text;
    private JTextField pol2Text;
    private JTextField valueText;
    private JButton resetButton;
    private JButton computeButton;
    private JComboBox<String> basicOperation;
    private JComboBox<String> advancedOperation;
    private JPanel panelMain;
    private JCheckBox checkBasic;
    private JCheckBox checkValue;
    private JCheckBox checkAdvanced;
    private JLabel pol1Label;
    private JLabel pol2Label;
    private JPanel panelRightBottom;
    private JPanel panelLeft;
    private JList<Object> listResult;
    private JScrollPane scrollPaneHistory;
    private JScrollPane scrollPaneResults;
    private final ArrayList<HistoryEntry> history;
    private final ArrayList<Polynomial> results;
    DefaultListModel<Object> listHistoryModel;
    DefaultListModel<Object> listResultModel;

    public CalcView () {
        super("Polynomial Calculator - TP Project");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        listHistoryModel = new DefaultListModel<>();
        listResultModel = new DefaultListModel<>();
        listHistory.setModel(listHistoryModel);
        listResult.setModel(listResultModel);

        history = new ArrayList<>();
        results = new ArrayList<>();

        valueText.setVisible(false);

        listHistory.addListSelectionListener(e -> {
            int poz = listHistory.getSelectedIndex();
            if (poz >= 0 && poz < 5*history.size()) {
                pol1Text.setText(String.valueOf(history.get(history.size()-(poz/5)-1).p1));
                pol2Text.setText(String.valueOf(history.get(history.size()-(poz/5)-1).p2));
            }
        });

        listResult.addListSelectionListener(e -> {
            int poz = listResult.getSelectedIndex();
            if (poz >= 0 && poz < results.size()) {
                if (poz == 0)
                    pol1Text.setText(String.valueOf(results.get(poz)));
                else
                    pol2Text.setText(String.valueOf(results.get(poz)));
            }
        });

        checkValue.addActionListener(e ->
                valueText.setVisible(!valueText.isVisible()));
        checkBasic.addActionListener(e ->
                basicOperation.setEnabled(!basicOperation.isEnabled()));
        checkAdvanced.addActionListener(e ->
                advancedOperation.setEnabled(!advancedOperation.isEnabled()));
    }

    void showError(String errMessage) {
        //UIManager.put("OptionPane.minimumSize",new Dimension(200,200));
        //JOptionPane.showMessageDialog(this, errMessage);

        JLabel label = new JLabel(errMessage);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        JOptionPane.showMessageDialog(this,label,"ERROR",JOptionPane.WARNING_MESSAGE);
    }

    //-----------------------------------------------------------------------------------RESET
    public void resetAll(){
        removeListResult();
        removeHistoryList();
        pol1Text.setText("");
        pol2Text.setText("");
        valueText.setText("");
    }
    //-----------------------------------------------------------------------------------REFRESH
    public void addListResult(String mesaj, Polynomial p){
        results.add(p);
        if(checkValue.isSelected() && valueText.getText().length() > 0) {
            float value = Float.parseFloat(valueText.getText());
            listResultModel.addElement(mesaj + p + " = " + p.evalueaza(value));
        }
        else {
            listResultModel.addElement(mesaj + p);
        }
    }
    public void removeListResult() {
        results.removeAll(results);
        listResultModel.removeAllElements();
    }

    //-----------------------------------------------------------------------------------HISTORY
    public void addListHistory(HistoryEntry h){
        history.add(h);
    }
    public void refreshHistoryList(){
        listHistoryModel.removeAllElements();
        Collections.reverse(history);
        for (HistoryEntry h : history) {
            listHistoryModel.addElement("----------------------------------");
            listHistoryModel.addElement(h.operatie);
            listHistoryModel.addElement("P1: " + h.p1);
            listHistoryModel.addElement("P2: " + h.p2);
            listHistoryModel.addElement("----------------------------------");
        }
        Collections.reverse(history);
    }

    public void removeHistoryList() {
        history.removeAll(history);
        listHistoryModel.removeAllElements();
    }

    //-----------------------------------------------------------------------------------GETERE
    public String getPol1Text() {
        return pol1Text.getText();
    }
    public String getPol2Text() {
        return pol2Text.getText();
    }
    public boolean getCheckAdvanced() {
        return checkAdvanced.isSelected();
    }
    public boolean getCheckBasic() {
        return checkBasic.isSelected();
    }
    public String getBasicOperation() {
        return (String)basicOperation.getSelectedItem();
    }
    public String getAdvancedOperation() {
        return (String)advancedOperation.getSelectedItem();
    }

    //-----------------------------------------------------------------------------------LISTENER
    public void addComputeListener(ActionListener action){
        computeButton.addActionListener(action);
    }
    public void addResetListener(ActionListener action){
        resetButton.addActionListener(action);
    }

}
