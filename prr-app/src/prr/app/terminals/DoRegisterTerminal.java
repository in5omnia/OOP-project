package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.exceptions.*;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

    DoRegisterTerminal(Network receiver) {
        super(Label.REGISTER_TERMINAL, receiver);
        addIntegerField("terminalKey", Prompt.terminalKey());
        addOptionField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
        addStringField("clientKey", Prompt.clientKey());
        //FIXME add command fields
    }

    @Override
    protected final void execute() throws CommandException {
        //FIXME Boas Praticas
        String fields[] = {
				integerField("terminalKey").toString(),
                stringField("terminalType"),
                stringField("clientKey")
		};

        try {
            _receiver.registerTerminal(fields);
        } catch (InvalidTerminalIdException e) {
            throw new InvalidTerminalKeyException(e.getKey());
        } catch (DuplicateTerminalException e) {
            throw new DuplicateTerminalKeyException(e.getKey());
        } catch (UnrecognizedEntryException | UnknownClientException e) {
            e.printStackTrace(); //FIXME do we leave this here?
        }
    }
}
