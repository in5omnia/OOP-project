package prr.plans;


public class GoldPlan implements Plan {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public long calculateMessageCost(int units){
        if (units < 100)
            return 10;
        return (long) 2*units;
    }

    public long calculateVoiceCommunicationCost(int units, boolean isFriend){
        long cost = (long) 10*units;
        if (isFriend)
            return (long) (cost*0.5);
        return cost;
    }

    public long calculateVideoCommunicationCost(int units, boolean isFriend){
        long cost = (long) 20*units;
        if (isFriend)
            return (long) (cost*0.5);
        return cost;
    }

}
