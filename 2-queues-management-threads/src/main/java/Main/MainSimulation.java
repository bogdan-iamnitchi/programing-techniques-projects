package Main;

import Controller.Controller;
import GUI.AppView;

public class MainSimulation {
    public static void main(String[] args) {
        AppView screen = new AppView();
        Controller controller = new Controller(screen);
        screen.setVisible(true);
    }
}
