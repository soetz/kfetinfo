package kfetinfo.ui;

import javafx.scene.control.ListCell;
import kfetinfo.core.Membre;

/**
 * <p>MembreCell est une classe utilisée pour afficher des objets {@code Membre} dans des {@code ListView}. Elle hérite de {@code ListCell<Membre>}, du package {@code javafx.scene.control}.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class MembreCell extends ListCell<Membre> {

	@Override
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