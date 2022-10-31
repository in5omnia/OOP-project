package prr.notifications;

import prr.terminals.Terminal;
import prr.terminals.states.Busy;
import prr.terminals.states.Off;
import prr.terminals.states.Silent;

public class InteractiveNotification extends Notification {

    public InteractiveNotification(Terminal origin, Terminal destination) {
        super(origin, destination);
    }

    public void chooseNotificationType(Busy newState){
        setNotificationType(new B2I());
    }

    public void chooseNotificationType(Silent newState){
        setNotificationType(new S2I());
    }
    public void chooseNotificationType(Off newState){
        setNotificationType(new O2I());
    }

}
