/*
 * kfetinfo - Logiciel pour la K'Fet du BDE Info de l'IUT Lyon 1
 *  Copyright (C) 2017 Simon Lecutiez

 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kfetinfo.ui;

import kfetinfo.core.Boisson;
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

import javafx.scene.control.ListCell;

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
