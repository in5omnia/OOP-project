package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.enableClientNotifications(stringField("clientId"));
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(e.getKey());
		} /* catch (AlreadyOnNotificationException e) {
		//FIXME verify this code
			_display.popup(Message.clientNotificationsAlreadyEnabled());
		} */
	}
}
