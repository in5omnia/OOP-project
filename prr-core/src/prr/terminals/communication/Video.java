package prr.terminals.communication;

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
    public String toString() {
        return "VIDEO" + super.toString();
    }
}
