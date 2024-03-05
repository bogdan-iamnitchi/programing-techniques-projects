package Model;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private static int totalWaitTime = 0;
    private static int totalServiceTime = 0;
    private static int nrOfServedClients = 0;

    private final BlockingQueue<Client> clients;
    private final AtomicInteger waitTime;
    private volatile boolean hasClients;

    public int MAX_CLIENTS = 500;
    public static final int SLEEP_TIME = 300;

    public Server(int MaxClientsPerServer) {
        this.MAX_CLIENTS = MaxClientsPerServer;
        this.clients = new ArrayBlockingQueue<>(MaxClientsPerServer);
        this.waitTime = new AtomicInteger(0);
        this.hasClients = false;
    }

    public Server(int MaxClientsPerServer, Server server) {
        this.clients = new ArrayBlockingQueue<>(MaxClientsPerServer);

        for(Client client : server.clients) {
            Client nouClient = new Client(client.getId(),client.getTimeArrival(), client.getTimeService());
            this.clients.add(nouClient);
        }
        this.waitTime = new AtomicInteger(server.getWaitTime());
        this.hasClients = server.hasClients;
    }

    public int getServerSize(){
        return clients.size();
    }
    public int getWaitTime() {
        return waitTime.get();
    }
    public boolean isEmpty() { return clients.isEmpty(); }

    public void addClientToQueue(Client newClient) {
        clients.add(newClient);
        hasClients = true;
        waitTime.set(waitTime.get() + newClient.getTimeService());
        totalServiceTime += newClient.getTimeService();
    }

    public void removeClientFromQueue(Client oldClient) {
        clients.remove(oldClient);
        nrOfServedClients++;

        totalWaitTime += oldClient.getTimeWait();

        if(clients.isEmpty()) {
            hasClients = false;
        }
    }

    public static double averageWaitTime()
    {
        return Math.round((double) totalWaitTime /nrOfServedClients*1000.0)/1000.0;
    }

    public static double averageServiceTime()
    {
        return Math.round((double) totalServiceTime /nrOfServedClients*1000.0)/1000.0;
    }

    @Override
    public void run() {
        while(hasClients) {
            Iterator<Client> clientIterator = clients.iterator();
            Client curentClient = clientIterator.next();

            if(curentClient.getTimeService() == 0) {
                removeClientFromQueue(curentClient);
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
            if(curentClient.decrementAndGetService() == 0) {
                removeClientFromQueue(curentClient);
            }
            waitTime.decrementAndGet();
        }

    }

    public String toString() {
        StringBuilder rez = new StringBuilder();
        for(Client client : clients) {
            rez.append(client);
        }
        rez.append("\n");
        return String.valueOf(rez);
    }
}