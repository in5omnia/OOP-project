package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnrecognizedEntryException;
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

        String terminalKey = integerField("terminalKey").toString();
        String terminalType = stringField("terminalType");

        String fields[] = {terminalType, terminalKey, stringField("clientKey")};

        try {
            _receiver.registerTerminal(fields);

        } catch (InvalidTerminalIdException e) {
            throw new InvalidTerminalKeyException(e.getKey());

        } catch (DuplicateTerminalException e) {
            throw new DuplicateTerminalKeyException(e.getKey());

        } catch (UnknownClientException e) {
            throw new prr.app.exceptions.UnknownClientKeyException(e.getKey());

        } catch (UnrecognizedEntryException e) {
            e.printStackTrace();
        }
    }
}
