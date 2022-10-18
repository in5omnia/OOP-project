package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.app.main.Prompt;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		addStringField("clientKey",  Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String clientKey = stringField("clientKey");
		//try {
			String showClient = _receiver.showClient(clientKey);
		//} FIXME
	}
}
