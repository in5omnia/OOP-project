package prr.terminals.communication;

import prr.clients.Level;
import prr.clients.DetectCommunicationVisitor;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Video extends InteractiveCommunication implements Serializable{

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Video(Terminal source, Terminal destination, int communicationId) {
        super(source, destination, communicationId);
    }

    @Override
    protected double calculateCost(double units){
        Terminal source = getSource();
        boolean isFriend = source.isFriend(getDestination().getKey());
        return source.getOwner().getPlan().calculateVideoCommunicationCost(units, isFriend);
    }

    @Override
    public void accept(DetectCommunicationVisitor v, Level level) {
        v.visit(this, level);
    }

    @Override
    public String toString() {
        return "VIDEO" + super.toString();
    }
}
