package prr.app.main;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

	DoOpenFile(NetworkManager receiver) {
		super(Label.OPEN_FILE, receiver);
		addStringField("filename", Prompt.openFile());
	}

	@Override
	protected final void execute() throws CommandException {

		try {
			String filename = stringField("filename");
			_receiver.load(filename);

		} catch (UnavailableFileException e) {
				throw new FileOpenFailedException(e);
		}

	}
}
