package prr.app.terminal;

import prr.Network;
import prr.exceptions.InvalidCommunicationException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("communicationKey", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
		int communicationKey = integerField("communicationKey");
		try {
			_receiver.performPayment(communicationKey);

		} catch (InvalidCommunicationException e) {
			_display.popup(Message.invalidCommunication());
		}

	}
}
