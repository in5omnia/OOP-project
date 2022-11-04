package prr.app.terminal;

import prr.Network;
import prr.exceptions.InvalidDurationException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addRealField("duration", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		double duration = realField("duration");
		try {
			_display.popup(Message.communicationCost(_receiver.endInteractiveCommunication(duration)));
		} catch (InvalidDurationException e) {
			//do nothing
		}
	}
}
