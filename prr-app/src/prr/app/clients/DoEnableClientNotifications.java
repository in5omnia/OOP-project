package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.AlreadyOnNotificationException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.enableClientNotifications(stringField("clientId"));
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(e.getKey());
		}  catch (AlreadyOnNotificationException e) {
			_display.popup(Message.clientNotificationsAlreadyEnabled());
		}
	}
}
