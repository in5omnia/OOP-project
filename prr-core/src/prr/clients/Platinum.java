package prr.clients;

import prr.plans.PlatinumPlan;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

public class Platinum extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private int _consecutiveTextCommunications = 0;

    public Platinum(Client client) {
        super(client, new PlatinumPlan());
    }

    @Override
    public void detectCommunication(Text communication) {
        _consecutiveTextCommunications++;
    }

    @Override
    public void detectCommunication(Voice communication) {
        _consecutiveTextCommunications = 0;
    }

    @Override
    public void detectCommunication(Video communication) {
        _consecutiveTextCommunications = 0;
    }

    @Override
    public void updateAfterCommunication(double balance) {
        if (balance < 0) {
            Client client = getClient();
            client.setLevel(new Normal(client));
        }
        if (_consecutiveTextCommunications >= 2){
            Client client = getClient();
            client.setLevel(new Gold(client));
        }
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }

}
