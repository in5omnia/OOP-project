package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		//FIXME add command fields
	}

	@Override
	protected final void execute() throws CommandException {
                //FIXME implement command
                // create an instance of prr.app.terminal.Menu with the
                // selected Terminal
		Form f = new Form();
		f.addStringField("terminalKey", Prompt.terminalKey());
		f.parse();
		String terminalKey = f.stringField("terminalKey");
		(new prr.app.terminal.Menu(_receiver, _receiver.findTerminal(terminalKey))).open();
		//FIXME surround with a try catch in case the terminal doesn't exist
	}
}
