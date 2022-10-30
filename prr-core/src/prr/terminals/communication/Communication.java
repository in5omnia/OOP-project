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

    private boolean _ongoing;

    // FIXME I will leave this here, however we might have to put it in the subclasses
    // I was thinking of using the strategy pattern to display it on toString
    // that way the code would look cleaner and as DM probably wants
    // This should not be hard but i really need to do communications FIXME
    private int _units = 0;

    private long _cost = 0;

    private int _communicationId;

    public Communication(Terminal source, Terminal destination, int communicationId, boolean ongoing, int units) {
        _source = source;
        _destination = destination;
        _communicationId = communicationId;
        _ongoing = ongoing;
        _units = units;
    }

    public int getCost(){

        //TODO IMPLEMENTATION
        //must call level and plan intertwined
        return 10;
    }

    @Override
    public String toString() {
        String status = _ongoing ? "ONGOING" : "FINISHED";

        return "|" + _communicationId + "|" + _source + "|" + _destination + "|" + _units + "|" + _cost + "|" + status;
    }
}
