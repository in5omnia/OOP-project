package prr.clients;

import java.io.Serializable;
import java.util.Comparator;

public class ClientDebtsComparator implements Comparator<Client>, Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    @Override
    public int compare(Client c1, Client c2){
        int result = (int) (c2.getDebts() - c1.getDebts());
        if (result == 0)
            result = c1.compareTo(c2);
        return result;
    }
}