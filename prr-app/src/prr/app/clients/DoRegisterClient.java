package prr.app.clients;

import prr.Network;
import prr.exceptions.DuplicateClientException;
import prr.app.exceptions.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

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
		String clientId = stringField("clientId");
		String name = stringField("name");
		int taxId = integerField("taxId");
		try {
			_receiver.registerClient(clientId, name, taxId);
		} catch (DuplicateClientException e) {
			throw new DuplicateClientKeyException(e.getKey());
		}
	}

}
