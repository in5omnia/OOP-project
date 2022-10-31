package prr.notifications;

import prr.terminals.Terminal;
import prr.terminals.states.Idle;
import prr.terminals.states.Off;
import prr.terminals.states.Silent;

public class TextNotification extends Notification {

    public TextNotification(Terminal origin, Terminal destination) {
        super(origin, destination);
    }

    public void chooseNotificationType(Idle newState){
        setNotificationType(new O2I());
    }

    public void chooseNotificationType(Silent newState){
        setNotificationType(new O2S());
    }

}
