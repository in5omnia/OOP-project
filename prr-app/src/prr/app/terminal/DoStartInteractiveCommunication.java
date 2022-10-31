
//-------
package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;

import prr.exceptions.DestinationTerminalSilentException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.DestinationTerminalBusyException;
import prr.exceptions.DestinationTerminalOffException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.exceptions.CannotCommunicateException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("destinationTerminal", Prompt.terminalKey());
		addOptionField("communicationType", Prompt.commType(), "VOICE", "VIDEO");

	}

	@Override
	protected final void execute() throws CommandException {
		String destinationTerminalKey = stringField("destinationTerminal");
		String communicationType = stringField("communicationType");
		try {
			_receiver.startInteractiveCommunication(_network, destinationTerminalKey, communicationType);

		} catch(UnsupportedAtOriginException e){
			_display.popup(Message.unsupportedAtOrigin(e.getKey(), e.getType()));

		} catch(UnsupportedAtDestinationException e){
			_display.popup(Message.unsupportedAtDestination(e.getKey(), e.getType()));

		} catch(DestinationTerminalOffException e) {
			_display.popup(Message.destinationIsOff(destinationTerminalKey));

		} catch(DestinationTerminalBusyException e) {
			_display.popup(Message.destinationIsBusy(destinationTerminalKey));

		} catch(DestinationTerminalSilentException e) {
			_display.popup(Message.destinationIsSilent(destinationTerminalKey));

		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(destinationTerminalKey);

		} catch (CannotCommunicateException e) {
			//do nothing FIXME
		}

	}
}
