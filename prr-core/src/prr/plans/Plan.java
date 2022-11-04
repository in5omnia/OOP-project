package prr.plans;

import java.io.Serializable;

public interface Plan extends Serializable {
    double calculateMessageCost(double units);
    double calculateVoiceCommunicationCost(double units, boolean isFriend);
    double calculateVideoCommunicationCost(double units, boolean isFriend);
    //FIXME too much code repetition in subclasses?

}
