package Logic;

import GUI.AppView;
import Model.Client;
import Model.Server;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable{

    private final int N, Q;
    private final int timeMinArrival, timeMaxArrival;
    private final int timeMinService, timeMaxService;
    private final int timeMaxSimulation;
    private final Scheduler scheduler;
    private final AppView screen;
    private static List<Client> generatedClients;
    private static int cnt = 1;

    public SimulationManager(int N, int Q, int timeMinArrival, int timeMaxArrival, int timeMinService,
                             int timeMaxService, int timeMaxSimulation, int strategy, boolean generate, AppView screen) {
        this.N = N;
        this.Q = Q;
        this.timeMinArrival = timeMinArrival;
        this.timeMaxArrival = timeMaxArrival;
        this.timeMinService = timeMinService;
        this.timeMaxService = timeMaxService;
        this.timeMaxSimulation = timeMaxSimulation;

        scheduler = new Scheduler(this.Q, this.N);
        scheduler.selectStrategy(strategy);

        if(generate) {
            generatedClients = generateRandomClients(this.N);
        }
        this.screen = screen;
    }

    public List<Client> getGeneratedClients() {
        return generatedClients;
    }

    public List <Client> generateRandomClients(int N){
        List <Client> generatedClients = new ArrayList<>();
        Random rand = new Random();

        for(int i=0; i<N; i++) {
            Client newClient = new Client();
            newClient.setId(i+1);
            newClient.setTimeArrival(rand.nextInt((timeMaxArrival - timeMinArrival) + 1) + timeMinArrival);
            newClient.setTimeService(rand.nextInt((timeMaxService - timeMinService) + 1) + timeMinService);
            generatedClients.add(newClient);
        }
        generatedClients.sort(new CompareByTimeArrival());
        return generatedClients;
    }

    public String printTestSpecification() {
        StringBuilder rez = new StringBuilder();
        rez.append("\n==================================================================================");
        rez.append("\nTEST " + cnt + ": \n");
        rez.append("N = " + N + " | " + "Q = " + Q + "\n");
        rez.append("Time Max Simulation  = " + timeMaxSimulation + "\n");
        rez.append("Bound Time Arrival  = [" + timeMinArrival + ", " + timeMaxArrival+ "]\n");
        rez.append("Bound Time Service  = [" + timeMinService + ", " + timeMaxService+ "]\n");
        rez.append("===================================================================================\n");
        return String.valueOf(rez);
    }
    public String toString(int time, List<Client> generatedClients) {
        StringBuilder rez = new StringBuilder();
        rez.append("-----------------------------------------------------------------------------------");
        rez.append("\nTime ").append(time).append(": \n");
        for(Client client : generatedClients) {
            rez.append(client);
        }
        rez.append("\n");
        rez.append(scheduler);
        rez.append("-----------------------------------------------------------------------------------\n");
        return String.valueOf(rez);
    }

    public String printResults() {
        StringBuilder rez = new StringBuilder();
        rez.append("\n==================================================================================");
        rez.append("\nSimulation completed succesfully...\n");
        rez.append("Peak Hours: ").append(Scheduler.getPeakHours()).append("\n");
        rez.append("Average waiting time: ").append(Server.averageWaitTime()).append(" sec\n");
        rez.append("Average service time: ").append(Server.averageServiceTime()).append(" sec\n");
        rez.append("===================================================================================");
        return String.valueOf(rez);
    }

    @Override
    public void run() {

        try{
            FileWriter myWriter = new FileWriter("test-" + (cnt) + "-log.txt");
            myWriter.write(printTestSpecification());

            int curentTime = 0;
            Client newClient;

            while(curentTime < timeMaxSimulation) {
                if(!generatedClients.isEmpty()) {
                    newClient = generatedClients.get(0);

                    while(newClient.getTimeArrival() == curentTime && !generatedClients.isEmpty()) {

                        scheduler.dispatchClient(newClient);
                        generatedClients.remove(newClient);
                        if(!generatedClients.isEmpty())
                            newClient = generatedClients.get(0);
                    }
                }
                scheduler.findPeekHour(curentTime);

                int finalCurentTime = curentTime;
                myWriter.write(toString(curentTime, generatedClients));
                SwingUtilities.invokeLater(() -> screen.refreshSimList(finalCurentTime, scheduler.getServers(), generatedClients));
                //System.out.println(toString(curentTime, generatedClients));

                try {
                    Thread.sleep(Server.SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                curentTime++;
                if(generatedClients.isEmpty() && scheduler.serversEmpty()){
                    break;
                }
            }
            SwingUtilities.invokeLater(
                    () -> screen.refreshResultsList("Generating test-" + (cnt++)+ "-log.txt ...",
                            Scheduler.getPeakHours(), Server.averageWaitTime(), Server.averageServiceTime())
            );
            myWriter.write(printResults());
            myWriter.close();
        } catch (IOException e) {
            screen.showError("An error ocured to writing to the file");
        }
    }

    public void start(){
        Thread mainThread = new Thread(this);
        mainThread.start();
    }

}
