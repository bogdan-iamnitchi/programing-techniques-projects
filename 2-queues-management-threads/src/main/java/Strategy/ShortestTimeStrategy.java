package Strategy;

import Model.Client;
import Model.Server;

import java.util.List;

public class ShortestTimeStrategy implements Strategy{

    public void startServer(Server server, Client client) {
        Thread serverProcess = new Thread(server);
        server.addClientToQueue(client);
        client.setTimeWait(client.getTimeService());
        serverProcess.start();
    }

    public void addToExistingServer(Server server, Client client, int minTime) {
        server.addClientToQueue(client);
        client.setTimeWait(minTime + client.getTimeService());
    }

    public int findMin(List<Server> servers) {
        int minTime = Integer.MAX_VALUE;
        for(Server server : servers) {
            if(!server.isEmpty())
                minTime = Math.min(server.getWaitTime(), minTime);
        }
        return minTime;
    }

    @Override
    public void addTask(List<Server> servers, Client client) {

        for(Server server : servers) {
            if(server.isEmpty()) {
                startServer(server, client);
                return;
            }
        }

        int minTime = findMin(servers);
        for(Server server : servers) {
            if(!server.isEmpty() && server.getWaitTime() == minTime) {
                addToExistingServer(server, client, minTime);
                return;
            }
        }
    }
}
