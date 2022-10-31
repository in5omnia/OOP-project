package prr.plans;

import java.io.Serializable;

public interface Plan extends Serializable {
    long calculateMessageCost(int units);
    long calculateVoiceCommunicationCost(int units, boolean isFriend);
    long calculateVideoCommunicationCost(int units, boolean isFriend);
    //FIXME too much code repetition in subclasses?

}
