package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class Communication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private Terminal _source;
    private Terminal _destination;

    private boolean _ongoing = true;

    // FIXME I will leave this here, however we might have to put it in the subclasses
    // I was thinking of using the strategy pattern to display it on toString
    // that way the code would look cleaner and as DM probably wants
    // This should not be hard but i really need to do communications FIXME
    private int _units = 0;

    private long _cost = 0;

    private String _communicationId;


    @Override
    public String toString() {
        String status = _ongoing ? "ONGOING" : "FINISHED";

        return "|" + _communicationId + "|" + _source + "|" + _destination + "|" + _units + "|" + _cost + "|" + status;
    }
}
