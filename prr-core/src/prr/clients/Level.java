package prr.clients;

import java.io.Serializable;

public abstract class Level implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private Client _client;

    public Level(Client client) {
        _client = client;
    }

    protected Client getClient() {
        return _client;
    }
}
