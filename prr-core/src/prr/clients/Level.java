package prr.clients;

import prr.plans.Plan;

import java.io.Serializable;

public abstract class Level implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private Plan _plan;

    private Client _client;

    public Level(Client client, Plan plan) {
        _client = client;
        _plan = plan;
    }

    protected Client getClient() {
        return _client;
    }

    public Plan getPlan() {
        return _plan;
    }

    public void negativeBalance() {
        Client client = getClient();
        if (client.calculateBalance() < 0)
            client.setLevel(new Normal(client));
    }

    public void positiveBalanceAnd5Video(){}
    public void positiveBalanceAnd2Text(){}

}
