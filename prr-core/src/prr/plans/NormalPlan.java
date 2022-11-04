package prr.plans;

public class NormalPlan implements Plan {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    @Override
    public double calculateMessageCost(double units){
        if (units < 50)
            return 10;
        if (units < 100)
            return 16;
        return 2*units;
    }

    @Override
    public double calculateVoiceCommunicationCost(double units, boolean isFriend){
        double cost = 20*units;
        if (isFriend)
            return cost*0.5;
        return cost;
    }

    @Override
    public double calculateVideoCommunicationCost(double units, boolean isFriend){
        double cost = 30*units;
        if (isFriend)
            return cost*0.5;
        return cost;
    }

}
