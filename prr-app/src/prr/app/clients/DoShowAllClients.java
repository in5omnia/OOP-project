package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

	DoShowAllClients(Network receiver) {
		super(Label.SHOW_ALL_CLIENTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(_receiver.showAllClients());
		} //catch (UnknownClientException e) {
			throw new UnknownClientKeyException(e.getKey());	//FIXME does this have to be catched?
		}
	}
}
