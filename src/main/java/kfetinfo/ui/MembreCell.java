package kfetinfo.ui;

import javafx.scene.control.ListCell;
import kfetinfo.core.Membre;

public class MembreCell extends ListCell<Membre> {
	protected void updateItem(Membre item, boolean empty){
		super.updateItem(item, empty);
		setText(null);
		if(!empty && item != null){
			if(item.getNom() != ""){
				final String text = item.getBlaze();
				setText(text);
			} else {
				setText("Nouveau membre");
			}
		}
	}
}