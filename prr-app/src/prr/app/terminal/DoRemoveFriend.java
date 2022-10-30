package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		addStringField("friendTerminalKey", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		String friend = stringField("friendTerminalKey");
		try{
			_receiver.removeFriendFromTerminal(_network, friend);
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
                //FIXME implement command
	}
}
