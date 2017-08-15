package kfetinfo.ui;

import javafx.scene.control.ListCell;
import kfetinfo.core.Boisson;
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

public class ContenuCommandeCell extends ListCell<ContenuCommande> {
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
