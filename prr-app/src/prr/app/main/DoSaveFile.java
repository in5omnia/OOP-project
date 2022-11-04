package prr.app.main;

import prr.NetworkManager;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;


/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {

		try {
			_receiver.save();

		} catch (MissingFileAssociationException e) {

			Form form = new Form();
			form.addStringField("filename", Prompt.newSaveAs());
			form.parse();

			try {
				_receiver.saveAs(form.stringField("filename"));

			} catch (IOException | MissingFileAssociationException ex) {
				ex.printStackTrace();
			}

		} catch (/*FileNotFoundException|*/ IOException e){
			e.printStackTrace();
		}
	}
}
