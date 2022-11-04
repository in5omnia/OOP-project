package prr.clients;

import prr.plans.Plan;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

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

    public abstract void detectCommunication(Text communication);

    public abstract void detectCommunication(Voice communication);

    public abstract void detectCommunication(Video communication);

    public abstract void updateAfterCommunication(double balance);

    public void updateAfterPayment(double balance) {}


}
