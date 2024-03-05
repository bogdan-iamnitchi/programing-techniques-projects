package Strategy;

import Model.Client;
import Model.Server;

import java.util.List;

public interface Strategy {

    void addTask(List<Server> servers, Client client);

}
