package prr.clients;


import prr.plans.NormalPlan;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

public class Normal extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Normal(Client client) {
        super(client, new NormalPlan());
    }

    @Override
    public void updateAfterPayment(double balance) {
        if (balance > 500){
            Client client = getClient();
            client.setLevel(new Gold(client));
        }
    }

    @Override
    public void detectCommunication(Text communication) {}  //do nothing

    @Override
    public void detectCommunication(Voice communication) {} //do nothing

    @Override
    public void detectCommunication(Video communication) {} //do nothing

    @Override
    public void negativeBalance(){}

    @Override
    public void updateAfterCommunication(double balance) {} //do nothing

    @Override
    public String toString() {
        return "NORMAL";
    }

}
