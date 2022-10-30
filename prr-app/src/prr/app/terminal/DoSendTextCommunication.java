package prr.app.terminal;

import prr.Network;
import prr.exceptions.CannotCommunicateException;
import prr.exceptions.DestinationTerminalOffException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("destinationTerminal", Prompt.terminalKey());
                addStringField("message", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                String destinationTerminal = stringField("destinationTerminal");
                String message = stringField("message");
                try {
                        _receiver.sendTextCommunication(_network, destinationTerminal, message);
                } catch (DestinationTerminalOffException e){
                        _display.popup(Message.destinationIsOff(destinationTerminal));
                } catch (UnknownTerminalException e) {
                        throw new UnknownTerminalKeyException(e.getKey());
                } catch (CannotCommunicateException e){
                        //do nothing FIXME
                }

                //FIXME implement command
        }
} 
