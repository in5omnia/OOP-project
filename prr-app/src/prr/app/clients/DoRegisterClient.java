package prr.app.clients;

import prr.Network;
import prr.exceptions.DuplicateClientException;
import prr.app.exceptions.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("clientId", Prompt.key());
		addStringField("name", Prompt.name());
		addIntegerField("taxId", Prompt.taxId());

	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.registerClient(stringField("clientId"), stringField("name"), integerField("taxId"));

		} catch (DuplicateClientException e) {
			throw new DuplicateClientKeyException(e.getKey());
		}
	}

}
