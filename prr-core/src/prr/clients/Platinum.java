package prr.clients;

import prr.plans.PlatinumPlan;

public class Platinum extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Platinum(Client client) {
        super(client, new PlatinumPlan());
    }

    @Override
    public void positiveBalanceAnd2Text(){
        Client client = getClient();
        if (getNumberOfConsecutiveTexts()==2 /*&& !anyTextOngoing()*/){
            client.setLevel(new Gold(client));
            resetConsecutiveTexts();
        }
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }

}
