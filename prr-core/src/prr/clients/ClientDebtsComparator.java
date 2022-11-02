package prr.clients;

import java.util.Comparator;

public class ClientDebtsComparator implements Comparator<Client> {  //FIXME static?

    @Override
    public int compare(Client c1, Client c2){
        int result = (int) (c2.getDebts() - c1.getDebts());
        if (result == 0)
            result = c1.compareTo(c2);
        return result;
    }
}