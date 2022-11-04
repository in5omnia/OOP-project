package prr.notifications;

import java.io.Serializable;

import prr.clients.Client;
import prr.terminals.Terminal;

public class Notification implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    //private String _originTerminalId;
    private Client _caller;

    //private String _originClientID;

    private Terminal _destination;

    private NotificationType _type;


    public Notification(Terminal destination, Client caller, NotificationType type) {
        _destination = destination;
        _caller = caller;
        _type = type;
    }


    public Client getCaller() {
        return _caller;
    }


    @Override
    public String toString(){
        return _type + "|" +  _destination.getKey();
    }
}
