package Logic;

import Model.Client;

import java.util.Comparator;

public class CompareByTimeArrival implements Comparator<Client> {
    @Override
    public int compare(Client c1, Client c2) {
        return c1.getTimeArrival() - c2.getTimeArrival();
    }
}
