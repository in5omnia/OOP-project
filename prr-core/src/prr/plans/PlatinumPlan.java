package prr.plans;

public class PlatinumPlan implements Plan {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    @Override
    public double calculateMessageCost(double units) {
        if (units < 50)
            return 0;
        return 4;
    }

    @Override
    public double calculateVoiceCommunicationCost(double units, boolean isFriend) {
        double cost = 10 * units;
        if (isFriend)
            return cost * 0.5;
        return cost;
    }

    @Override
    public double calculateVideoCommunicationCost(double units, boolean isFriend) {
        double cost = 10 * units;
        if (isFriend)
            return cost * 0.5;
        return cost;
    }

}
