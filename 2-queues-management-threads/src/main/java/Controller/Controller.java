package Controller;

import GUI.AppView;
import Logic.SimulationManager;

public class Controller {

    private SimulationManager simManager;
    private final AppView screen;
    private static boolean generated = false;

    private int N, Q;
    private int timeMinArrival, timeMaxArrival;
    private int timeMinService, timeMaxService;
    private int timeMaxSimulation;
    private int strategy;

    public Controller(AppView screen){
        this.screen = screen;

        solveGenerateButton();
        solveResetButton();
        solveStartButton();
    }

    public static void setGenerated(boolean generated) {
        Controller.generated = generated;
    }

    public void solveGenerateButton(){
        screen.addGenerateListener( e -> {
            generated = false;
            try {
                this.simManager = createSimulationManager();
            } catch (Exception ex) {
                screen.showError("Oops... Wrong Input!");
                return;
            }
            generated = true;
        });
    }

    public void solveResetButton(){
        screen.addResetListener( e -> screen.clearSimResult());
    }

    public void solveStartButton(){
        screen.addStartListener( e -> {
            try {
                this.simManager = createSimulationManager();
            } catch (Exception ex) {
                screen.showError("Oops... Wrong Input!");
                ex.printStackTrace();
                return;
            }
            screen.clearSimResult();
            simManager.start();
        });
    }


    public SimulationManager createSimulationManager() throws Exception{
        SimulationManager simManager;
        try {
            N = Integer.parseInt(screen.getNrClients());
            Q = Integer.parseInt(screen.getNrQueue());
            timeMinArrival = Integer.parseInt(screen.getMinArrival());
            timeMaxArrival = Integer.parseInt(screen.getMaxArrival());
            timeMinService = Integer.parseInt(screen.getMinService());
            timeMaxService = Integer.parseInt(screen.getMaxService());
            timeMaxSimulation = Integer.parseInt(screen.getMaxSimulation());
            strategy = screen.getStrategy();

        } catch (Exception exception) {
            throw new Exception(exception);
        }

        simManager = new SimulationManager(N, Q, timeMinArrival, timeMaxArrival,
                    timeMinService, timeMaxService, timeMaxSimulation, strategy, !generated,  screen);
        //simManager = new SimulationManager(10, 2, 2, 8, 2, 5, 60,  0, true, screen);

        screen.refreshClientsList(simManager.getGeneratedClients());

        return simManager;
    }

}
