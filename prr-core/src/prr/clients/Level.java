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

    //private List<Communication> _consecutiveCommunications = new ArrayList<>(); FIXME
    private List<Text> _textCommunications = new ArrayList<>();
    private List<Video> _videoCommunications = new ArrayList<>();

    //private int _textCommunicationCounter = 0;  //what about different terminals - like doing 5 video but the 5th is FIXME
    // ongoing when a voice/text starts and resets the counters, not allowing level to change FIXME
    //private int _videoCommunicationCounter = 0; FIXME

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


    protected int getNumberOfConsecutiveTexts(){
        return _textCommunications.size();
    }


    protected boolean anyVideoOngoing(){
        boolean ongoing = false;
        for (Video video : _videoCommunications){
            if (video.isOngoing()){
                ongoing = false;
                break;
            }
        }
        return ongoing;
    }

    protected int getNumberOfConsecutiveVideos(){
        return _videoCommunications.size();
    }

    protected void resetConsecutiveVideos(){
        _videoCommunications.subList(0, 4).clear(); //FIXME does this work
    }

    protected void resetConsecutiveTexts(){
        _textCommunications.subList(0, 1).clear(); //FIXME does this work
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

    public void detectCommunication(Text communication){
        if (getNumberOfConsecutiveVideos() < 5)
            _videoCommunications.clear();
        _textCommunications.add(communication);

    }

    public void detectCommunication(Voice communication){
        if (getNumberOfConsecutiveTexts() < 2)
            _textCommunications.clear();
        if (getNumberOfConsecutiveVideos() < 5)
            _videoCommunications.clear();
        //addVoice?
    }

    public void detectCommunication(Video communication){
        if (getNumberOfConsecutiveTexts() < 2)
            _textCommunications.clear();
        _videoCommunications.add(communication);
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
