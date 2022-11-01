package prr.clients;

import prr.plans.Plan;
import prr.terminals.communication.Communication;
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

    private int _textCommunicationCounter = 0;  //what about different terminals - like doing 5 video but the 5th is
    // ongoing when a voice/text starts and resets the counters, not allowing level to change
    private int _videoCommunicationCounter = 0;

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

    public int getTextCommunicationCounter() {
        return _textCommunicationCounter;
    }

    public int getVideoCommunicationCounter(){
        return _videoCommunicationCounter;
    }

    public void detectCommunication(Text communication){
        _textCommunicationCounter++;
        _videoCommunicationCounter = 0;
    }

    public void detectCommunication(Voice communication){
        _textCommunicationCounter = 0;
        _videoCommunicationCounter = 0;
    }

    public void detectCommunication(Video communication){
        _videoCommunicationCounter++;
        _textCommunicationCounter = 0;
    }

    public void negativeBalance() {
        Client client = getClient();
        if (client.calculateBalance() < 0)
            client.setLevel(new Normal(client));
    }

    public void clientBalanceOver500(){}

    public void positiveBalanceAnd5Video(){}

    public void positiveBalanceAnd2Text(){}

}
