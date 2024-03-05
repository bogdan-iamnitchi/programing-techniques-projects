package GUI;

import Model.Client;
import Model.Server;

import java.util.ArrayList;
import java.util.List;

public record SimulationEntry(int time, List<Server> servers, List<Client> clients) {

    public SimulationEntry(int time, List<Server> servers, List<Client> clients) {
        this.time = time;
        this.servers = new ArrayList<>();
        for (Server server : servers) {
            this.servers.add(new Server(server.MAX_CLIENTS, server));
        }
        this.clients = new ArrayList<>();
        for (Client client : clients) {
            Client nouClient = new Client(client.getId(), client.getTimeArrival(), client.getTimeService());
            this.clients.add(nouClient);
        }
    }
}
