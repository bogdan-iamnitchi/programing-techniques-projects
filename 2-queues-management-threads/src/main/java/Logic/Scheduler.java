package Logic;

import Model.Client;
import Model.Server;
import Strategy.Strategy;
import Strategy.ShortestTimeStrategy;
import Strategy.ShortestQueueStartegy;
import Strategy.StrategyPolicy;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private static int maxClientsPerTime;
    private static int peakHour;
    private static List<Integer> peakHours;

    private final int maxNoOfServers;
    private final int maxClientsPerServer;
    private final List<Server> servers;
    private Strategy strategy;

    public Scheduler(int maxNoOfServers, int maxClientsPerServer) {
        this.maxNoOfServers = maxNoOfServers;
        this.maxClientsPerServer = maxClientsPerServer;
        this.servers = new ArrayList<>();
        this.createServes();

        maxClientsPerTime = 0;
        peakHour = 0;
        peakHours = new ArrayList<>();
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public static int getMaxClientsPerTime() {
        return maxClientsPerTime;
    }

    public static String getPeakHours() {
        //System.out.println(peakHour);
        peakHours.removeIf(hour -> hour < peakHour);
        return peakHours.toString();
    }

    public void createServes() {
        for(int i=0; i<maxNoOfServers; i++) {
            servers.add(new Server(maxClientsPerServer));
        }
    }

    public boolean serversEmpty() {
        for(Server server : servers) {
            if(!server.isEmpty())
                return false;
        }
        return true;
    }

    public void selectStrategy(int policy) {
        if(policy == StrategyPolicy.SHORTEST_TIME.value) {
            this.strategy = new ShortestTimeStrategy();
        } else {
            this.strategy = new ShortestQueueStartegy();
        }
    }

    public void dispatchClient(Client client) {
        strategy.addTask(servers, client);
    }

    public void findPeekHour(int curentTime){
        int count = 0;
        for (Server server : servers) {
            count += server.getServerSize();
        }
        if(maxClientsPerTime < count) {
            maxClientsPerTime = count;
            peakHour = curentTime;
            peakHours.add(curentTime);
        }
        else if(maxClientsPerTime == count) {
            peakHours.add(curentTime);
        }
    }

    public String toString() {
        StringBuilder rez = new StringBuilder();
        int cnt = 0;
        for(Server server : servers) {
            rez.append("Queue ").append(++cnt).append(": ").append(server);
        }
        rez.append("\n");
        return String.valueOf(rez);
    }
}
