package prr.clients;

import prr.plans.GoldPlan;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

public class Gold extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Gold(Client client) {
        super(client, new GoldPlan());
    }

    private int _consecutiveVideoCommunications = 0;

    @Override
    public void detectCommunication(Text communication) {
        _consecutiveVideoCommunications = 0;
    }

    @Override
    public void detectCommunication(Voice communication) {
        _consecutiveVideoCommunications = 0;
    }

    @Override
    public void detectCommunication(Video communication) {
        _consecutiveVideoCommunications++;
    }

    @Override
    public void updateAfterCommunication(double balance) {
        if (balance < 0) {
            Client client = getClient();
            client.setLevel(new Normal(client));
        } else if (_consecutiveVideoCommunications >= 5){
            Client client = getClient();
            client.setLevel(new Platinum(client));
        }

    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
