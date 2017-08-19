package kfetinfo.ui;

import javafx.scene.control.ListCell;
import kfetinfo.core.Boisson;
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

/**
 * <p>ContenuCommandeCell est une classe utilisée pour afficher des objets {@code ContenuCommande} dans des {@code ListView}. Elle hérite de {@code ListCell<ContenuCommande>}, du package {@code javafx.scene.control}.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class ContenuCommandeCell extends ListCell<ContenuCommande> {

	@Override
	protected void updateItem(ContenuCommande item, boolean empty){

		super.updateItem(item, empty);
		setText(null);
		if(!empty && item != null){
			if(item.getNom() != ""){
				final String text = item.getNom();
				setText(text);
			} else {
				String type = "Nouveau";
				if(item instanceof Plat){
					type = "Nouveau plat";
				} else if(item instanceof Ingredient){
					type = "Nouvel ingrédient";
				} else if(item instanceof Sauce){
					type = "Nouvelle sauce";
				} else if(item instanceof Boisson){
					type = "Nouvelle boisson";
				} else if(item instanceof SupplementBoisson){
					type = "Nouveau supplément";
				} else if(item instanceof Dessert){
					type = "Nouveau dessert";
				}

				setText(type);
			}
		}
	}
}
