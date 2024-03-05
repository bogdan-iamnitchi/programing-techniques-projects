package Strategy;

import Model.Client;
import Model.Server;

import java.util.List;

public class ShortestQueueStartegy implements Strategy{
    private int minQueue = Integer.MAX_VALUE;

    public void startServer(Server server, Client client) {
        Thread serverProcess = new Thread(server);
        server.addClientToQueue(client);
        client.setTimeWait(client.getTimeService());
        serverProcess.start();
    }

    public void addToExistingServer(Server server, Client client) {
        server.addClientToQueue(client);
        client.setTimeWait(minQueue + client.getTimeService());
    }

    public int findMin(List<Server> servers) {
        for(Server server : servers) {
            if(!server.isEmpty())
                minQueue = Math.min(server.getServerSize(), minQueue);
        }
        return minQueue;
    }

    @Override
    public void addTask(List<Server> servers, Client client) {

        for(Server server : servers) {
            if(server.isEmpty()) {
                startServer(server, client);
                return;
            }
        }

        minQueue = findMin(servers);
        for(Server server : servers) {
            if(!server.isEmpty() && server.getServerSize() == minQueue) {
                addToExistingServer(server, client);
                return;
            }
        }
    }
}
