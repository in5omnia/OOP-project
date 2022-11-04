package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public class Voice extends InteractiveCommunication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;
    public Voice(Terminal source, Terminal destination, int communicationId) {
        super(source, destination, communicationId);
    }

    @Override
    protected double calculateCost(double units){
        Terminal source = getSource();
        boolean isFriend = source.isFriend(getDestination().getKey());
        return source.getOwner().getPlan().calculateVoiceCommunicationCost(units, isFriend);
    }

    @Override
    public String toString() {
        return "VOICE" + super.toString();
    }
}
