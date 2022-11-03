package prr.plans;

public class PlatinumPlan implements Plan {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    @Override
    public long calculateMessageCost(int units){
        if (units < 50)
            return 0;
        return 4;
    }

    @Override
    public long calculateVoiceCommunicationCost(int units, boolean isFriend){
        long cost = (long) 10*units;
        if (isFriend)
            return (long) (cost*0.5);
        return cost;
    }

    @Override
    public long calculateVideoCommunicationCost(int units, boolean isFriend){
        long cost = (long) 10*units;
        if (isFriend)
            return (long) (cost*0.5);
        return cost;
    }

}
