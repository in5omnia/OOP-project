package prr.clients;

import prr.plans.Plan;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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



    /*public int getTextCommunicationCounter() {
        return _textCommunicationCounter;
    }

    public int getVideoCommunicationCounter(){
        return _videoCommunicationCounter;
    }*/

   /* public void detectCommunication(Text communication){
        _textCommunicationCounter++;
        _videoCommunicationCounter = 0;
    }

    public void detectCommunication(Voice communication){
        _textCommunicationCounter = 0;
        _videoCommunicationCounter = 0;
    }*/

    public abstract void detectCommunication(Text communication);/*{
        if (getNumberOfConsecutiveVideos() < 5)
            _videoCommunications.clear();
        _textCommunications.add(communication);
    } */

    public abstract void detectCommunication(Voice communication); /*{
        if (getNumberOfConsecutiveTexts() < 2)
            _textCommunications.clear();
        if (getNumberOfConsecutiveVideos() < 5)
            _videoCommunications.clear();
        //addVoice?
    } */

    public abstract void detectCommunication(Video communication); /*{
        if (getNumberOfConsecutiveTexts() < 2)
            _textCommunications.clear();
        _videoCommunications.add(communication);
    }*/

    public void negativeBalance() {
        Client client = getClient();
        if (client.calculateBalance() < 0)
            client.setLevel(new Normal(client));
    }

    public abstract void updateAfterCommunication(double balance);

    public void updateAfterPayment(double balance) {}


    public void clientBalanceOver500(){}

    public void positiveBalanceAnd5Video(){}

    public void positiveBalanceAnd2Text(){}

}
