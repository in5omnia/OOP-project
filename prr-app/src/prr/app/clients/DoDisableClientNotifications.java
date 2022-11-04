package prr.app.clients;

import prr.Network;
import prr.exceptions.UnknownClientException;
import prr.exceptions.AlreadyOffNotificationException;
import prr.app.exceptions.UnknownClientKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

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
			_receiver.disableClientNotifications(stringField("clientId"));

		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(e.getKey());

		}  catch (AlreadyOffNotificationException e) {
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		}
	}
}
