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
    private double _units = 0;

    private double _cost = 0;

    private boolean _paid = false;

    private int _communicationId;

    public Communication(Terminal source, Terminal destination, int communicationId, boolean ongoing) {
        _source = source;
        _destination = destination;
        _communicationId = communicationId;
        _ongoing = ongoing;
    }

    public Communication(Terminal source, Terminal destination, int communicationId, boolean ongoing, int units) {
        this(source, destination, communicationId, ongoing);
        defineUnitsAndCost(units);
    }

    public void defineUnitsAndCost(double units){    //could be protected and called by a "setDuration" in interactiveCom
        _units = units;
        _cost = calculateCost(units);
    }

    protected abstract double calculateCost(double units);

    public Terminal getSource(){
        return _source;
    }

    public Terminal getDestination(){
        return _destination;
    }

    public int getId(){
        return _communicationId;
    }

    public boolean hasBeenPaid(){
        return _paid;
    }

    public double getCost(){
        return _cost;
    }

    public void pay() {
        _paid = true;
    }

    public boolean isOngoing() {
        return _ongoing;
    }

    public void endCommunication(){
        _ongoing = false;
    }

    public boolean ongoing(){
        return _ongoing;
    }

    @Override
    public String toString() {
        String status = _ongoing ? "ONGOING" : "FINISHED";

        return "|" + _communicationId + "|" + _source.getKey() + "|" + _destination.getKey() + "|" + Math.round(_units) + "|" + Math.round(_cost) + "|" + status;
    }
}
