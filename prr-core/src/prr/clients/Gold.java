package prr.clients;

import prr.plans.GoldPlan;

public class Gold extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Gold(Client client) {
        super(client, new GoldPlan());
    }

    @Override
    public void positiveBalanceAnd5Video(){
        Client client = getClient();
        if (getNumberOfConsecutiveVideos()==5 /*&& !anyVideoOngoing()*/){
            client.setLevel(new Platinum(client));
            resetConsecutiveVideos();
        }
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
