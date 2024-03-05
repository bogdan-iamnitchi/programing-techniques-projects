package org.example;

public class MainCalculator {

    public static void main(String[] args) {

        PolCalculator calculator = new PolCalculator();
        CalcView screen = new CalcView();
        Controller controller = new Controller(calculator, screen);
        screen.setVisible(true);

        try {
            Polynomial p1 = new Polynomial("x^3-2x^2+6x-5");
            Polynomial p2 = new Polynomial("x-1");
            screen.addListHistory(new HistoryEntry("Add: ", p1, p2));
            screen.refreshHistoryList();
        }
        catch (Exception e) {
            screen.showError(e.getMessage());
        }

    }

}
